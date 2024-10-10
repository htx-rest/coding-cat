package com.htx.state;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/10 21:20
 * @Desc: 文章栏目关系类型
 */
public enum TermRelationType {
    /**
     * 内容
     */
    CONTENT(1),

    /**
     * 内容链接
     */
    CONTENT_LINK(2),

    /**
     * 栏目链接
     */
    CHANNEL_LINK(3);


    private Integer type;

    public Integer getType() {
        return type;
    }

    TermRelationType(Integer type) {
        this.type = type;
    }
}
