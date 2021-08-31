package com.learning.learningquartzscheduler.playground;

import com.learning.learningquartzscheduler.info.TimerInfo;
import com.learning.learningquartzscheduler.job.ApprovalJob;
import com.learning.learningquartzscheduler.job.HelloWorldJob;
import com.learning.learningquartzscheduler.timerservice.SchedulerService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Service
public class PlaygroundService {
    @Autowired
    private final SchedulerService schedulerService;

    public PlaygroundService(final SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }
    public void runHelloWorldJob() {
        final TimerInfo info=new TimerInfo();
//        info.setTotalFireCount(10);
//        info.setRemainingFireCount(info.getTotalFireCount());
//        info.setRunForever(false);
//        info.setRepeatInterval(3000);
//        info.setInitialOffset(20000);
//        System.out.println(" Job will execute at:  "+new Date(System.currentTimeMillis()+info.getInitialOffset()));
        info.setCallBackData("Initial Timer Data");
        LocalDateTime startDate=LocalDateTime.now().plusSeconds(5);
        System.out.println("Job will start at : "+startDate);
        info.setStartDate(startDate);
        info.setEndDate(startDate.plusMinutes(1));
        System.out.println("Job will End at : "+startDate.plusMinutes(1));
        schedulerService.schedule(HelloWorldJob.class,info);
    }

    public List<TimerInfo> getAllRunningTimer(){
        return schedulerService.getAllRunningTimer();
    }

    public boolean deleteTimer(String timerId){
        return schedulerService.deleteTimer(timerId);
    }
    public void updateTimer(String timerId,TimerInfo info){
        schedulerService.updateTimer(timerId,info);
    }

    public void runApprovalJob() throws SchedulerException, InterruptedException {
        final TimerInfo info=new TimerInfo();
        info.setTotalFireCount(10);
        info.setRemainingFireCount(info.getTotalFireCount());
        info.setRunForever(false);
        info.setRepeatInterval(1000);
        info.setInitialOffset(5000);
        info.setCallBackData("Initial Timer Data");
        schedulerService.schedule(ApprovalJob.class,info);
    }
}
