package com.htx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.htx.model.PostTag;

import java.util.List;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/18 22:34
 * @Desc: 标签表 服务类
 */
public interface IPostTagService extends IService<PostTag> {
    /**
     * 获取文章标签
     * @param postId
     * @return
     */
    List<PostTag> getByPostId(Long postId);
}
