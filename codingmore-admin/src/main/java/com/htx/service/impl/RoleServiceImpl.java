package com.htx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htx.dto.RolePageQueryParam;
import com.htx.mapper.RoleMapper;
import com.htx.model.*;
import com.htx.service.IRoleMenuRelationService;
import com.htx.service.IRoleResourceRelationService;
import com.htx.service.IRoleService;
import com.htx.service.IUsersCacheService;
import com.htx.vo.RoleVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/13 21:59
 * @Desc: 后台用户角色表 服务实现类
 */
@Service
@Slf4j
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private IRoleMenuRelationService roleMenuRelationService;

    @Autowired
    private IRoleResourceRelationService roleResourceRelationService;

    @Autowired
    private IUsersCacheService usersCacheService;

    @Override
    public List<Menu> getMenuList(Long userId) {
        return roleMapper.getMenuList(userId);
    }

    @Override
    public List<Menu> listMenu(Long roleId) {
        return roleMapper.getMenuListByRoleId(roleId);
    }

    @Override
    public List<Resource> listResource(Long roleId) {
        return roleMapper.getResourceListByRoleId(roleId);
    }

    @Override
    @Transactional
    public int allocMenu(Long roleId, List<Long> menuIds) {
        //先删除原有关系
        QueryWrapper<RoleMenuRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        roleMenuRelationService.remove(queryWrapper);

        ArrayList<RoleMenuRelation> relationList = new ArrayList<>();
        //批量插入新关系
        for (Long menuId : menuIds) {
            RoleMenuRelation relation = new RoleMenuRelation();
            relation.setRoleId(roleId);
            relation.setMenuId(menuId);
            relationList.add(relation);
        }
        roleMenuRelationService.saveBatch(relationList);
        usersCacheService.delResourceListByRoleId(roleId);
        return menuIds.size();
    }

    @Override
    @Transactional
    public int allocResource(Long roleId, List<Long> resourceIds) {
        log.info("先删除原有关系，角色{}，资源{}", roleId, resourceIds);
        QueryWrapper<RoleResourceRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        roleResourceRelationService.remove(queryWrapper);

        List<RoleResourceRelation> relationList = new ArrayList<>();
        //批量插入新关系
        for (Long resourceId : resourceIds) {
            RoleResourceRelation relation = new RoleResourceRelation();
            relation.setRoleId(roleId);
            relation.setResourceId(resourceId);
            relationList.add(relation);
        }
        log.info("批量插入角色和资源的关系{}", relationList);
        roleResourceRelationService.saveBatch(relationList);

        usersCacheService.delResourceListByRoleId(roleId);
        return resourceIds.size();
    }

    @Override
    public IPage<RoleVo> findByPage(RolePageQueryParam param) {
        QueryWrapper<RolePageQueryParam> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(param.getKeyword())){
            queryWrapper.like("name",param.getKeyword());
        }
        Page<RoleVo> postsPage = new Page<>(param.getPage(), param.getPageSize());
        return roleMapper.findByPage(postsPage,queryWrapper);
    }

    @Override
    public boolean batchRemove(List<Long> roleIds) {
        usersCacheService.delResourceListByRoleIds(roleIds);
        return  this.removeByIds(roleIds);
    }
}
