package com.htx.controller;

import com.htx.dto.PostsPageQueryParam;
import com.htx.service.IPostsService;
import com.htx.vo.PostsVo;
import com.htx.webapi.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/18 22:28
 * @Desc: 文章 服务类
 */
@Controller
@Api(tags = "学习网站rest接口")
@RequestMapping("/rest")
public class RestController {

    @Autowired
    private IPostsService postsService;

    @RequestMapping(value = "/postPageQuery", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("内容分页查询")
    public ResultObject<Map<String, Object>> queryPageable(PostsPageQueryParam postsPageQueryParam) {
        Map<String, Object> map = new HashMap<>();
        List<PostsVo> postsIPage = postsService.findByPageWithTagPaged(postsPageQueryParam);
        map.put("items", postsIPage);
        return ResultObject.success(map);
    }

    @RequestMapping(value = "/clickLike", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("点赞方法")
    public ResultObject<Map<String, Object>> giveThumbsUp(HttpServletRequest request, @NotNull @RequestParam Long postsId) {
        Map<String, Object> map = new HashMap<>();
        Boolean hasClicked = postsService.hasClickedLike(postsId, request);
        map.put("hasClicked", hasClicked);
        postsService.increaseLikeCount(postsId, request);
        Integer newestCount = postsService.getLikeCount(postsId);
        map.put("newestCount", newestCount);
        return ResultObject.success(map);
    }

    @RequestMapping(value = "/getLikeCount", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("获取点赞数量的方法")
    public ResultObject<Integer> getLikeCount(@NotNull @RequestParam Long postsId) {
        Integer newestCount = postsService.getLikeCount(postsId);
        return ResultObject.success(newestCount);
    }
}
