package com.htx.mapper;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.htx.dto.UsersPageQueryParam;
import com.htx.model.Users;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/11 21:03
 * @Desc: 用户表 Mapper 接口
 */
public interface UsersMapper extends BaseMapper<Users> {
    IPage<Users> findByPage(IPage<Users> page, @Param(Constants.WRAPPER) Wrapper<UsersPageQueryParam> wrapper);
}
