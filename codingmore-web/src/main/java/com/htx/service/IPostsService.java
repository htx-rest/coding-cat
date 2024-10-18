package com.htx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.htx.dto.PostsPageQueryParam;
import com.htx.model.Posts;
import com.htx.vo.PostsVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/18 22:29
 * @Desc: 文章 服务类
 */
public interface IPostsService extends IService<Posts> {

    IPage<PostsVo> findByPageWithTag(PostsPageQueryParam postsPageQueryParam);

    List<PostsVo> findByPageWithTagPaged(PostsPageQueryParam postsPageQueryParam);

    List<Posts> listByTermTaxonomyId(Long termTaxonomyId);

    PostsVo getPostsById(Long id);

    void increasePageView(Long id, HttpServletRequest request);

    void increaseLikeCount(Long id, HttpServletRequest  request);

    int getPageView(Long id);

    int getLikeCount(Long id);

    Boolean hasClickedLike(Long id, HttpServletRequest request);

}
