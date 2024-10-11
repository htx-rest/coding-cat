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
 * @Date: 2024/10/11 19:55
 * @Desc: 后台角色资源关系表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="RoleResourceRelation对象", description="后台角色资源关系表")
public class RoleResourceRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_resource_relation_id", type = IdType.AUTO)
    private Long roleResourceRelationId;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "资源ID")
    private Long resourceId;
}
