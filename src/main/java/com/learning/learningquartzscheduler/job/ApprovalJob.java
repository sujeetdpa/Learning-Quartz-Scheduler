package com.learning.learningquartzscheduler.job;

import com.learning.learningquartzscheduler.info.TimerInfo;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApprovalJob implements Job {
    Logger log= LoggerFactory.getLogger(ApprovalJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        TimerInfo timerInfo = (TimerInfo) jobDetail.getJobDataMap().get(ApprovalJob.class.getSimpleName());
        log.info("Running Approval Job "+timerInfo.getTotalFireCount());
    }
}
