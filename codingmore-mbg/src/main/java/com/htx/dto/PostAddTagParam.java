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
 * @Date: 2024/10/11 20:27
 * @Desc:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="PostTag对象", description="标签表")
public class PostAddTagParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标签名称")
    @NotBlank(message="标签不能为空")
    private String description;

    @ApiModelProperty(value = " 对应文章ID")
    private Long postId;

    @ApiModelProperty(value = "排序")
    private int termOrder;
}
