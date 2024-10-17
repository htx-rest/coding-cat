package com.htx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/13 10:33
 * @Desc: 后台管理系统接口主类
 */
@ServletComponentScan
@SpringBootApplication
public class CodingmoreAdminBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(CodingmoreAdminBootstrap.class);
    }
}
