package com.learning.learningquartzscheduler.job;

import com.learning.learningquartzscheduler.info.TimerInfo;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldJob implements Job {
    Logger log= LoggerFactory.getLogger(HelloWorldJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap=jobExecutionContext.getJobDetail().getJobDataMap();
        TimerInfo info= (TimerInfo) jobDataMap.get(HelloWorldJob.class.getSimpleName());
        log.info("Remaining fireCount "+ info.getRemainingFireCount());
    }
}
