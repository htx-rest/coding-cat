package com.htx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/11 20:38
 * @Desc: 站点
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Site对象", description="站点")
public class SiteParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("站点名称")
    @NotBlank(message = "站点名称不能为空")
    private String siteName;

    @ApiModelProperty("站点介绍")
    private String siteDesc;

    @NotBlank(message = "关键字不能为空")
    @ApiModelProperty("关键字")
    private String keywords;

    @ApiModelProperty("属性")
    private String attribute;
}
