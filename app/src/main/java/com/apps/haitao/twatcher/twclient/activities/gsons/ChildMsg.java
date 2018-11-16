package com.apps.haitao.twatcher.twclient.activities.gsons;

import java.util.Date;
import java.util.List;

public class ChildMsg {

    private Date date; 						//当前日期

    //private String parentId;					//家长ID

    private String childId;				    //孩子ID

    private int totalMinutes;					//孩子使用(指定所有)App的总时间(分钟)

    private int openNum;

    private List<AppTimeInfo> appTimeInfoList;

    public List<AppTimeInfo> getAppTimeInfoList() {
        return appTimeInfoList;
    }

    public void setAppTimeInfoList(List<AppTimeInfo> appTimeInfoList) {
        this.appTimeInfoList = appTimeInfoList;
    }

    //

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(int totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public int getOpenNum() {
        return openNum;
    }

    public void setOpenNum(int openNum) {
        this.openNum = openNum;
    }
}
