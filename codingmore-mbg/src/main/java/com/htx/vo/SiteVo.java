package com.htx.vo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/11 20:24
 * @Desc:
 */
@Data
public class SiteVo {

    private Long siteId;

    @ApiModelProperty("站点名称")
    private String siteName;

    @ApiModelProperty("站点介绍")
    private String siteDesc;

    @ApiModelProperty("关键字")
    private String keywords;

    @ApiModelProperty("修改时间")
    private Date updateTime;

    @ApiModelProperty("属性")
    private Map<String,Object> attribute;
    private String attributeStr;

    public String getAttributeStr() {
        if(attribute != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return  objectMapper.writeValueAsString(attribute);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return attributeStr;
    }
}
