package com.htx.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/17 22:16
 * @Desc:
 */
public interface IOssService {
    String upload(String url);

    String upload(MultipartFile file);

    boolean needUpload(String imageUrl);
}
