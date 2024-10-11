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
 * @Date: 2024/10/11 20:39
 * @Desc: 栏目
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TermTaxonomy对象", description="栏目")
public class TermTaxonomyParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")

    private Long termTaxonomyId;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "名称")
    @NotBlank(message="名称不能为空")
    private String name;

    @ApiModelProperty(value = "父栏目id")
    private Long parentId;

    @ApiModelProperty("属性")
    private String attribute;

    private String type;
}
