package com.htx.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.htx.component.DynamicSecurityMetadataSource;
import com.htx.dto.ResourceParam;
import com.htx.model.Resource;
import com.htx.model.RoleResourceRelation;
import com.htx.service.IResourceService;
import com.htx.service.IRoleResourceRelationService;
import com.htx.webapi.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Author: htx
* @GZH: 二哈学习之路
* @Date: 2024/10/17 22:56
* @Desc: 后台资源表 前端控制器
*/
@Controller
@RequestMapping("/resource")
@Api(tags = "资源")
public class ResourceController {
    @Autowired
    private IResourceService resourceService;

    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    @Autowired
    private IRoleResourceRelationService roleResourceRelationService;

    @ApiOperation("添加后台资源")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject<String> create(@Valid ResourceParam requestParam) {
        Resource resource = new Resource();
        BeanUtils.copyProperties(requestParam, resource);
        if (resource.getCreateTime() == null) {
            resource.setCreateTime(new Date());
        }
        return ResultObject.success(resourceService.save(resource) ? "添加成功" : "添加失败");
    }

    @ApiOperation("修改后台资源")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject<String> update(ResourceParam requestParam) {
        if (requestParam.getResourceId() == null) {
            return ResultObject.failed("id不能为空");
        }
        Resource resource = resourceService.getById(requestParam.getResourceId());
        if (resource == null) {
            return ResultObject.failed("资源不存在");
        }
        BeanUtils.copyProperties(requestParam, resource);
        return ResultObject.success(resourceService.updateById(resource) ? "修改成功" : "修改失败");

    }

    @ApiOperation("根据ID获取资源详情")
    @RequestMapping(value = "/id", method = RequestMethod.GET)
    @ResponseBody
    public ResultObject<Resource> getItem(@RequestParam Long id) {
        return ResultObject.success(resourceService.getById(id));
    }

    @ApiOperation("根据ID删除后台资源")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject<String> delete(@RequestParam Long id) {
        QueryWrapper<RoleResourceRelation> wrapper = new QueryWrapper<>();
        wrapper.eq("resource_id", id);
        int count = roleResourceRelationService.count(wrapper);
        if (count > 0) {
            return ResultObject.failed("该资源已被使用，不能删除");
        }
        return ResultObject.success(resourceService.remove(id) ? "删除成功" : "删除失败");
    }

    @ApiOperation("分页模糊查询后台资源")
    @RequestMapping(value = "/queryPageable", method = RequestMethod.GET)
    @ResponseBody
    public ResultObject<Map<String,Object>> queryPageable( Long categoryId,String nameKeyword,  String urlKeyword,long pageSize, long page) {
        Map<String,Object> map = new HashMap<>();
        Page<Resource> resourcePage = new Page<>(page,pageSize);
        QueryWrapper<Resource> resourceQueryWrapper = new QueryWrapper();
        if(categoryId!=null){
            resourceQueryWrapper.eq("category_id", categoryId);
        }
        if(StringUtils.isNotBlank(nameKeyword)){
            resourceQueryWrapper.like("name", "%"+nameKeyword+"%");
        }
        if(StringUtils.isNotBlank(urlKeyword)){
            resourceQueryWrapper.like("url", "%"+urlKeyword+"%");
        }

        IPage<Resource> postTagIPage = resourceService.page(resourcePage,resourceQueryWrapper);
        map.put("items",postTagIPage.getRecords());
        map.put("total",postTagIPage.getTotal());
        return ResultObject.success(map);
    }

    @ApiOperation("查询所有后台资源")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public ResultObject<List<Resource>> listAll() {
        return ResultObject.success(resourceService.list());
    }

}
