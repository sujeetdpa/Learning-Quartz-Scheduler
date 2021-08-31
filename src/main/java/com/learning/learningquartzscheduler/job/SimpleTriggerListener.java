package com.learning.learningquartzscheduler.job;

import com.learning.learningquartzscheduler.info.TimerInfo;
import com.learning.learningquartzscheduler.timerservice.SchedulerService;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.springframework.beans.factory.annotation.Autowired;

public class SimpleTriggerListener implements TriggerListener {
    private final SchedulerService schedulerService;

    public SimpleTriggerListener(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @Override
    public String getName() {
        return SimpleTriggerListener.class.getSimpleName();
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {
        String timerId=trigger.getKey().getName();
        JobDataMap jobDataMap=jobExecutionContext.getJobDetail().getJobDataMap();
        TimerInfo timerInfo= (TimerInfo) jobDataMap.get(timerId);

        if (!timerInfo.isRunForever()){
            int remainingFireCount = timerInfo.getRemainingFireCount()-1;
            if(remainingFireCount==0){
                return;
            }
            timerInfo.setRemainingFireCount(remainingFireCount);
        }
        schedulerService.updateTimer(timerId,timerInfo);
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {

    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {

    }
}
