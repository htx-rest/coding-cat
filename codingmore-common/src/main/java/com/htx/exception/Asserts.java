package com.htx.exception;

import com.htx.webapi.IErrorCode;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/10 21:09
 * @Desc: 断言处理类，用于抛出各种API异常
 */
public class Asserts {
    private Asserts(){

    }

    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
