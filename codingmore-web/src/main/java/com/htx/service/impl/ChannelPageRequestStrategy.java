package com.htx.service.impl;

import com.htx.service.ILearnWebRequestStrategy;
import com.htx.util.WebRequestParam;
import org.springframework.stereotype.Service;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/18 22:38
 * @Desc: 栏目请求处理策略
 */
@Service("channelPageRequestStrategy")
public class ChannelPageRequestStrategy implements ILearnWebRequestStrategy {
    @Override
    public String handleRequest(WebRequestParam webRequestParam) {
        return "channel";
    }
}
