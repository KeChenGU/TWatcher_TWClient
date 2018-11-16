package com.apps.haitao.twatcher.twclient.activities.tables;

import com.apps.haitao.twatcher.twclient.activities.tables.App_Time_Infos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Child_Msg {

    private Date date; 						//当前日期

    //private String parent_id;					//家长ID

    private String child_id;				    //孩子ID

    private int total_minutes;					//孩子使用(指定所有)App的总时间(分钟)

    private int open_num;						//打开App总次数

    //private int limit_minutes;                  //限制时间

    //与App_Time_Infos表是 一对多关系
    //App应用使用时间信息表
    private List<App_Time_Infos> app_time_infosList = new ArrayList<App_Time_Infos>();

    public List<App_Time_Infos> getApp_time_infosList() {
        return app_time_infosList;
    }

    public void setApp_time_infosList(List<App_Time_Infos> app_time_infosList) {
        this.app_time_infosList = app_time_infosList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getChild_id() {
        return child_id;
    }

    public void setChild_id(String child_id) {
        this.child_id = child_id;
    }

    public int getTotal_minutes() {
        return total_minutes;
    }

    public void setTotal_minutes(int total_minutes) {
        this.total_minutes = total_minutes;
    }

    public int getOpen_num() {
        return open_num;
    }

    public void setOpen_num(int open_num) {
        this.open_num = open_num;
    }

//    public int getLimit_minutes() {
//        return limit_minutes;
//    }
//
//    public void setLimit_minutes(int limit_minutes) {
//        this.limit_minutes = limit_minutes;
//    }
}
