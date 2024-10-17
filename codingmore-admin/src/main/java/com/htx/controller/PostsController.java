package com.htx.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htx.dto.PostsPageQueryParam;
import com.htx.dto.PostsParam;
import com.htx.service.IPostsService;
import com.htx.service.IUsersService;
import com.htx.vo.PostsVo;
import com.htx.webapi.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/17 22:19
 * @Desc: 文章 前端控制器
 */
@Controller
@Api(tags = "文章 ")
@RequestMapping("/posts")
public class PostsController {
    @Autowired
    private IPostsService postsService;

    @Autowired
    private IUsersService iUsersService;

    /**
     *
     * @param postsParam
     * @param result 将通知注入到 BindingResult 对象，否则 Hibernate Validator不起效
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("添加文章")
    public ResultObject<String> insert(@Valid PostsParam postsParam, BindingResult result) {
        // TODO 定时发布
        postsParam.setPostDate(null);
        postsService.savePosts(postsParam);
        return ResultObject.success("保存成功");
    }

    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("根据id获取文章")
    public ResultObject<PostsVo> getById(long postsId) {
        return ResultObject.success(postsService.getPostsById(postsId));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("更新")
    public ResultObject<String> update(@Valid PostsParam postsParam) {
        // TODO 定时发布
        postsParam.setPostDate(null);
        postsService.updatePosts(postsParam);
        return ResultObject.success("更新成功");
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("删除")
    public ResultObject<String> delete(long postsId) {
        return ResultObject.success(postsService.removePostsById(postsId) ? "删除成功" : "删除失败");
    }

    @RequestMapping(value = "/queryPageable", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("分页查询")
    public ResultObject<Map<String, Object>> queryPageable(PostsPageQueryParam postsPageQueryParam) {
        Map<String, Object> map = new HashMap<>();
        IPage<PostsVo> postsIPage = postsService.findByPage(postsPageQueryParam);
        map.put("items", postsIPage.getRecords());
        map.put("total", postsIPage.getTotal());
        return ResultObject.success(map);
    }


    @RequestMapping(value = "/insertPostTermTaxonomy", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("添加文章栏目关联关系")
    public ResultObject<String> insertPostTermTaxonomy(Long[] postsIds, Long[] termTaxonomyIds) {
        return ResultObject.success(postsService.insertPostTermTaxonomy(postsIds,termTaxonomyIds) > 0? "保存成功" : "保存失败");
    }

    @RequestMapping(value = "/setOnTop", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("置顶方法")
    public ResultObject<String> setOnTop(Long postsId) {
        postsService.setOnTop(postsId, 1);
        return ResultObject.success("操作成功");
    }

    @RequestMapping(value = "/cancelOnTop", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("取消置顶方法")
    public ResultObject<String> cancelOnTop(Long postsId) {
        postsService.setOnTop(postsId, 0);
        return ResultObject.success("操作成功");
    }

    @RequestMapping(value = "/uploadMd",method=RequestMethod.POST)
    @ResponseBody
    @ApiOperation("上传")
    public ResultObject<Map<String, Object>> uploadMd(@RequestParam("file") MultipartFile file, HttpServletRequest req)  {
        Map<String, Object> map = new HashMap<>();
        String content = postsService.uploadMd(file);
        map.put("content", content);
        map.put("postTitle",file.getOriginalFilename());
        return ResultObject.success(map);
    }
}
