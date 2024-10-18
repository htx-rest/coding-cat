package com.htx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htx.mapper.PostTagMapper;
import com.htx.model.PostTag;
import com.htx.model.PostTagRelation;
import com.htx.service.IPostTagRelationService;
import com.htx.service.IPostTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/18 22:42
 * @Desc: 标签表 服务实现类
 */
@Service
public class PostTagServiceImpl extends ServiceImpl<PostTagMapper, PostTag> implements IPostTagService {
    @Autowired
    private IPostTagRelationService postTagRelationService;

    @Override
    public List<PostTag> getByPostId(Long postId) {
        QueryWrapper<PostTagRelation> postTagRelationQueryWrapper = new QueryWrapper<>();
        postTagRelationQueryWrapper.eq("post_id",postId);
        List<PostTagRelation> postTagRelationList = postTagRelationService.list(postTagRelationQueryWrapper);
        List<Long> postTagIdList = postTagRelationList.stream().map(PostTagRelation::getPostTagId).collect(Collectors.toList());
        QueryWrapper<PostTag> postTagQueryWrapper = new QueryWrapper();
        postTagQueryWrapper.in("post_tag_id",postTagIdList);
        return this.list(postTagQueryWrapper);
    }
}
