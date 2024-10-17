package com.htx.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.htx.dto.SiteParam;
import com.htx.model.Site;
import com.htx.service.ISiteService;
import com.htx.webapi.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @Author: htx
* @GZH: 二哈学习之路
* @Date: 2024/10/17 23:01
* @Desc: 站点 前端控制器
*/
@Controller
@Api(tags = "站点")
@RequestMapping("/site")
public class SiteController {
    @Autowired
    private ISiteService siteService;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("添加站点")
    public ResultObject<String> insert(@Valid SiteParam siteParam) throws JsonProcessingException {
        int count = siteService.count();
        if (count > 0) {
            return ResultObject.failed("只能有一条配置信息");
        }

        Site site = new Site();
        BeanUtils.copyProperties(siteParam, site);
        handleAttribute(siteParam, site);
        return ResultObject.success(siteService.save(site) ? "保存成功" : "保存失败");
    }

    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("获取站点配置")
    public ResultObject<Site> getSite() {
        int count = siteService.count();
        if (count == 0) {
            return ResultObject.failed("没有配置信息");
        }
        List<Site> siteList = siteService.list();
        return ResultObject.success(siteList.get(0));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("更新")
    public ResultObject<String> update(@Valid SiteParam siteParam) throws JsonProcessingException {
        List<Site> siteList = siteService.list();
        if (siteList.size() == 0) {
            return ResultObject.failed("请初始化站点数据");
        }
        Site site = siteService.getById(siteList.get(0).getSiteId());
        BeanUtils.copyProperties(siteParam, site);
        site.setUpdateTime(new Date());
        handleAttribute(siteParam, site);
        return ResultObject.success(siteService.updateById(site) ? "更新成功" : "更新失败");
    }

    /**
     * 处理扩展字段
     */
    private void handleAttribute(SiteParam siteParam, Site site) throws JsonProcessingException {
        if (StringUtils.isNotBlank(siteParam.getAttribute())) {
            Map attribute = objectMapper.readValue(siteParam.getAttribute(), Map.class);
            site.setAttribute(attribute);
        }
    }
}
