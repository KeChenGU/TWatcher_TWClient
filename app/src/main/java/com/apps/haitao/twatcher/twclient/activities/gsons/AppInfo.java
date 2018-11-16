package com.apps.haitao.twatcher.twclient.activities.gsons;

import java.util.Date;

public class AppInfo {

    private String appId; //

    private String appName; //

    private String appVersion;

    private int appIconId;

    private String appIconPath;

    private byte[] appIconBytes;

    private Date installedTime;

    //
    private String userId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public int getAppIconId() {
        return appIconId;
    }

    public void setAppIconId(int appIconId) {
        this.appIconId = appIconId;
    }

    public String getAppIconPath() {
        return appIconPath;
    }

    public void setAppIconPath(String appIconPath) {
        this.appIconPath = appIconPath;
    }

    public byte[] getAppIconBytes() {
        return appIconBytes;
    }

    public void setAppIconBytes(byte[] appIconBytes) {
        this.appIconBytes = appIconBytes;
    }

    public Date getInstalledTime() {
        return installedTime;
    }

    public void setInstalledTime(Date installedTime) {
        this.installedTime = installedTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
