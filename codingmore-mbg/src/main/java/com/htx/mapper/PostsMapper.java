package com.htx.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.htx.dto.PostsPageQueryParam;
import com.htx.model.Posts;
import com.htx.vo.PostsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/11 20:51
 * @Desc: 文章 Mapper 接口
 */
public interface PostsMapper extends BaseMapper<Posts> {

    IPage<PostsVo> findByPage(IPage<PostsVo> page, @Param(Constants.WRAPPER) Wrapper<PostsPageQueryParam> wrapper);

    IPage<PostsVo> findByPageWithTag(IPage<PostsVo> page, @Param(Constants.WRAPPER) Wrapper<PostsPageQueryParam> wrapper);

    List<PostsVo> findByPageWithTagPaged(@Param(Constants.WRAPPER) Wrapper<PostsPageQueryParam> wrapper, Long searchTagId, long pageStart, long pageSize);

}
