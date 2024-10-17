package com.htx.component;

import com.htx.service.IPostsService;
import com.htx.service.IScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @Author: htx
 * @GZH: 二哈学习之路
 * @Date: 2024/10/13 15:26
 * @Desc: 定时发布文章
 */
@Slf4j
@Component
public class PublishPostJob extends QuartzJobBean {
    @Autowired
    private IScheduleService scheduleService;

    @Autowired
    private IPostsService postsService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Trigger trigger = jobExecutionContext.getTrigger();
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        Long data = jobDataMap.getLong("data");
        log.info("定时发布文章操作：{}",data);

        // 获取文章的 ID后获取文章，更新文章为发布的状态，还有发布的时间
        boolean success = postsService.updatePostByScheduler(data);

        //完成后删除触发器和任务
        if (success) {
            log.info("定时任务执行成功，开始清除定时任务");
            scheduleService.cancelScheduleJob(trigger.getKey().getName());
        }
    }
}
