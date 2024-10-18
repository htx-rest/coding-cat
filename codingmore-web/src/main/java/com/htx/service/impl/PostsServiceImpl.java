package com.htx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htx.assist.RedisConstants;
import com.htx.dto.PostsPageQueryParam;
import com.htx.mapper.PostsMapper;
import com.htx.model.*;
import com.htx.service.*;
import com.htx.state.PostStatus;
import com.htx.util.CusAccessObjectUtil;
import com.htx.vo.PostsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/18 22:31
 * @Desc: 文章 服务实现类
 */
@Service
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements IPostsService {
    @Autowired
    private IUsersService iUsersService;

    @Autowired
    private ITermRelationshipsService iTermRelationshipsService;

    @Autowired
    private IPostTagService iPostTagService;

    @Autowired
    private IPostTagRelationService iPostTagRelationService;

    @Autowired
    private IRedisService redisService;

    @Override
    public PostsVo getPostsById(Long id) {
        Posts posts = this.getById(id);
        PostsVo postsVo = new PostsVo();
        if (posts == null) {
            return postsVo;
        }
        BeanUtils.copyProperties(posts, postsVo);
        QueryWrapper<TermRelationships> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("term_relationships_id", posts.getPostsId());
        List<TermRelationships> termRelationshipsList = iTermRelationshipsService.list(queryWrapper);
        if (termRelationshipsList.size() > 0) {
            postsVo.setTermTaxonomyId(termRelationshipsList.get(0).getTermTaxonomyId());
        }
        QueryWrapper<PostTagRelation> tagRelationWrapper = new QueryWrapper<>();
        tagRelationWrapper.eq("post_id", posts.getPostsId());
        tagRelationWrapper.orderBy(true,true,"term_order");
        List<PostTagRelation> postTagRelationList = iPostTagRelationService.list(tagRelationWrapper);
        if (postTagRelationList.size() > 0) {
            List<Long> tagIds = postTagRelationList.stream().map(PostTagRelation::getPostTagId).collect(Collectors.toList());
            QueryWrapper<PostTag> tagQuery = new QueryWrapper<>();
            tagQuery.in("post_tag_id", tagIds);
            List<PostTag> postTags = iPostTagService.list(tagQuery);
            Collections.sort(postTags, new Comparator<PostTag>() {
                @Override
                public int compare(PostTag o1, PostTag o2) {
                    return tagIds.indexOf(o1.getPostTagId())-tagIds.indexOf(o2.getPostTagId());
                }
            });
            postsVo.setTagsName(StringUtils.join(postTags.stream().map(PostTag::getDescription).collect(Collectors.toList()), ","));
            postsVo.setTags(postTags);
        }

        Users users = iUsersService.getById(posts.getPostAuthor());
        postsVo.setUserNiceName(users.getUserNicename());
        return postsVo;
    }

    @Override
    public IPage<PostsVo> findByPageWithTag(PostsPageQueryParam postsPageQueryParam) {
        QueryWrapper<PostsPageQueryParam> queryWrapper = new QueryWrapper<>();
        if (postsPageQueryParam.getTermTaxonomyId() != null) {
            queryWrapper.eq("b.term_taxonomy_id", postsPageQueryParam.getTermTaxonomyId());
        }
        if(StringUtils.isNotBlank(postsPageQueryParam.getPostTitleKeyword())){
            queryWrapper.like("a.post_title", "%"+postsPageQueryParam.getPostTitleKeyword()+"%");
        }
        if (postsPageQueryParam.getOrderBy() != null) {
            String[] cloums = postsPageQueryParam.getOrderBy().split(",");
            queryWrapper.orderBy(true, postsPageQueryParam.isAsc(), cloums);
        }
        queryWrapper.eq("a.post_status", PostStatus.PUBLISHED.toString());
        Page<PostsVo> postsPage = new Page<>(postsPageQueryParam.getPage(), postsPageQueryParam.getPageSize());

        return this.getBaseMapper().findByPageWithTag(postsPage, queryWrapper);
    }

    @Override
    public List<PostsVo> findByPageWithTagPaged(PostsPageQueryParam postsPageQueryParam) {
        QueryWrapper<PostsPageQueryParam> queryWrapper = new QueryWrapper<>();
        if (postsPageQueryParam.getTermTaxonomyId() != null) {
            queryWrapper.eq("b.term_taxonomy_id", postsPageQueryParam.getTermTaxonomyId());
        }
        if(StringUtils.isNotBlank(postsPageQueryParam.getPostTitleKeyword())){
            queryWrapper.like("a.post_title", "%"+postsPageQueryParam.getPostTitleKeyword()+"%");
        }
        if (postsPageQueryParam.getOrderBy() != null) {
            String[] cloums = postsPageQueryParam.getOrderBy().split(",");
            queryWrapper.orderBy(true, postsPageQueryParam.isAsc(), cloums);
        }
        queryWrapper.eq("a.post_status", PostStatus.PUBLISHED.toString());
        long pageSize = postsPageQueryParam.getPageSize();
        long pageStart = (postsPageQueryParam.getPage() - 1) * pageSize;
        Long searchTagId = postsPageQueryParam.getSearchTagId();

        return this.getBaseMapper().findByPageWithTagPaged(queryWrapper, searchTagId, pageStart, pageSize);
    }

    @Override
    public List<Posts> listByTermTaxonomyId(Long termTaxonomyId) {
        QueryWrapper<TermRelationships> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("term_taxonomy_id", termTaxonomyId);
        List<TermRelationships> termRelationshipsList = iTermRelationshipsService.list(queryWrapper);
        if (termRelationshipsList.size() > 0) {
            List<Long> postIds = termRelationshipsList.stream().map(TermRelationships::getTermRelationshipsId).collect(Collectors.toList());
            return this.listByIds(postIds);
        }

        return new ArrayList<Posts>();
    }

    @Override
    public void increasePageView(Long id, HttpServletRequest request) {
        Posts posts = this.getById(id);
        if(posts == null){
            return;
        }

        // 通过 Redis 来给 PV + 1
        Long pageView = redisService.incr(RedisConstants.getWebPageViewKey(id.toString()), 1);
        // 更新 MySQL
        posts.setPageView(pageView);
        this.updateById(posts);
    }


    @Override
    public int getPageView(Long id) {
        return redisService.countKey(RedisConstants.getWebPageViewKey(id+":*"));
    }

    @Override
    public void increaseLikeCount(Long id, HttpServletRequest request) {
        String ip = CusAccessObjectUtil.getIpAddress(request);
        redisService.incr(RedisConstants.getWebPostLikeKey(id+":"+ip), 1);
    }

    @Override
    public Boolean hasClickedLike(Long id, HttpServletRequest request) {
        String ip = CusAccessObjectUtil.getIpAddress(request);
        return redisService.get(RedisConstants.getWebPostLikeKey(id+":"+ip)) !=null;

    }

    @Override
    public int getLikeCount(Long id) {
        return redisService.countKey(RedisConstants.getWebPostLikeKey(id+":*"));
    }
}
