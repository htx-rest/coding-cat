package com.htx.config;

import com.htx.component.DynamicSecurityService;
import com.htx.model.Resource;
import com.htx.service.IResourceService;
import com.htx.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/13 15:03
 * @Desc: learn-admin-security模块相关配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CodingmoreSecurityConfig extends CustomSecurityConfig {
    @Autowired
    private IUsersService usersService;

    @Autowired
    private IResourceService resourceService;

    @Bean
    @Override
    public UserDetailsService userDetailsService(){
        return username -> usersService.loadUserByUsername(username);
    }

    @Bean
    public DynamicSecurityService dynamicSecurityService(){
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
                List<Resource> resources = resourceService.list();
                resources.forEach(item->{
                    map.put(item.getUrl(), new SecurityConfig(item.getResourceId() + ":" + item.getName()));
                });
                return map;
            }
        };
    }
}
