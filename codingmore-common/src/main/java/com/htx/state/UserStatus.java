package com.htx.state;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/10 21:21
 * @Desc: 用户状态
 */
public enum UserStatus {
    ENABLE(0),

    DISABLED(1);

    private int status;

    UserStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
