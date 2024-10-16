package com.htx.controller;

import com.htx.service.ILearnWebRequestStrategy;
import com.htx.util.WebRequestParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/18 22:24
 * @Desc:
 */
@Controller
@Api(tags = "学习网站前端入口")
public class LearnWebFacadeController {
    private static Logger LOGGER = LoggerFactory.getLogger(LearnWebFacadeController.class);

    @Resource(name = "channelPageRequestStrategy")
    private ILearnWebRequestStrategy channelPageRequestStrategy;

    @Resource(name = "contentPageRequestStrategy")
    private ILearnWebRequestStrategy contentPageRequestStrategy;

    @Resource(name = "indexPageRequestStrategy")
    private ILearnWebRequestStrategy indexPageRequestStrategy;

    @RequestMapping(value = {"/index.html","/"}, method = RequestMethod.GET)
    @ApiOperation("首页页入口")
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        WebRequestParam webRequestParam = new WebRequestParam.Builder().request(request).response(response).model(model).build();
        return indexPageRequestStrategy.handleRequest(webRequestParam);
    }

    @ApiOperation("内容动态页入口")
    @RequestMapping(value = {"/{postId:[0-9]+}.html"}, method = RequestMethod.GET)
    public String post(@PathVariable Long postId, HttpServletRequest request,
                       HttpServletResponse response, ModelMap model) {
        WebRequestParam webRequestParam = new WebRequestParam.Builder().request(request).response(response).postId(postId).model(model).build();
        return contentPageRequestStrategy.handleRequest(webRequestParam);
    }


    @ApiOperation("内容动态页入口")
    @RequestMapping(value = {"/{channelId:[0-9]+}/{postId:[0-9]+}.html"}, method = RequestMethod.GET)
    public String content(@PathVariable Long channelId, @PathVariable Long postId, HttpServletRequest request,
                          HttpServletResponse response, ModelMap model) {
        WebRequestParam webRequestParam = new WebRequestParam.Builder().request(request).response(response).channelId(channelId).postId(postId).model(model).build();
        return contentPageRequestStrategy.handleRequest(webRequestParam);
    }


    @ApiOperation("内容动态分页入口")
    @RequestMapping(value = {"/{channelId:[0-9]+}/postpage_{page:[0-9]+}.html"}, method = RequestMethod.GET)
    public String contentPage( @PathVariable Long channelId, @PathVariable Integer page,
                               HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        WebRequestParam webRequestParam = new WebRequestParam.Builder().request(request).response(response).channelId(channelId).page(page).model(model).page(page).build();
        return contentPageRequestStrategy.handleRequest(webRequestParam);
    }

    /**
     * 栏目动态分页入口
     */
    @ApiOperation("栏目动态分页入口")
    @RequestMapping(value = {"/{channelId:[0-9]+}_{page:[0-9]+}.html"}, method = RequestMethod.GET)
    public String channelPage( @PathVariable Long channelId,  @PathVariable Integer page, HttpServletRequest request,
                               HttpServletResponse response, ModelMap model) /*throws GlobalException*/ {
        WebRequestParam webRequestParam = new WebRequestParam.Builder().request(request).response(response).channelId(channelId).model(model).page(page).build();
        return channelPageRequestStrategy.handleRequest(webRequestParam);
    }
}
