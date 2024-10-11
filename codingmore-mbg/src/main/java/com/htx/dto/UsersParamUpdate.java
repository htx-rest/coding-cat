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
public class UsersParamUpdate implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long usersId;

    @ApiModelProperty(value = "昵称")
    @NotBlank(message="昵称不能为空")
    private String userNicename;

    @ApiModelProperty(value = "Email")
    private String userEmail;

    @ApiModelProperty(value = "网址")
    private String userUrl;

    @ApiModelProperty(value = "图像")
    private String displayName;
}
