package com.htx.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/11 19:45
 * @Desc: 标签表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="PostTag对象", description="标签表")
public class PostTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "post_tag_id")
    @TableId(value = "post_tag_id", type = IdType.AUTO)
    private Long postTagId;

    @ApiModelProperty(value = "标签名称")
    private String description;
}
