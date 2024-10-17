package com.htx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.htx.dto.PostAddTagParam;
import com.htx.model.PostTag;

import java.util.List;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/17 22:32
 * @Desc: 标签表 服务类
 */
public interface IPostTagService extends IService<PostTag> {

    boolean savePostTag(PostAddTagParam postAddTagParam);

    /**
     * 获取文章标签
     * @param postId
     * @return
     */
    List<PostTag> getByPostId(Long postId);

    /**
     * 删除标签
     * @param postTagId
     * @return
     */
    boolean removeTag(Long postTagId);
}
