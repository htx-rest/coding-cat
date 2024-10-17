package com.htx.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htx.dto.RolePageQueryParam;
import com.htx.dto.RoleParam;
import com.htx.model.Menu;
import com.htx.model.Resource;
import com.htx.model.Role;
import com.htx.service.IRoleService;
import com.htx.vo.RoleVo;
import com.htx.webapi.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
* @Date: 2024/10/17 22:59
* @Desc: 后台用户角色表 前端控制器
*/
@Controller
@RequestMapping("/role")
@Api(tags = "角色")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @ApiOperation("添加角色")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject<String> create(@Valid RoleParam roleParam) {
        if (roleParam.getCreateTime() == null) {
            roleParam.setCreateTime(new Date());
        }
        if (roleParam.getStatus() != 0 && roleParam.getStatus() != 1) {
            return ResultObject.failed("状态不合法");
        }
        Role role = new Role();
        BeanUtils.copyProperties(roleParam, role);
        return ResultObject.success(roleService.save(role) ? "添加成功" : "添加失败");
    }

    @ApiOperation("修改角色")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject<String> update(@Valid RoleParam roleParam) {
        if (roleParam.getStatus() != 0 && roleParam.getStatus() != 1) {
            return ResultObject.failed("状态不合法");
        }
        if (roleParam.getRoleId() == null) {
            return ResultObject.failed("角色id不能为空");
        }
        Role role = roleService.getById(roleParam.getRoleId());
        BeanUtils.copyProperties(roleParam, role);
        return ResultObject.success(roleService.updateById(role) ? "修改成功" : "修改失败");
    }

    @ApiOperation("批量删除角色")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject<String> delete(@RequestParam("ids") Long[] ids) {
        return ResultObject.success( roleService.batchRemove(CollectionUtil.toList(ids)) ? "删除成功" : "删除失败");
    }

    @ApiOperation("获取所有角色")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public ResultObject<List<Role>> listAll() {
        return ResultObject.success( roleService.list());
    }

    @ApiOperation("根据角色名称分页获取角色列表")
    @RequestMapping(value = "/queryPageable", method = RequestMethod.GET)
    @ResponseBody
    public ResultObject<Map<String,Object>> queryPageable(RolePageQueryParam rolePageQueryParam) {
        Map<String,Object> map = new HashMap<>();
        IPage<RoleVo> postTagIPage = roleService.findByPage(rolePageQueryParam);
        map.put("items",postTagIPage.getRecords());
        map.put("total",postTagIPage.getTotal());
        return ResultObject.success(map);
    }

    @ApiOperation("修改角色状态")
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject updateStatus(@RequestParam(value = "id") Long id, @RequestParam(value = "status") Integer status) {
        if (status != 0 && status != 1) {
            return ResultObject.failed("状态不合法");
        }
        Role role = roleService.getById(id);
        if(role == null){
            return ResultObject.failed("角色不存在");
        }
        role.setStatus(status);
        return ResultObject.success(roleService.updateById(role) ? "修改成功" : "修改失败");

    }

    @ApiOperation("获取角色相关菜单")
    @RequestMapping(value = "/listMenu", method = RequestMethod.GET)
    @ResponseBody
    public ResultObject<List<Menu>> listMenu(@RequestParam Long roleId) {
        List<Menu> roleList = roleService.listMenu(roleId);
        return ResultObject.success(roleList);
    }

    @ApiOperation("获取角色相关资源")
    @RequestMapping(value = "/listResource", method = RequestMethod.GET)
    @ResponseBody
    public ResultObject<List<Resource>> listResource(@RequestParam Long roleId) {
        List<Resource> roleList = roleService.listResource(roleId);
        return ResultObject.success(roleList);
    }

    @ApiOperation("给角色分配菜单")
    @RequestMapping(value = "/allocMenu", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject<String> allocMenu(@RequestParam Long roleId, @RequestParam List<Long> menuIds) {
        int count = roleService.allocMenu(roleId, menuIds);
        return ResultObject.success(count>0?"分配成功":"分配失败");
    }

    @ApiOperation("给角色分配资源")
    @RequestMapping(value = "/allocResource", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject<String> allocResource(@RequestParam Long roleId, @RequestParam List<Long> resourceIds) {
        int count = roleService.allocResource(roleId, resourceIds);
        return ResultObject.success(count>0?"分配成功":"分配失败");
    }
}
