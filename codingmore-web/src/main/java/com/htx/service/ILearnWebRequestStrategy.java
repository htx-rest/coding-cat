package com.htx.service;

import com.htx.util.WebRequestParam;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/18 22:26
 * @Desc: 前端请求处理策略
 */
public interface ILearnWebRequestStrategy {
    String handleRequest(WebRequestParam webRequestParam);

}
