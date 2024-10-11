package com.htx.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.htx.dto.RolePageQueryParam;
import com.htx.model.Menu;
import com.htx.model.Resource;
import com.htx.model.Role;
import com.htx.vo.RoleVo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/11 20:56
 * @Desc: 后台用户角色表 Mapper 接口
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据后台用户ID获取菜单
     */
    List<Menu> getMenuList(@Param("usersId") Long usersId);

    /**
     * 根据角色ID获取菜单
     */
    List<Menu> getMenuListByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据角色ID获取资源
     */
    List<Resource> getResourceListByRoleId(@Param("roleId") Long roleId);

    IPage<RoleVo> findByPage(IPage<RoleVo> page, @Param(Constants.WRAPPER) Wrapper<RolePageQueryParam> wrapper);
}
