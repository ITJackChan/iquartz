/**
 * Copyright © 2019 by ITJackChan. All rights reserved.
 */
package com.chen.demo;

import java.util.Date;

import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author Chen Yibiao
 * @date 2019年6月22日
 */
public class SimpleScheduler {

    public static void main(String[] args) {
        //fn1();
        //fn2();
        //fn3();
    }

    public static void fn1() {
        try {
            // 构建子组件Job和Trigger，(建造者模式)
            JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                //需要传递的内容，给具体job传递参数
                .usingJobData("key", "value")
                //定义name和group
                .withIdentity("myJob").build();
            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                //定时 ，每1秒钟执行一次，不写默认是重复次数（0次）和重复间隔（0秒）
                .withIntervalInSeconds(1)
                //.withRepeatCount(5)
                //重复执行
                .repeatForever();
            SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger().withIdentity("myTrigger")
                //加入 scheduler之后立刻执行
                .startNow().withSchedule(simpleScheduleBuilder).build();

            // 通过调度工厂(工厂模式)获取调度器实例
            // StdSchedulerFactory：默认值加载是当前工作目录下的”quartz.properties”属性文件。如果加载失败，会去加载org/quartz包下的”quartz.properties”属性文件。
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            scheduler.scheduleJob(jobDetail, simpleTrigger);
            
            SchedulerMetaData metaData = scheduler.getMetaData();
            System.out.println("SchedulerMetaData：\n  " + metaData);
            
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    public static void fn2() {
        try {
            Date start = new Date();
            // Date end = new Date(start.getTime() + 1000 * 5);
            // 获取当前时间5s后的时间
            Date end = DateBuilder.nextGivenSecondDate(null, 5);
            // 构建子组件Job和Trigger，(建造者模式)
            JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myJob").build();
            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(1).repeatForever();
            SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger().withIdentity("myTrigger")
                //.startAt(DateBuilder.futureDate(5,DateBuilder.IntervalUnit.MINUTE))
                .startAt(start).endAt(end).withSchedule(simpleScheduleBuilder).build();

            // 通过调度工厂(工厂模式)获取调度器实例
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            scheduler.scheduleJob(jobDetail, simpleTrigger);

        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }
    
    public static void fn3() {
        try {
            //storeDurly(),没有触发器指向任务的时候，将任务保存在队列中，然后可以通过手动触发
            JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myJob","myGroup").storeDurably().build();
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            //任务立即执行
            scheduler.addJob(jobDetail, true);
            System.out.println("手动触发\n");
            scheduler.triggerJob(JobKey.jobKey("myJob","myGroup"));
            
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }
}