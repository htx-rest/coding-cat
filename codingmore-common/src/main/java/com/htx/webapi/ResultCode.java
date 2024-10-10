package com.htx.webapi;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/10 21:02
 * @Desc: 枚举了一些常用API操作码
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(0, "操作成功"),

    FAILED(500, "操作失败"),

    VALIDATE_FAILED(506, "参数检验失败"),

    UNAUTHORIZED(401, "暂未登录或token已经过期"),

    FORBIDDEN(403, "没有相关权限");

    private long code;

    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
