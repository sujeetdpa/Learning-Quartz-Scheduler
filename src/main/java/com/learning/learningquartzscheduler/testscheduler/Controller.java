package com.learning.learningquartzscheduler.testscheduler;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;

@RestController
public class Controller {
    @Autowired
    private Scheduler scheduler;

    @GetMapping("/")
    public String runEmailJob() throws SchedulerException {
        JobDataMap jobDataMap=new JobDataMap();
        jobDataMap.put("Email","test@gmail.com");
        jobDataMap.put("subject","Testing Scheduler");
        JobDetail jobDetail = JobBuilder.newJob(EmailJob.class)
                .withIdentity(EmailJob.class.getSimpleName())
                .usingJobData(jobDataMap)
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(EmailJob.class.getSimpleName())
                .startAt(new Date())
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
        scheduler.scheduleJob(jobDetail,trigger);

        return "<h1>Running Email Job</h1>";
    }
}
