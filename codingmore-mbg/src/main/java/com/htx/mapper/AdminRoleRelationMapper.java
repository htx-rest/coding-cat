package com.htx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.htx.model.AdminRoleRelation;
import com.htx.model.Resource;
import com.htx.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @Author: htx
* @GZH: 二哈学习之路
* @Date: 2024/10/11 20:46
* @Desc: 后台用户和角色关系表 Mapper 接口
*/
public interface AdminRoleRelationMapper extends BaseMapper<AdminRoleRelation> {
    /**
     * 批量插入用户角色关系
     */
    int insertList(@Param("list") List<AdminRoleRelation> adminRoleRelationList);

    /**
     * 获取用于所有角色
     */
    List<Role> getRoleList(@Param("usersId") Long usersId);

    /**
     * 获取用户所有可访问资源
     */
    List<Resource> getResourceList(@Param("usersId") Long usersId);

    /**
     * 获取资源相关用户ID列表
     */
    List<Long> getAdminIdList(@Param("resourceId") Long resourceId);
}
