package com.htx.service.impl;

import com.htx.model.Site;
import com.htx.service.ILearnWebRequestStrategy;
import com.htx.service.IPostsService;
import com.htx.service.ISiteService;
import com.htx.util.WebRequestParam;
import com.htx.vo.PostsVo;
import com.htx.vo.SiteVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/18 22:39
 * @Desc: 内容请求处理策略
 */
@Service("contentPageRequestStrategy")
public class ContentPageRequestStrategy implements ILearnWebRequestStrategy {
    private static final String CONTENT_PAGE = "article.html";

    private static final String POSTS_VO = "postsVo";

    private static final String CLICKED_LIKE = "hasClickedLike";

    /**
     * 站点信息
     */
    private static final String SITE_CONFIG = "siteConfig";

    private static final String TAG_LIST = "tagList";

    @Autowired
    private ISiteService siteService;

    @Autowired
    private IPostsService iPostsService;

    @Override
    public String handleRequest(WebRequestParam webRequestParam) {
        iPostsService.increasePageView(webRequestParam.getPostId(),webRequestParam.getRequest());

        Boolean hasClickedLike = iPostsService.hasClickedLike(webRequestParam.getPostId(),webRequestParam.getRequest());
        webRequestParam.getRequest().setAttribute(CLICKED_LIKE, hasClickedLike);
        PostsVo postsVo = iPostsService.getPostsById(webRequestParam.getPostId());
        webRequestParam.getRequest().setAttribute(POSTS_VO, postsVo);
        if(StringUtils.isNotBlank(postsVo.getTagsName())){
            webRequestParam.getRequest().setAttribute(TAG_LIST, CollectionUtils.arrayToList(postsVo.getTagsName().split(",")));
        }
        List<Site> siteList = siteService.list();
        //处理站点配置
        if(siteList.size() > 0) {
            Site site = siteList.get(0);
            SiteVo siteVo = new SiteVo();
            BeanUtils.copyProperties(site, siteVo);
            webRequestParam.getRequest().setAttribute(SITE_CONFIG, siteVo);
        }
        return CONTENT_PAGE;
    }
}
