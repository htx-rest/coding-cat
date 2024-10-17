package com.htx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htx.dto.PostAddTagParam;
import com.htx.mapper.PostTagMapper;
import com.htx.model.PostTag;
import com.htx.model.PostTagRelation;
import com.htx.service.IPostTagRelationService;
import com.htx.service.IPostTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/17 22:46
 * @Desc: 标签表 服务实现类
 */
@Service
@Transactional
@Slf4j
public class PostTagServiceImpl extends ServiceImpl<PostTagMapper, PostTag> implements IPostTagService {
    @Autowired
    private IPostTagRelationService postTagRelationService;

    @Override
    public boolean savePostTag(PostAddTagParam postAddTagParam) {
        log.info("保存文章的标签{}", postAddTagParam);

        PostTag postTag = new PostTag();
        BeanUtils.copyProperties(postAddTagParam,postTag);

        boolean result = save(postTag);

        if(postAddTagParam.getPostId()!=null){
            PostTagRelation postTagRelation = new PostTagRelation();
            postTagRelation.setPostTagId(postTag.getPostTagId());
            postTagRelation.setPostId(postAddTagParam.getPostId());
            postTagRelation.setTermOrder(postAddTagParam.getTermOrder());
            postTagRelationService.save(postTagRelation);
        }
        return result;
    }

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

    @Override
    public boolean removeTag(Long postTagId) {
        QueryWrapper<PostTagRelation> postTagRelationQueryWrapper = new QueryWrapper();
        postTagRelationQueryWrapper.eq("post_tag_id",postTagId);
        postTagRelationService.remove(postTagRelationQueryWrapper);
        return this.removeById(postTagId);
    }
}
