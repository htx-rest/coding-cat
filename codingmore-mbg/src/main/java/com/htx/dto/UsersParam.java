package com.htx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/11 20:44
 * @Desc: 用户表
 */
@Data
@ApiModel(value="Users对象", description="用户表")
public class UsersParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登录名")
    @NotBlank(message="登录名不能为空")
    private String userLogin;

    @ApiModelProperty(value = "密码")
    @NotBlank(message="密码不能为空")
    private String userPass;

    @ApiModelProperty(value = "昵称")
    @NotBlank(message="昵称不能为空")
    private String userNicename;

    @ApiModelProperty(value = "Email")
    private String userEmail;

    @ApiModelProperty("属性")
    private String attribute;

    @ApiModelProperty(value = "网址")
    private String userUrl;

    @ApiModelProperty(value = "图像")
    private String displayName;
}
