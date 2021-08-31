package com.learning.learningquartzscheduler.util;

import com.learning.learningquartzscheduler.info.TimerInfo;
import org.quartz.*;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class TimerUtil {

    private TimerUtil(){}

    public static JobDetail buildJobDetails(final Class jobClass, final TimerInfo info){
        final JobDataMap jobDataMap=new JobDataMap();
        jobDataMap.put(jobClass.getSimpleName(),info);
        return JobBuilder
                .newJob(jobClass)
                .withIdentity(jobClass.getSimpleName())
                .setJobData(jobDataMap)
                .storeDurably(true)
                .build();
    }
    public static Trigger buildTrigger(final Class jobClass,TimerInfo info){
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(info.getRepeatInterval());

        if(info.isRunForever()){
             simpleScheduleBuilder = simpleScheduleBuilder.repeatForever();
         }
         else {
             simpleScheduleBuilder=simpleScheduleBuilder.withRepeatCount(info.getTotalFireCount());
         }
         return TriggerBuilder.newTrigger()
                 .withIdentity(jobClass.getSimpleName())
                 .startAt(new Date(System.currentTimeMillis()+info.getInitialOffset()))
                 .withSchedule(simpleScheduleBuilder)
                 .build();
    }
    public static CronTrigger buildCronTrigger(Class jobClass,TimerInfo info)  {
        CronTriggerFactoryBean cronTriggerFactoryBean=new CronTriggerFactoryBean() ;
        cronTriggerFactoryBean.setBeanName(jobClass.getSimpleName());
        cronTriggerFactoryBean.setCronExpression(buildCronExpression(info));
        cronTriggerFactoryBean.setGroup("grp");
        cronTriggerFactoryBean.setStartTime(Date.from(info.getStartDate().atZone(ZoneId.systemDefault()).toInstant()));
        cronTriggerFactoryBean.setJobDetail(buildJobDetails(jobClass,info));
        try {
            cronTriggerFactoryBean.afterPropertiesSet();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cronTriggerFactoryBean.getObject();


    }
    private static String buildCronExpression(TimerInfo info){
        LocalDateTime startDate=info.getStartDate();
        LocalDateTime endDate=info.getEndDate();
        String year = String.valueOf(startDate.getYear());
        String cronExpression  = "";
        cronExpression = startDate.getSecond()
                + " "
                + startDate.getMinute()
                + " "
                + startDate.getHour()
                + " "
                + startDate.getDayOfMonth()
                + " "
                + startDate.getMonth().getValue()
                + " ? "
                +year;
        System.out.println("Cron Expression : "+cronExpression);
        return cronExpression;
    }
}
