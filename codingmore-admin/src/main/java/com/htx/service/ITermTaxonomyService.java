package com.htx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.htx.model.TermTaxonomy;
import com.htx.vo.TermTaxonomyTreeNode;

import java.util.List;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/17 23:05
 * @Desc: 栏目 服务类
 */
public interface ITermTaxonomyService extends IService<TermTaxonomy> {

    /**
     * 删除栏目 包含了逻辑判断
     * @param termTaxonomyId
     * @return
     */
    boolean removeTermTaxonomy(long termTaxonomyId);

    /**
     * 根据站点id和父栏目id获得所有子孙栏目节点
     * @param parentId 父栏目id
     * @return
     */
    List<TermTaxonomyTreeNode> getAllByParentId(Long parentId);

    /**
     * 根据父栏目id获得直属子栏目
     * @param parentId 父栏目id，如果为null则返回null
     * @return
     */
    List<TermTaxonomyTreeNode> getChildrenByParentId(Long parentId);
}
