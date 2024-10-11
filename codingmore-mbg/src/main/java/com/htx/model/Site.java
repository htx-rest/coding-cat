package com.htx.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Map;
import java.util.Date;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/11 19:57
 * @Desc: 站点
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Site对象", description="站点")
@TableName(autoResultMap = true)
public class Site implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "site_id", type = IdType.AUTO)
    private Long siteId;

    @TableField("site_name")
    @ApiModelProperty("站点名称")
    @NotBlank(message = "站点名称不能为空")
    private String siteName;

    @TableField("site_desc")
    @ApiModelProperty("站点介绍")
    private String siteDesc;

    @TableField("keywords")
    @NotBlank(message = "关键字不能为空")
    @ApiModelProperty("关键字")
    private String keywords;

    @TableField("update_time")
    @ApiModelProperty("修改时间")
    private Date updateTime;

    @TableField(typeHandler = JacksonTypeHandler.class)
    @ApiModelProperty("属性")
    private Map<String,Object> attribute;
}
