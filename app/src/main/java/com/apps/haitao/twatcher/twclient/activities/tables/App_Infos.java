package com.apps.haitao.twatcher.twclient.activities.tables;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//为了使数据库字段容易区分 改驼峰命名为 下划线命名
public class App_Infos extends LitePalSupport{

    private String app_id; //

    private String app_name; //

    private String app_version;

    private int app_icon_id;

    private String app_icon_path;

    private byte[] app_icon_bytes;

    private Date installed_time;

    //与TW_Users 表 是 多对一关系
    private TW_Users tw_users;

    //与App_Time_Infos 表 是 一对多关系
    private List<App_Time_Infos> app_time_infosList = new ArrayList<App_Time_Infos>();

    public List<App_Time_Infos> getApp_time_infosList() {
        return app_time_infosList;
    }

    public void setApp_time_infosList(List<App_Time_Infos> app_time_infosList) {
        this.app_time_infosList = app_time_infosList;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public int getApp_icon_id() {
        return app_icon_id;
    }

    public void setApp_icon_id(int app_icon_id) {
        this.app_icon_id = app_icon_id;
    }

    public String getApp_icon_path() {
        return app_icon_path;
    }

    public void setApp_icon_path(String app_icon_path) {
        this.app_icon_path = app_icon_path;
    }

    public byte[] getApp_icon_bytes() {
        return app_icon_bytes;
    }

    public void setApp_icon_bytes(byte[] app_icon_bytes) {
        this.app_icon_bytes = app_icon_bytes;
    }

    public Date getInstalled_time() {
        return installed_time;
    }

    public void setInstalled_time(Date installed_time) {
        this.installed_time = installed_time;
    }
}
