package com.htx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/11 20:32
 * @Desc: 资源分类表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ResourceCategory对象", description="资源分类表")
public class ResourceCategoryParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long resourceCategoryId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "分类名称")
    @NotBlank(message="分类名称不能为空")
    private String name;

    @ApiModelProperty(value = "排序")
    @NotNull(message="排序不能为空")
    private Integer sort;
}
