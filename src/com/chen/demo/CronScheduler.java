/**
 * Copyright © 2019 by ITJackChan. All rights reserved.
 */
package com.chen.demo;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author Chen Yibiao
 * @date 2019年6月22日
 */
public class CronScheduler {

    public static void main(String[] args) {
        try {
            // 构建子组件Job和Trigger(建造者模式)
            JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("cronJob").build();

            // Cron表达式，每日的9点40触发一次
            String ce = "0 40 9 * * ?";
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("cronTrigger").withSchedule(CronScheduleBuilder.cronSchedule(ce)).build();
            
            // 通过调度工厂(工厂模式)获取调度器实例
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException  se) {
            se.printStackTrace();
        }
    }
    
}
