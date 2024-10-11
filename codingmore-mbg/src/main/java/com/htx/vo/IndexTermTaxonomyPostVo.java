package com.htx.vo;

import com.htx.model.Posts;
import com.htx.model.TermTaxonomy;
import lombok.Data;

import java.util.List;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/11 20:22
 * @Desc: 首页栏目文章列表
 */
@Data
public class IndexTermTaxonomyPostVo {
    private TermTaxonomy termTaxonomy;
    private List<Posts> posts;
}
