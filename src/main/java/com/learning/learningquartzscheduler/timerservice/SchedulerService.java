package com.learning.learningquartzscheduler.timerservice;

import com.learning.learningquartzscheduler.info.TimerInfo;
import com.learning.learningquartzscheduler.job.HelloWorldJob;
import com.learning.learningquartzscheduler.job.SimpleTriggerListener;
import com.learning.learningquartzscheduler.util.TimerUtil;
import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchedulerService {
    Logger log= LoggerFactory.getLogger(SchedulerService.class);

    @Autowired
    private final Scheduler scheduler;

    public SchedulerService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void init(){
        try{
            scheduler.start();
//            scheduler.getListenerManager().addTriggerListener(new SimpleTriggerListener(this));
        }
        catch (SchedulerException e){
            log.error(e.getMessage(),e);
        }
    }

    public void schedule(final Class jobClass,final TimerInfo info){
        final JobDetail jobDetail= TimerUtil.buildJobDetails(jobClass,info);
//        final Trigger trigger=TimerUtil.buildTrigger(jobClass,info);
        final CronTrigger cronTrigger=TimerUtil.buildCronTrigger(jobClass,info);
        try {
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException e) {
            log.error(e.getMessage(),e);
        }
    }

    public List<TimerInfo> getAllRunningTimer(){
        try {
            List<TimerInfo> timerInfos = scheduler.getJobKeys(GroupMatcher.anyGroup())
                    .stream()
                    .map(jobKey -> {
                        try {
                            return (TimerInfo) scheduler.getJobDetail(jobKey).getJobDataMap().get(jobKey.getName());
                        } catch (SchedulerException e) {
                            log.error(e.getMessage());
                            return null;
                        }
                    })
                    .collect(Collectors.toList());
            return timerInfos;
        } catch (SchedulerException e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    public void updateTimer(String timerId,TimerInfo info){
        try {
            JobDetail jobDetail = scheduler.getJobDetail(new JobKey(timerId));
            log.info(jobDetail.getKey().getName());
            if(jobDetail==null){
                log.error("Failed to find timer with id {}",timerId);
                return ;
            }
            jobDetail.getJobDataMap().put(timerId,info);

            scheduler.addJob(jobDetail,true);

        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }

    }

    public boolean deleteTimer(String timerId){
        try {
            return scheduler.deleteJob(new JobKey(timerId));
        } catch (SchedulerException e) {
            log.error(e.getMessage(),e);
            return false;
        }
    }

    @PreDestroy
    public void destroy(){
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
