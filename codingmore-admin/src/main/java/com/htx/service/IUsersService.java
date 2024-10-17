package com.htx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.htx.dto.UpdateAdminPasswordParam;
import com.htx.dto.UsersPageQueryParam;
import com.htx.model.Resource;
import com.htx.model.Role;
import com.htx.model.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/13 15:04
 * @Desc: 用户表 服务类
 */
public interface IUsersService extends IService<Users> {
    /**
     * 根据用户名获取后台管理员
     */
    Users getAdminByUsername(String username);

    /**
     * 注册功能
     */
    boolean register(Users users);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username,String password);

    /**
     * 刷新token的功能
     * @param oldToken 旧的token
     */
    String refreshToken(String oldToken);

    /**
     * 修改密码
     */
    int updatePassword(UpdateAdminPasswordParam updatePasswordParam);

    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 获取当前登录用户
     */
    Users getCurrentLoginUser();

    /**
     * 获取当前登录用户id
     */
    Long getCurrentUserId();


    List<Resource> getResourceList(Long adminId);

    /**
     * 获取用户对于角色
     */
    List<Role> getRoleList(Long adminId);

    /**
     * 修改用户角色关系
     */
    @Transactional
    int updateRole(Long adminId, List<Long> roleIds);


    /**
     * 自定义分页查询
     *
     */
    IPage<Users> findByPage(UsersPageQueryParam param);

    boolean removeUser(Long usersId);
}
