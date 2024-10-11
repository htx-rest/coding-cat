package com.htx.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/11 20:03
 * @Desc: 用户表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Users对象", description="用户表")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "users_id", type = IdType.AUTO)
    private Long usersId;

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

    @ApiModelProperty(value = "网址")
    private String userUrl;

    @ApiModelProperty(value = "注册时间")
    private Date userRegistered;

    @ApiModelProperty(value = "激活码")
    private String userActivationKey;

    @ApiModelProperty(value = "用户状态")
    private Integer userStatus;

    @ApiModelProperty(value = "图像")
    private String displayName;

    @ApiModelProperty(value = "用户类型 0 :后台 1：前端")
    private Integer userType;

    @ApiModelProperty(value = "openid")
    private String openId;

    @TableField(typeHandler = JacksonTypeHandler.class)
    @ApiModelProperty("属性")
    private Map<String,Object> attribute;
}
