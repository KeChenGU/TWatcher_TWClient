package com.apps.haitao.twatcher.twclient.activities.tables;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//为了使数据库字段容易区分 改驼峰命名为 下划线命名

public class TW_Users extends LitePalSupport implements Serializable{

    private String user_id;     //处理后的用户id

    private String name;        //用户名

    private String password;    //用户密码**

    private String user_name;   //*用户真实姓名

    private String gender;      //用户性别

    private String id_card_num; //*用户身份证号

    private long phone_num;     //注册手机号

    private String email;       //*注册邮箱

    private String identity;    //身份

    private boolean is_child;   //是否孩子

    private Date register_time; //注册时间

    //和TW_User_Associations表 是 一对多关系
    private List<TW_User_Associations> tw_user_associationsList = new ArrayList<TW_User_Associations>();

    //和App_Infos表是一对多关系
    private List<App_Infos> app_infosList = new ArrayList<>();

    public List<TW_User_Associations> getTw_user_associationsList() {
        return tw_user_associationsList;
    }

    public void setTw_user_associationsList(List<TW_User_Associations> tw_user_associationsList) {
        this.tw_user_associationsList = tw_user_associationsList;
    }

    public List<App_Infos> getApp_infosList() {
        return app_infosList;
    }

    public void setApp_infosList(List<App_Infos> app_infosList) {
        this.app_infosList = app_infosList;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId_card_num() {
        return id_card_num;
    }

    public void setId_card_num(String id_card_num) {
        this.id_card_num = id_card_num;
    }

    public long getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(long phone_num) {
        this.phone_num = phone_num;
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

    public boolean isIs_child() {
        return is_child;
    }

    public void setIs_child(boolean is_child) {
        this.is_child = is_child;
    }

    public Date getRegister_time() {
        return register_time;
    }

    public void setRegister_time(Date register_time) {
        this.register_time = register_time;
    }
}
