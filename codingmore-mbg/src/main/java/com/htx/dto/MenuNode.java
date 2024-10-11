package com.htx.dto;

import com.htx.model.Menu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/11 19:36
 * @Desc: 后台菜单节点封装
 */
@Data
public class MenuNode extends Menu {
    @ApiModelProperty(value = "子级菜单")
    private List<MenuNode> children;
}
