package com.htx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htx.mapper.CommentsMapper;
import com.htx.model.Comments;
import com.htx.service.ICommentsService;
import org.springframework.stereotype.Service;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/18 22:38
 * @Desc: 评论表 服务实现类
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements ICommentsService {

}
