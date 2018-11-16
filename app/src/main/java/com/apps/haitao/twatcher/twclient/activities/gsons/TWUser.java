package com.apps.haitao.twatcher.twclient.activities.gsons;

import com.apps.haitao.twatcher.twclient.activities.tables.App_Infos;
import com.apps.haitao.twatcher.twclient.activities.tables.TW_User_Associations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TWUser {

    private String userId;     //处理后的用户id

    private String name;        //用户名

    private String password;    //用户密码**

    private String userName;   //*用户真实姓名

    private String gender;      //用户性别

    private String idCardNum; //*用户身份证号

    private long phoneNum;     //注册手机号

    private String email;       //*注册邮箱

    private String identity;    //身份

    private boolean isChild;   //是否孩子

    private String registerTime; //注册时间

//    //和TW_User_Associations表 是 一对多关系
//    private List<TWUserAssociation> twUserAssociationList = new ArrayList<TWUserAssociation>();
//
//    //和App_Infos表是一对多关系
//    private List<AppInfo> appInfoList = new ArrayList<>();


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public long getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(long phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public boolean isChild() {
        return isChild;
    }

    public void setIsChild(boolean isChild) {
        this.isChild = isChild;
    }

    public void setChild(boolean child) {
        isChild = child;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }
}
