package com.apps.haitao.twatcher.twclient.activities.gsons;

import java.util.Date;

public class AppTimeInfo {

    private Date whichDay;

    private String appName;

    private String firstOpenTime;

    private String lastCloseTime;

    //private String usingTime;

    private int usingMinutes;

    private int openTimes;

    //
    private String appId;

    public Date getWhichDay() {
        return whichDay;
    }

    public void setWhichDay(Date whichDay) {
        this.whichDay = whichDay;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getFirstOpenTime() {
        return firstOpenTime;
    }

    public void setFirstOpenTime(String firstOpenTime) {
        this.firstOpenTime = firstOpenTime;
    }

    public String getLastCloseTime() {
        return lastCloseTime;
    }

    public void setLastCloseTime(String lastCloseTime) {
        this.lastCloseTime = lastCloseTime;
    }

    public int getUsingMinutes() {
        return usingMinutes;
    }

    public void setUsingMinutes(int usingMinutes) {
        this.usingMinutes = usingMinutes;
    }

    public int getOpenTimes() {
        return openTimes;
    }

    public void setOpenTimes(int openTimes) {
        this.openTimes = openTimes;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
