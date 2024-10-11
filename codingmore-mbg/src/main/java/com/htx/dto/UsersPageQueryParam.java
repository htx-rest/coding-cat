package com.htx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/11 20:43
 * @Desc:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Users对象", description="用户")
public class UsersPageQueryParam {
    long pageSize;

    long page;

    @ApiModelProperty("用户名")
    String userLogin;

    @ApiModelProperty("昵称")
    String userNicename;

    @ApiModelProperty("角色id")
    Long roleId;
}
