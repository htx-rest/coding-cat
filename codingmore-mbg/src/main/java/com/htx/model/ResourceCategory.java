package com.htx.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.io.Serializable;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/11 19:48
 * @Desc: 资源分类表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ResourceCategory对象", description="资源分类表")
public class ResourceCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "resource_category_id", type = IdType.AUTO)
    private Long resourceCategoryId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "排序")
    private Integer sort;
}
