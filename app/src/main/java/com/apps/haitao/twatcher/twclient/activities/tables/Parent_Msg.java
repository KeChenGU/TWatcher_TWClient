package com.apps.haitao.twatcher.twclient.activities.tables;

import java.util.Date;

public class Parent_Msg {

    private Date date;

    private String parent_id;

    private String control_child_id;

    //private List<String> child_idList;

    private String notification;

    private int limit_minutes;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getControl_child_id() {
        return control_child_id;
    }

    public void setControl_child_id(String control_child_id) {
        this.control_child_id = control_child_id;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public int getLimit_minutes() {
        return limit_minutes;
    }

    public void setLimit_minutes(int limit_minutes) {
        this.limit_minutes = limit_minutes;
    }
}
