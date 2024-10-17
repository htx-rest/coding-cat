package com.htx.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htx.dto.UpdateAdminPasswordParam;
import com.htx.dto.UsersPageQueryParam;
import com.htx.exception.Asserts;
import com.htx.mapper.AdminRoleRelationMapper;
import com.htx.mapper.UsersMapper;
import com.htx.model.*;
import com.htx.service.IUsersCacheService;
import com.htx.service.IUsersService;
import com.htx.state.UserStatus;
import com.htx.state.UserType;
import com.htx.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/13 22:11
 * @Desc: 用户表 服务实现类
 */
@Service
@Slf4j
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {
    private static Logger LOGGER = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private IUsersCacheService usersCacheService;

    @Autowired
    private AdminRoleRelationMapper adminRoleRelationMapper;

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public Users getAdminByUsername(String username) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_login", username);
        List<Users> usersList = baseMapper.selectList(queryWrapper);

        if (usersList != null && usersList.size() > 0) {
            return usersList.get(0);
        }

        // 用户名错误，提前抛出异常
        throw new UsernameNotFoundException("用户名错误");
    }

    @Override
    public boolean register(Users users) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_login", users.getUserLogin());
        int count = this.baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            return false;
        }
        users.setUserRegistered(new Date());
        users.setUserType(UserType.BACKEND.getType());
        users.setUserStatus(UserStatus.ENABLE.getStatus());
        String encodePassword = passwordEncoder.encode(users.getUserPass());
        users.setUserPass(encodePassword);

        return save(users);
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            // 查询用户+用户资源
            UserDetails userDetails = loadUserByUsername(username);

            // 验证密码
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                Asserts.fail("密码不正确");
            }
            if (!userDetails.isEnabled()) {
                Asserts.fail("帐号已被禁用");
            }

            // 返回 JWT
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    public void setAuthentication(UserDetails userDetails) {
        // 当前登录用户+用户权限
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public String refreshToken(String oldToken) {
        return jwtTokenUtil.refreshHeadToken(oldToken);
    }

    @Override
    public int updatePassword(UpdateAdminPasswordParam updatePasswordParam) {
        AdminUserDetails adminUserDetails = (AdminUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users users = adminUserDetails.getUsers();

        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_login", users.getUserLogin());
        List<Users> usersList = baseMapper.selectList(queryWrapper);

        if (CollUtil.isEmpty(usersList)) {
            return -2;
        }
        Users user = usersList.get(0);
        if (!passwordEncoder.matches(updatePasswordParam.getOldPassword(), user.getUserPass())) {
            return -3;
        }
        user.setUserPass(passwordEncoder.encode(updatePasswordParam.getNewPassword()));
        baseMapper.updateById(user);
        usersCacheService.delAdminUserByUserId(user.getUsersId());
        return 1;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        // 根据用户名查询用户
        Users admin = getAdminByUsername(username);
        if (admin != null) {
            List<Resource> resourceList = getResourceList(admin.getUsersId());
            return new AdminUserDetails(admin,resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    @Override
    public Users getCurrentLoginUser() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        AdminUserDetails adminUserDetails = (AdminUserDetails)auth.getPrincipal();
        return adminUserDetails.getUsers();
    }

    @Override
    public Long getCurrentUserId() {
        return getCurrentLoginUser().getUsersId();
    }

    @Override
    public List<Resource> getResourceList(Long adminId) {
        log.info("根据用户{}找到资源", adminId);
        List<Resource> resourceList = usersCacheService.getResourceListByUserId(adminId);

        log.info("Redis 用户关联的角色{}", resourceList);
        if(CollUtil.isNotEmpty(resourceList)){
            return  resourceList;
        }

        resourceList = adminRoleRelationMapper.getResourceList(adminId);
        log.info("根据用户获取数据库中的资源大小{}, 内容{}", resourceList.size(), resourceList);
        if(CollUtil.isNotEmpty(resourceList)){
            usersCacheService.setResourceList(adminId,resourceList);
        }
        return resourceList;
    }

    @Override
    public List<Role> getRoleList(Long adminId) {
        return adminRoleRelationMapper.getRoleList(adminId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateRole(Long adminId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        //先删除原来的关系

        QueryWrapper<AdminRoleRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("users_id",adminId);
        adminRoleRelationMapper.delete(queryWrapper);

        //建立新关系
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<AdminRoleRelation> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                AdminRoleRelation roleRelation = new AdminRoleRelation();
                roleRelation.setUsersId(adminId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }
            adminRoleRelationMapper.insertList(list);
        }
        usersCacheService.delResourceListByUserId(adminId);
        return count;
    }

    @Override
    public IPage<Users> findByPage(UsersPageQueryParam param) {
        QueryWrapper<UsersPageQueryParam> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(param.getUserLogin())){
            queryWrapper.like("user_login",param.getUserLogin());
        }
        if(StringUtils.isNotEmpty(param.getUserNicename())){
            queryWrapper.like("user_nicename",param.getUserNicename());
        }
        if(param.getRoleId()!=null){
            queryWrapper.like("b.role_id",param.getRoleId());
        }
        Page<Users> usersPage = new Page<>(param.getPage(), param.getPageSize());
        IPage<Users> usersIPage = usersMapper.findByPage(usersPage,queryWrapper);
        return usersIPage;
    }

    @Override
    public boolean removeUser(Long usersId) {
        QueryWrapper<AdminRoleRelation> query = new QueryWrapper<>();
        query.eq("users_id",usersId);
        adminRoleRelationMapper.delete(query);
        return this.removeById(usersId);
    }
}
