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
 * @Date: 2024/10/11 19:46
 * @Desc: 标签文章关系表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="PostTagRelation对象", description="标签文章关系表")
public class PostTagRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "对应文章ID")
    @MppMultiId
    private Long postId;

    @ApiModelProperty(value = "标签ID")
    @MppMultiId
    private Long postTagId;

    @ApiModelProperty(value = "排序")
    private Integer termOrder;
}
