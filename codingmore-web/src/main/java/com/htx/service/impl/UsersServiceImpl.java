package com.htx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htx.mapper.UsersMapper;
import com.htx.model.Users;
import com.htx.service.IUsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/18 22:45
 * @Desc: 用户表 服务实现类
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {
    private static Logger LOGGER = LoggerFactory.getLogger(UsersServiceImpl.class);
}
