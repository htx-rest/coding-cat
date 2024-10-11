package com.htx.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/11 20:36
 * @Desc:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Role对象", description="角色")
public class RolePageQueryParam {
    long pageSize;

    long page;

    @ApiModelProperty(value = "关键字")
    String keyword;
}
