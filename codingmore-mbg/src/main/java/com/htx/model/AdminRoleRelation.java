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
 * @Date: 2024/10/11 19:38
 * @Desc: 后台用户和角色关系表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="AdminRoleRelation对象", description="后台用户和角色关系表")
public class AdminRoleRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "admin_role_relation_id", type = IdType.AUTO)
    private Long adminRoleRelationId;

    @ApiModelProperty(value = "用户id")
    private Long usersId;

    @ApiModelProperty(value = "角色id")
    private Long roleId;
}

