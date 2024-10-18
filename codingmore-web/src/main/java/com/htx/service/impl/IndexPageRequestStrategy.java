package com.htx.service.impl;

import com.htx.dto.PostsPageQueryParam;
import com.htx.model.Site;
import com.htx.service.ILearnWebRequestStrategy;
import com.htx.service.IPostsService;
import com.htx.service.ISiteService;
import com.htx.service.ITermTaxonomyService;
import com.htx.state.PostStatus;
import com.htx.util.WebRequestParam;
import com.htx.vo.PostsVo;
import com.htx.vo.SiteVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/18 22:40
 * @Desc: 首页请求处理策略
 */
@Service("indexPageRequestStrategy")
public class IndexPageRequestStrategy implements ILearnWebRequestStrategy {
    private static final String INDEX_PAGE = "index.html";
    /**
     * 站点栏目内容信息
     */
    private static final String INDEX_TERM_TAXONOMY_POST_VO = "indexTermTaxonomyPostVo";

    /**
     * 站点信息
     */
    private static final String SITE_CONFIG = "siteConfig";

    /**
     * 文章分页列表
     */
    private static final String POSTS_ITEMS = "postsItems";
    /**
     * 、文章条数（前端列表暂时没用上）
     */
    private static final String POSTS_TOTAL= "postsTotal";

    @Autowired
    private ISiteService siteService;

    @Autowired
    private ITermTaxonomyService termTaxonomyService;

    @Autowired
    private IPostsService postsService;

    @Override
    public String handleRequest(WebRequestParam webRequestParam) {

        List<Site> siteList = siteService.list();
        //处理站点配置
        if(siteList.size() > 0) {
            Site site = siteList.get(0);
            SiteVo siteVo = new SiteVo();
            BeanUtils.copyProperties(site, siteVo);
            webRequestParam.getRequest().setAttribute(SITE_CONFIG, siteVo);
        }

        PostsPageQueryParam pageQueryParam = new PostsPageQueryParam();
        pageQueryParam.setPage(webRequestParam.getPage());
        pageQueryParam.setAsc(webRequestParam.isAsc());
        pageQueryParam.setOrderBy(webRequestParam.getOrderBy());
        pageQueryParam.setPageSize(webRequestParam.getPageSize());
        pageQueryParam.setPostStatus(PostStatus.PUBLISHED.toString());
        pageQueryParam.setTermTaxonomyId(webRequestParam.getChannelId());

        List<PostsVo> pageVoList = postsService.findByPageWithTagPaged(pageQueryParam);
        webRequestParam.getRequest().setAttribute(POSTS_ITEMS, pageVoList);
        return INDEX_PAGE;
    }
}
