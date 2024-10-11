package com.htx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/11 20:40
 * @Desc: 修改用户名密码参数
 */
@Data
@ApiModel(value="用户登录", description="用户表")
public class UpdateAdminPasswordParam {

    @NotEmpty(message = "旧密码不能为空")
    @ApiModelProperty(value = "旧密码", required = true)
    private String oldPassword;

    @NotEmpty(message = "新密码不能为空")
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;
}
