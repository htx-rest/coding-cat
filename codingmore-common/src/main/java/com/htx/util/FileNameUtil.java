package com.htx.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/10 21:11
 * @Desc: 文件名处理工具类
 */
public class FileNameUtil {
    private FileNameUtil(){

    }

    private static final String[] IMAGE_EXTENTION = {".jpg", ".jpeg", ".png", ".gif"};

    public static String getImgName(String url) {
        String ext = "";
        for (String extItem : IMAGE_EXTENTION) {
            if (url.contains(extItem)) {
                ext = extItem;
                break;
            }
        }
        // 2022年06月09日 + UUID + .jpg
        return  DateUtil.today() + UUID.fastUUID() + ext;
    }
}
