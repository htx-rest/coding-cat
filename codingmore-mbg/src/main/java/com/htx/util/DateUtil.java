package com.htx.util;

import cn.hutool.core.date.DateTime;

import java.util.Date;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/10 22:31
 * @Desc: 时间工具类
 */
public class DateUtil {
    private DateUtil(){

    }

    public static String getShortTime(Date time) {
        String shortString = "";
        long now = DateTime.now().getTime();

        long delTime = (now - time.getTime()) / 1000;
        if (delTime > 365 * 24 * 60 * 60) {
            shortString = (int) (delTime / (365 * 24 * 60 * 60)) + "年前";
        } else if (delTime > 24 * 60 * 60) {
            shortString = (int) (delTime / (24 * 60 * 60)) + "天前";
        } else if (delTime > 60 * 60) {
            shortString = (int) (delTime / (60 * 60)) + "小时前";
        } else if (delTime > 60) {
            shortString = (int) (delTime / (60)) + "分前";
        } else if (delTime > 1) {
            shortString = delTime + "秒前";
        } else {
            shortString = "1秒前";
        }
        return shortString;
    }
}
