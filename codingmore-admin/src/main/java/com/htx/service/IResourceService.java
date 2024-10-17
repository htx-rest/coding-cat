package com.htx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.htx.model.Resource;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/13 15:04
 * @Desc: 后台资源表 服务类
 */
public interface IResourceService extends IService<Resource> {
    boolean remove(Long resourceId);
}
