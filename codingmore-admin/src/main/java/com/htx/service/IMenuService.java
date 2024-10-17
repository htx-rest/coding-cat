package com.htx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.htx.dto.MenuNode;
import com.htx.model.Menu;

import java.util.List;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/17 21:15
 * @Desc: 后台菜单表 服务类
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 树形结构返回所有菜单列表
     */
    List<MenuNode> treeList();

}
