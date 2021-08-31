package com.learning.learningquartzscheduler.controller;

import com.learning.learningquartzscheduler.info.TimerInfo;
import com.learning.learningquartzscheduler.job.TaskAndSchedulingApiExample;
import com.learning.learningquartzscheduler.playground.PlaygroundService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/timer")
public class PlaygroundController {

    @Autowired
    private final PlaygroundService service;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    TaskScheduler taskScheduler;

    public PlaygroundController(PlaygroundService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String runHelloWorldJob() throws SchedulerException, InterruptedException {
        service.runHelloWorldJob();
//        service.runApprovalJob();
        return "<h1>Triggering the Jobs.....</h1>";
    }

    @GetMapping("/all")
    public List<TimerInfo> getAllRunningTimer(){
        return service.getAllRunningTimer();
    }

    @DeleteMapping("/delete/{timerId}")
    public boolean deleteTimer(@PathVariable String timerId){
        return service.deleteTimer(timerId);

    }
    @GetMapping("/execute")
    public String execute(){
        Date date=new Date();
        date.setSeconds(date.getSeconds()+5);
        taskScheduler.schedule(new TaskAndSchedulingApiExample(),new CronTrigger("5 * * * * *"));
        return "Executing.........";
    }

}
