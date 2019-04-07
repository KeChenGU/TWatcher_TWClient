package com.apps.haitao.twatcher.twclient.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.haitao.twatcher.twclient.R;
import com.apps.haitao.twatcher.twclient.activities.gsons.TWUser;
import com.apps.haitao.twatcher.twclient.activities.tables.TW_Users;
import com.apps.haitao.twatcher.twclient.activities.twutil.DbUtil;
import com.apps.haitao.twatcher.twclient.activities.twutil.HttpUtil;
import com.apps.haitao.twatcher.twclient.activities.twutil.JsonUtil;
import com.apps.haitao.twatcher.twclient.activities.twutil.LogUtil;
import com.apps.haitao.twatcher.twclient.activities.twutil.Utility;
import com.google.gson.Gson;

import org.litepal.LitePal;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private String backNews = "";

    private TextView toRegisterText;

    private TextView loginAccountText;

    private TextView loginPasswordText;

    private TextView fogetPasswordText;

    private Button loginButton;

    private Button registerButton;

    private CheckBox rememberAccountBox;

    //
    private SharedPreferences preferences;

    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DbUtil.createLocalDataBase();
        //Connector.getDatabase();
        toRegisterText = (TextView)findViewById(R.id.to_register_text);
        loginButton = (Button)findViewById(R.id.login_button);
        registerButton = (Button)findViewById(R.id.register_button);
        toRegisterText.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        loginAccountText = (TextView)findViewById(R.id.login_account);
        loginPasswordText = (TextView)findViewById(R.id.login_password);
        fogetPasswordText = (TextView)findViewById(R.id.forget_password);
        rememberAccountBox = (CheckBox)findViewById(R.id.remember_account);
        fogetPasswordText.setOnClickListener(this);
        //rememberAccountBox.setOnClickListener(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = preferences.getBoolean("remember_account", false);
        if (isRemember) {
            loginAccountText.setText(preferences.getString("account", ""));
            loginPasswordText.setText(preferences.getString("password", ""));
            rememberAccountBox.setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        List<TW_Users> twUsersList = null;
        Bundle twUser = new Bundle();
        switch (v.getId()) {
            case R.id.login_button:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        checkInputsNetWorkly();
                    }
                }).start();
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    LogUtil.e(Utility.WATCH_TAG, "睡眠失败!");
                }
                if (!dealLoginBackNews()) {
                    Toast.makeText(this, backNews, Toast.LENGTH_SHORT).show();
                    //backNews = "";
                    break;
                }
                intent.setClass(LoginActivity.this, WatchActivity.class);
                twUsersList = checkInputs();
                if(twUsersList != null && twUsersList.size() > 0){
                    editor = preferences.edit();
                    if (rememberAccountBox.isChecked()) {
                        editor.putBoolean("remember_account", true);
                        editor.putString("account", loginAccountText.getText().toString());
                        editor.putString("password", loginPasswordText.getText().toString());
                    }else {
                        editor.clear();
                    }
                    editor.apply();
                    twUser.putSerializable("loginUser", (Serializable) twUsersList.get(0));
                    intent.putExtras(twUser);
                    startActivity(intent);
                } else {
                    LogUtil.e(Utility.WATCH_TAG, "服务器插入异常!");
                    Toast.makeText(this, "服务器插入异常!", Toast.LENGTH_SHORT).show();
                    //break;
                }
                //backNews = "";
                break;
            case R.id.to_register_text:
            case R.id.register_button:
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.forget_password:
                intent.setClass(LoginActivity.this, ChangePassActivity.class);
                startActivity(intent);
                break;
        }
    }

    private List<TW_Users> checkInputs() {
        String account = loginAccountText.getText().toString();
        String password = DbUtil.md5(loginPasswordText.getText().toString());
        List<TW_Users> accountFlagList = LitePal.where("phone_num = ?", account).find(TW_Users.class);
        List<TW_Users> passwordFlagList = LitePal.where("phone_num = ? AND password = ?",
                account, password).find(TW_Users.class);

        if (accountFlagList == null || accountFlagList.size() == 0) {
            //Toast.makeText(this, "账户不存在!", Toast.LENGTH_SHORT).show();
            return null;
        } else if (passwordFlagList == null || passwordFlagList.size() == 0) {
            Toast.makeText(this, "密码错误!", Toast.LENGTH_SHORT).show();
            return null;
        }
        List<TW_Users> tw_usersList = LitePal.where("phone_num = ? AND password = ?", account, password).find(TW_Users.class);
        //if (tw_usersList == null || tw_usersList.size() == 0) {
        //    return false;
        //}
        return tw_usersList;
    }

    private List<TW_Users> checkInputsLocally(String account, String password) {

        return null;
    }

    private void checkInputsNetWorkly() {
        //final String account = loginAccountText.getText().toString().trim();
        //final String password = DbUtil.md5(loginPasswordText.getText().toString().trim());
        String account = loginAccountText.getText() == null ? "" : loginAccountText.getText().toString();
        String password = loginPasswordText.getText() == null ? "123456"
                : DbUtil.md5(loginPasswordText.getText().toString().trim());
        if (account == null || password == null || account.equals("") || password.equals("")) {
            backNews = "账号密码不得为空!";
            return;
        }
        try {
            backNews = HttpUtil.sendLoginRequest(account, password);
            Log.d("json", backNews);
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e(Utility.WATCH_TAG, "登录异常!");
            //Toast.makeText(this, "请检查网络！", Toast.LENGTH_SHORT).show();
            //return;
        }

    }

    private boolean dealLoginBackNews() {
        if (!JsonUtil.isUserJSONValidByGson(backNews)) {
            LogUtil.d(Utility.WATCH_TAG, backNews);
            return false;
        }
        TWUser twUser = new Gson().fromJson(backNews, TWUser.class);
        if (twUser == null) {
            LogUtil.e(Utility.WATCH_TAG, backNews);
            return false;
        }
        List<TW_Users> twUsersList = LitePal.where("user_id = ?",
                twUser.getUserId()
        ).find(TW_Users.class);
        if (twUsersList == null || twUsersList.size() == 0) {
            DbUtil.insertNewUser(twUser);
            //return true;
        }
        return true;
    }

}
