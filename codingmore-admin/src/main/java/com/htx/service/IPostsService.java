package com.htx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.htx.dto.PostsPageQueryParam;
import com.htx.dto.PostsParam;
import com.htx.model.Posts;
import com.htx.vo.PostsVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/13 15:28
 * @Desc: 文章 服务类
 */
public interface IPostsService extends IService<Posts> {
    /**
     * 保存内容
     */
    void savePosts(PostsParam postsParam);

    /**
     * 修改内容
     */
    void updatePosts(PostsParam postsParam);

    /**
     * 定时发布文章后调用
     */
    boolean updatePostByScheduler(Long postId);

    boolean removePostsById(Long id);

    IPage<PostsVo> findByPage(PostsPageQueryParam postsPageQueryParam);

    PostsVo getPostsById(Long id);

    int  insertPostTermTaxonomy(Long[] postsIds, Long[] termTaxonomyIds);

    String uploadMd(MultipartFile file);

    /**
     * 置顶/取消置顶方法
     * @param postsId 文章id
     * @param flag 标志位：0-不置顶；1-置顶
     */
    void setOnTop(Long postsId, Integer flag);
}
