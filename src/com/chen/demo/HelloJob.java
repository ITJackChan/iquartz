/**
 * Copyright © 2019 by ITJackChan. All rights reserved.
 */
package com.chen.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Trigger;

/**
 * @author Chen Yibiao
 * @date 2019年6月22日
 */
public class HelloJob implements Job {

    /**
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Hello, Quartz! Now is " + format.format(new Date()));


        JobKey jobKey = context.getJobDetail().getKey();
        System.out.println("job.name: " + jobKey.getName());
        System.out.println("job.group: " + jobKey.getGroup());
        String value = context.getJobDetail().getJobDataMap().getString("key");
        System.out.println("dataMap.key: " + value);
        Trigger trigger = context.getTrigger();
        System.out.println("trigger.startTime: " + trigger.getStartTime());
        System.out.println("trigger.endTime: " + trigger.getEndTime());
        System.out.println("\n------------------ 分割线 ------------------\n");
    }

}
