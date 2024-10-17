package com.htx.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/13 15:20
 * @Desc: 线程池配置
 */
@Configuration
@EnableAsync
public class TreadPoolConfig implements AsyncConfigurer {
    @Value("${async.executor.corePoolSize}")
    private Integer corePoolSize;

    @Value("${async.executor.maxPoolSize}")
    private Integer maxPoolSize;

    @Value("${async.executor.queueCapacity}")
    private Integer queueCapacity;

    @Value("${async.executor.keepAliveSeconds}")
    private Integer keepAliveSeconds;

    @Value("${async.executor.threadNamePrefix}")
    private String threadNamePrefix;

    @Bean(name = "ossUploadImageExecutor")
    public ThreadPoolTaskExecutor getMetricsExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);

        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}
