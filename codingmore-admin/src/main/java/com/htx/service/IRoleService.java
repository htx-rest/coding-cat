package com.htx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.htx.dto.RolePageQueryParam;
import com.htx.model.Menu;
import com.htx.model.Resource;
import com.htx.model.Role;
import com.htx.vo.RoleVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/13 15:37
 * @Desc: 后台用户角色表 服务类
 */
public interface IRoleService extends IService<Role> {
    /**
     * 根据管理员ID获取对应菜单
     */
    List<Menu> getMenuList(Long userId);

    /**
     * 获取角色相关菜单
     */
    List<Menu> listMenu(Long roleId);

    /**
     * 获取角色相关资源
     */
    List<Resource> listResource(Long roleId);

    /**
     * 给角色分配菜单
     */
    @Transactional
    int allocMenu(Long roleId, List<Long> menuIds);

    /**
     * 给角色分配资源
     */
    @Transactional
    int allocResource(Long roleId, List<Long> resourceIds);

    /**
     * 自定义分页查询
     *
     */
    IPage<RoleVo> findByPage(RolePageQueryParam param);

    boolean batchRemove(List<Long> roleIds);
}
