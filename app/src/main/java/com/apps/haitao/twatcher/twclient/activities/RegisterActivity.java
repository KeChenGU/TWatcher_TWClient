package com.apps.haitao.twatcher.twclient.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.haitao.twatcher.twclient.R;
import com.apps.haitao.twatcher.twclient.activities.tables.TW_Users;
import com.apps.haitao.twatcher.twclient.activities.twutil.NetWorkUtil;

import org.litepal.LitePal;

import java.util.List;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private TextView toLoginText;

    private Button registerButton;

    private Button sendCheckCodeButton;

    private EditText accountInput;

    private EditText passwordInput;

    private TextView accountErrorText;

    private TextView passwordErrorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toLoginText = (TextView)findViewById(R.id.to_login_text);
        registerButton = (Button)findViewById(R.id.register_button);
        sendCheckCodeButton = (Button)findViewById(R.id.send_check_code);
        accountInput = (EditText)findViewById(R.id.register_account);
        passwordInput = (EditText)findViewById(R.id.register_password);
        accountErrorText = (TextView)findViewById(R.id.check_account);
        passwordErrorText = (TextView)findViewById(R.id.check_password);

        toLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkNetWorkState()) {
                   Toast.makeText(RegisterActivity.this, "当前网络不可用！", Toast.LENGTH_SHORT).show();
                   NetWorkUtil.showSetNetworkDialog(RegisterActivity.this);
                } else if (checkOverLay()) {
                    Toast.makeText(RegisterActivity.this, "该手机号已经被注册!", Toast.LENGTH_SHORT).show();
                } else if (checkInputMessage()) {
                    Intent intent = new Intent();
                    intent.setClass(RegisterActivity.this, SelectIdentityActivity.class);
                    Bundle userData = new Bundle();
                    userData.putString("Action", "handleRegister");
                    userData.putString("account", accountInput.getText() == null ? "" : accountInput.getText().toString());
                    userData.putString("password", passwordInput.getText() == null ? "" : passwordInput.getText().toString());
                    intent.putExtras(userData);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                }else {
                    Toast.makeText(RegisterActivity.this, "请确保账号密码输入格式无误!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        sendCheckCodeButton.setClickable(false);
        sendCheckCodeButton.setTextColor(Color.WHITE);
        accountInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String accountInputText = accountInput.getText().toString();
                accountInputText = accountInput.getText() == null ? "" : accountInputText;
                boolean flag = checkInputPhoneAccountInstantly(accountInputText);
                if (!flag) {
                    accountErrorText.setText("请输入合法的大陆电话!");
                }else {
                    if (checkOverLay()){
                        accountErrorText.setText("该手机号已被注册");
                    }else{
                        accountErrorText.setText("");
                    }
                }
                if (!flag || accountInputText.length() != 11) {
                    sendCheckCodeButton.setClickable(false);
                    //sendCheckCodeButton.setBackgroundColor(Color.parseColor("#D6D7D7"));
                    sendCheckCodeButton.setTextColor(Color.WHITE);
                }else {
                    sendCheckCodeButton.setClickable(true);
                    //sendCheckCodeButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    sendCheckCodeButton.setTextColor(Color.BLACK);
                }
            }
        });
        passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int flag = checkInputPasswordInstantly(passwordInput.getText().toString());
                switch (flag){
                    case 0:
                        passwordErrorText.setText("");
                        break;
                    case 1:
                        passwordErrorText.setText("密码长度过短!");
                        break;
                    case 2:
                        passwordErrorText.setText("密码长度过长!");
                        break;
                    case 3:
                        passwordErrorText.setText("密码中不能包含除下划线外的特殊字符.");
                        break;
                    case 4:
                        passwordErrorText.setText("密码中必须包含大小写字母和阿拉伯数字!");
                        break;
                    case 5:
                        passwordErrorText.setText("");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private boolean checkInputPhoneAccountInstantly(String inputPhone) {
        if (inputPhone == null || inputPhone.equals("")) {
            return true;
        }
        //中国大陆号码正则表达式
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(inputPhone).matches();
    }

    private int checkInputPasswordInstantly(String inputPassword) {
        if (inputPassword == null || inputPassword.equals("")) {
            return 0;
        }
        if (inputPassword.length() < 8) {
            return 1;
        }
        if (inputPassword.length() > 16) {
            return 2;
        }
        if (inputPassword.matches("^\\w*\\W+\\w*$")) {
            return 3;
        }
        //String regex = "^(?![A-Z]*$)(?![a-z]*$)(?![0-9]*$)(?![^a-zA-Z0-9]*$)\\S{8,16}$"; //匹配特殊符号 大小写字母 数字
        //String regex1 = "^(?![A-Z]*$)(?![a-z]*$)(?![0-9]*$)(?!_*$)\\w{8,16}$";
        //String regex2 = "^(?![A-Z]*$)(?![a-z]*$)(?![0-9]*$)(?![^a-zA-Z0-9]*$)\w{8,16}$";
        //Pattern pattern = Pattern.compile(regex);
        String regexUpper = "[^A-Z]{8,16}", regexLower = "[^a-z]{8,16}", regexNum = "[^0-9]{8,16}";
//        if (inputPassword.matches(regexUpper) || inputPassword.matches(regexLower)
//                || inputPassword.matches(regexNum)) {
//            return 4;
//        }
        if (inputPassword.matches(regexUpper)) {
            return 4;
        } else if (inputPassword.matches(regexLower)) {
            return 4;
        } else if (inputPassword.matches(regexNum)) {
            return 4;
        } else {
            return 5;
        }
    }

    private boolean checkOverLay() {
        List<TW_Users> twUsersList = LitePal.where("name = ?", accountInput.getText().toString()).find(TW_Users.class);
        return ((twUsersList != null) && (twUsersList.size() > 0));
    }

    private boolean checkInputMessage() {
        return (accountInput.getText() != null) && (!accountInput.getText().toString().equals(""))
                && checkInputPhoneAccountInstantly(accountInput.getText().toString())
                && (checkInputPasswordInstantly(passwordInput.getText().toString()) == 5);
    }

    private boolean checkNetWorkState() {
//        return NetWorkUtil.isNetworkConnected(this)
//                && NetWorkUtil.isMobileConnected(this)
//                && NetWorkUtil.isWifiConnected(this);
        return NetWorkUtil.isNetworkAvailable(this);// && NetWorkUtil._ping() == 0;
    }
}
