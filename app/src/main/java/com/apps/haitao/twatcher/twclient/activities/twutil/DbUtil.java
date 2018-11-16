package com.apps.haitao.twatcher.twclient.activities.twutil;

import android.os.Bundle;

import com.apps.haitao.twatcher.twclient.activities.gsons.TWUser;
import com.apps.haitao.twatcher.twclient.activities.tables.TW_Users;

import org.litepal.tablemanager.Connector;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public final class DbUtil {

    public static void createLocalDataBase() {
        Connector.getDatabase();
    }

    public static TW_Users insertNewUser(Bundle userData){
        //String user_id = UUID.randomUUID().toString();
        TW_Users tw_users = new TW_Users();
        tw_users.setUser_id(UUID.randomUUID().toString());
        tw_users.setName(userData.getString("name", userData.getString("account", "")));
        tw_users.setPassword(md5(userData.getString("password", "123456")));
        tw_users.setGender(userData.getString("gender", "male"));
        tw_users.setPhone_num(Long.parseLong(userData.getString("phone_num", userData.getString("account", "110"))));
        tw_users.setEmail(userData.getString("email", "123456@gmail.com"));
        tw_users.setId_card_num(userData.getString("user_id_card_num", ""));
        tw_users.setIs_child(userData.getBoolean("is_child", false));
        tw_users.setRegister_time(new java.sql.Date(System.currentTimeMillis())); //new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        tw_users.save();
        return tw_users;
    }

    public static TW_Users insertNewUser(TWUser twUser) {
        TW_Users tw_users = new TW_Users();
        tw_users.setUser_id(twUser.getUserId());
        tw_users.setName(twUser.getName());
        tw_users.setPassword(twUser.getPassword());
        tw_users.setGender(twUser.getGender());
        tw_users.setPhone_num(twUser.getPhoneNum());
        tw_users.setEmail(twUser.getEmail());
        tw_users.setId_card_num(twUser.getIdCardNum());
        tw_users.setIs_child(twUser.isChild());
        tw_users.setRegister_time(new java.sql.Date(System.currentTimeMillis())); //new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        tw_users.save();
        return tw_users;
    }

    public static TW_Users insertNewUser(TW_Users twUsers) {
        twUsers.save();
        return twUsers;
    }

    public static String md5(String plainText) {
        //定义一个字节数组
        byte[] secretBytes = null;
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //对字符串进行加密
            md.update(plainText.getBytes());
            //获得加密后的数据
            secretBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        //将加密后的数据转换为16进制数字
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }


}
