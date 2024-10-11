package com.htx.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/11 19:52
 * @Desc: 后台角色菜单关系表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="RoleMenuRelation对象", description="后台角色菜单关系表")
public class RoleMenuRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_menu_relation_id", type = IdType.AUTO)
    private Long roleMenuRelationId;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "菜单ID")
    private Long menuId;
}
