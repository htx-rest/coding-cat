package com.htx.state;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/10 21:22
 * @Desc: 用户类型
 */
public enum UserType {
    BACKEND(0),

    FRONT(1);

    private int type;

    UserType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
