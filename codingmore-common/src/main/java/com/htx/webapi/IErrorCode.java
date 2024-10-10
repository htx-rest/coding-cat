package com.htx.webapi;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/10 21:00
 * @Desc: 封装API的错误码
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}
