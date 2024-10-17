package com.htx.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/17 21:11
 * @Desc: 评论表 前端控制器
 */
@Controller
@RequestMapping("/comments")
@Api(tags="评论")
public class CommentsController {
    private static Logger LOGGER = LoggerFactory.getLogger(CommentsController.class);
}
