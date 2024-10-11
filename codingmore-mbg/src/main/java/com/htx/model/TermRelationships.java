package com.htx.model;

import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/11 20:01
 * @Desc: 文章栏目关系表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TermRelationships对象", description="文章栏目关系表")
public class TermRelationships implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "对应文章ID/链接ID")
    @MppMultiId
    private Long termRelationshipsId;

    @ApiModelProperty(value = "栏目ID")
    @MppMultiId
    private Long termTaxonomyId;

    @ApiModelProperty(value = "排序")
    private Integer termOrder;

    @ApiModelProperty(value = "类型,0:文章内容,1:文章链接，2:栏目链接")
    @MppMultiId
    private Integer type;
}
