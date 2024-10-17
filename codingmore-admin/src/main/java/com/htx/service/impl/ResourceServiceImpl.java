package com.htx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htx.mapper.ResourceMapper;
import com.htx.model.Resource;
import com.htx.service.IResourceService;
import com.htx.service.IUsersCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/17 22:54
 * @Desc: 后台资源表 服务实现类
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {
    @Autowired
    private IUsersCacheService usersCacheService;

    @Override
    public boolean remove(Long resourceId) {
        usersCacheService.delResourceListByResourceId(resourceId);
        return this.removeById(resourceId);
    }
}
