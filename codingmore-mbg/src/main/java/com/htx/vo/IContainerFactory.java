package com.htx.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/11 20:20
 * @Desc:
 */
public interface IContainerFactory {
    IContainerFactory defaultContainerFactory = new IContainerFactory() {
        public Map<String, Object> getAttrsMap() {
            return new HashMap();
        }
    };
    Map getAttrsMap();
}
