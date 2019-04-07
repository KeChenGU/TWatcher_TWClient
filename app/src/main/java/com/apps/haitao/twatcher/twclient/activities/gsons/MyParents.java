package com.apps.haitao.twatcher.twclient.activities.gsons;

public class MyParents {

    private String sex;

    private String iconUrl;

    private String parentName;

    private String parentPhone;

    public MyParents() {
    }

    public MyParents(String sex, String iconUrl, String parentName, String parentPhone) {
        this.sex = sex;
        this.iconUrl = iconUrl;
        this.parentName = parentName;
        this.parentPhone = parentPhone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }
}
