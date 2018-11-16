package com.apps.haitao.twatcher.twclient.activities.tables;

import org.litepal.crud.LitePalSupport;

public class Parent_Settings extends LitePalSupport{

    private String notifyFrequency;

    //private String notifyYear;

    //private String notifyMonth;

    private String notifyDay;

    private String notifyHour;

    private String notifyMinute;

    private String allowCache;

    //与TW_users表是一对一关系
    private TW_Users tw_users;

    public TW_Users getTw_users() {
        return tw_users;
    }

    public void setTw_users(TW_Users tw_users) {
        this.tw_users = tw_users;
    }

    public String getNotifyFrequency() {
        return notifyFrequency;
    }

    public void setNotifyFrequency(String notifyFrequency) {
        this.notifyFrequency = notifyFrequency;
    }

    public String getNotifyDay() {
        return notifyDay;
    }

    public void setNotifyDay(String notifyDay) {
        this.notifyDay = notifyDay;
    }

    public String getNotifyHour() {
        return notifyHour;
    }

    public void setNotifyHour(String notifyHour) {
        this.notifyHour = notifyHour;
    }

    public String getNotifyMinute() {
        return notifyMinute;
    }

    public void setNotifyMinute(String notifyMinute) {
        this.notifyMinute = notifyMinute;
    }

    public String getAllowCache() {
        return allowCache;
    }

    public void setAllowCache(String allowCache) {
        this.allowCache = allowCache;
    }
}
