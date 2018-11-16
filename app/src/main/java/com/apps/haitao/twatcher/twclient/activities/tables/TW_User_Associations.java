package com.apps.haitao.twatcher.twclient.activities.tables;

import org.litepal.crud.LitePalSupport;

//为了使数据库字段容易区分 改驼峰命名为 下划线命名
public class TW_User_Associations extends LitePalSupport{

    private String self_id;

    private String connect_id;

    //和TW_Users表 是 多 对 一 关系
    private TW_Users tw_users;

    public TW_Users getTw_users() {
        return tw_users;
    }

    public void setTw_users(TW_Users tw_users) {
        this.tw_users = tw_users;
    }

    public String getSelf_id() {
        return self_id;
    }

    public void setSelf_id(String self_id) {
        this.self_id = self_id;
    }

    public String getConnect_id() {
        return connect_id;
    }

    public void setConnect_id(String connect_id) {
        this.connect_id = connect_id;
    }
}
