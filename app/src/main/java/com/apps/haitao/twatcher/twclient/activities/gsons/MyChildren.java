package com.apps.haitao.twatcher.twclient.activities.gsons;

public class MyChildren {

    public MyChildren() {
    }

    public MyChildren(String sex, String iconUrl, String childName, String childPhone, String selectCond) {
        this.sex = sex;
        this.iconUrl = iconUrl;
        this.childName = childName;
        this.childPhone = childPhone;
        this.selectCond = selectCond;
    }

    private String sex;

    private String iconUrl;

    private String childName;

    private String childPhone;

    private String selectCond;

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

    public String getChildPhone() {
        return childPhone;
    }

    public void setChildPhone(String childPhone) {
        this.childPhone = childPhone;
    }

    public String getSelectCond() {
        return selectCond;
    }

    public void setSelectCond(String selectCond) {
        this.selectCond = selectCond;
    }

    public String getChildName() {

        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }
}
