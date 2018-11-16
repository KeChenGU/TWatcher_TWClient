package com.apps.haitao.twatcher.twclient.activities.gsons;

import java.util.Date;

public class ParentMsg {

    private Date date;

    private String parentId;

    private String ChildId;

    //private List<String> childIdList;

    private String notification;

    private int limitMinutes;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getChildId() {
        return ChildId;
    }

    public void setChildId(String childId) {
        ChildId = childId;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public int getLimitMinutes() {
        return limitMinutes;
    }

    public void setLimitMinutes(int limitMinutes) {
        this.limitMinutes = limitMinutes;
    }
}
