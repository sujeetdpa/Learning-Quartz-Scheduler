package com.learning.learningquartzscheduler.info;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class TimerInfo implements Serializable {
    private int totalFireCount;
    private int remainingFireCount;
    private boolean isRunForever;
    private long repeatInterval;
    private long initialOffset;
    private String callBackData;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public int getRemainingFireCount() {
        return remainingFireCount;
    }

    public void setRemainingFireCount(int remainingFireCount) {
        this.remainingFireCount = remainingFireCount;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public boolean isRunForever() {
        return isRunForever;
    }

    public void setRunForever(boolean runForever) {
        isRunForever = runForever;
    }

    public int getTotalFireCount() {
        return totalFireCount;
    }

    public void setTotalFireCount(int totalFireCount) {
        this.totalFireCount = totalFireCount;
    }

    public long getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(long repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public long getInitialOffset() {
        return initialOffset;
    }

    public void setInitialOffset(long initialOffset) {
        this.initialOffset = initialOffset;
    }

    public String getCallBackData() {
        return callBackData;
    }

    public void setCallBackData(String callBackData) {
        this.callBackData = callBackData;
    }
}
