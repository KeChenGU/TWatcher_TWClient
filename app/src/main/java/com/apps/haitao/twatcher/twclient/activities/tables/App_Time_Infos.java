package com.apps.haitao.twatcher.twclient.activities.tables;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import org.litepal.crud.LitePalSupport;

import java.util.Date;
//为了使数据库字段容易区分 改驼峰命名为 下划线命名
public class App_Time_Infos extends LitePalSupport{

    private Date which_day;

    private String app_name;

    private String first_open_time;

    private String last_close_time;

    //private String using_time;

    private int using_minutes;

    private int open_times;

    //非表数据字段
    private Bitmap app_icon;

    private Drawable app_icon_draw;

    //与Child_Msg 表 是 多对一关系
    private Child_Msg child_msg;

    //与App_Infos 表 是 多对一关系
    private App_Infos app_infos;

    public Child_Msg getChild_msg() {
        return child_msg;
    }

    public void setChild_msg(Child_Msg child_msg) {
        this.child_msg = child_msg;
    }

    public App_Infos getApp_infos() {
        return app_infos;
    }

    public void setApp_infos(App_Infos app_infos) {
        this.app_infos = app_infos;
    }

    public Date getWhich_day() {
        return which_day;
    }

    public void setWhich_day(Date which_day) {
        this.which_day = which_day;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getFirst_open_time() {
        return first_open_time;
    }

    public void setFirst_open_time(String first_open_time) {
        this.first_open_time = first_open_time;
    }

    public String getLast_close_time() {
        return last_close_time;
    }

    public void setLast_close_time(String last_close_time) {
        this.last_close_time = last_close_time;
    }

    public int getUsing_minutes() {
        return using_minutes;
    }

    public void setUsing_minutes(int using_minutes) {
        this.using_minutes = using_minutes;
    }

    public int getOpen_times() {
        return open_times;
    }

    public void setOpen_times(int open_times) {
        this.open_times = open_times;
    }

    public Bitmap getApp_icon() {
        return app_icon;
    }

    public void setApp_icon(Bitmap app_icon) {
        this.app_icon = app_icon;
    }

    public Drawable getApp_icon_draw() {
        return app_icon_draw;
    }

    public void setApp_icon_draw(Drawable app_icon_draw) {
        this.app_icon_draw = app_icon_draw;
    }

    @Override
    public String toString() {
        return "App_Time_Infos{" +
                "which_day=" + which_day +
                ", app_name='" + app_name + '\'' +
                ", first_open_time='" + first_open_time + '\'' +
                ", last_close_time='" + last_close_time + '\'' +
                ", using_minutes=" + using_minutes +
                ", open_times=" + open_times +
                ", app_icon=" + app_icon +
                ", app_icon_draw=" + app_icon_draw +
                ", child_msg=" + child_msg +
                ", app_infos=" + app_infos +
                '}';
    }
}
