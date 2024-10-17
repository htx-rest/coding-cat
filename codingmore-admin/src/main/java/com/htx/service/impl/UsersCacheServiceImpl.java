package com.htx.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.htx.assist.RedisConstants;
import com.htx.mapper.AdminRoleRelationMapper;
import com.htx.model.AdminRoleRelation;
import com.htx.model.Resource;
import com.htx.model.Users;
import com.htx.service.IRedisService;
import com.htx.service.IRoleResourceRelationService;
import com.htx.service.IUsersCacheService;
import com.htx.service.IUsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/13 22:37
 * @Desc:
 */
@Service
@Slf4j
public class UsersCacheServiceImpl implements IUsersCacheService {
    @Autowired
    private IUsersService usersService;

    @Autowired
    private IRedisService redisService;

    @Autowired
    private AdminRoleRelationMapper adminRoleRelationMapper;

    @Autowired
    private IRoleResourceRelationService roleResourceRelationService;

    @Override
    public void delAdminUserByUserId(Long usersId) {
        Users admin = usersService.getById(usersId);
        if (admin != null) {
            redisService.del(RedisConstants.getAdminUserKey(admin.getUserLogin()));
        }
    }

    @Override
    public void delResourceListByUserId(Long usersId) {
        redisService.del(RedisConstants.getAdminResourceKey(usersId));
    }

    @Override
    public void delResourceListByRoleId(Long roleId) {
        log.info("Redis 根据角色 ID{} 查询 user 和 role 的关系", roleId);
        QueryWrapper<AdminRoleRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        List<AdminRoleRelation> relations = adminRoleRelationMapper.selectList(queryWrapper);
        log.info("根据角色获取到用户{}", relations);

        if (CollUtil.isNotEmpty(relations)) {
            List<String> keys = relations.stream().map(
                    relation -> RedisConstants.getAdminResourceKey(relation.getUsersId())
            ).collect(Collectors.toList());
            log.info("Redis 要删除的 keys{}", keys);
            redisService.del(keys);
        }
    }

    @Override
    public void delResourceListByRoleIds(List<Long> roleIds) {
        QueryWrapper<AdminRoleRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("role_id", roleIds);
        List<AdminRoleRelation> relations = adminRoleRelationMapper.selectList(queryWrapper);
        if (CollUtil.isNotEmpty(relations)) {
            List<String> keys = relations.stream().map(
                    relation -> RedisConstants.getAdminResourceKey(relation.getUsersId())
            ).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    @Override
    public void delResourceListByResourceId(Long resourceId) {
        List<Long> adminIdList = adminRoleRelationMapper.getAdminIdList(resourceId);
        if (CollUtil.isNotEmpty(adminIdList)) {
            List<String> keys = adminIdList.stream().map(
                    adminId -> RedisConstants.getAdminResourceKey(adminId)
            ).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    @Override
    public Users getAdminUserByUsername(String username) {
        return (Users) redisService.get(RedisConstants.getAdminUserKey(username));
    }

    @Override
    public void setAdminUser(Users users) {
        redisService.set(RedisConstants.getAdminUserKey(users.getUserLogin()), users, RedisConstants.REDIS_EXPIRE);
    }

    @Override
    public List<Resource> getResourceListByUserId(Long usersId) {
        return (List<Resource>) redisService.get(RedisConstants.getAdminResourceKey(usersId));
    }

    @Override
    public void setResourceList(Long adminId, List<Resource> resourceList) {
        redisService.set(RedisConstants.getAdminResourceKey(adminId), resourceList, RedisConstants.REDIS_EXPIRE);
    }
}
