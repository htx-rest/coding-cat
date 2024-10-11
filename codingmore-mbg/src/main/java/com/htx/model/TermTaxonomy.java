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

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/11 20:02
 * @Desc: 栏目
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TermTaxonomy对象", description="栏目")
@TableName(autoResultMap = true)
public class TermTaxonomy implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "term_taxonomy_id", type = IdType.AUTO)
    private Long termTaxonomyId;

    @ApiModelProperty(value = "栏目名称")
    private String name;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "父栏目id")
    private Long parentId;

    @TableField("create_user_id")
    @ApiModelProperty("创建用户id")
    private Long createUserId;

    @TableField("create_time")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @TableField("update_time")
    @ApiModelProperty("修改时间")
    private Date updateTime;

    @TableField(typeHandler = JacksonTypeHandler.class)
    @ApiModelProperty("属性")
    private Map<String,Object> attribute;
}
