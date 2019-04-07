package com.apps.haitao.twatcher.twclient.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.haitao.twatcher.twclient.R;
import com.apps.haitao.twatcher.twclient.activities.twutil.HttpUtil;
import com.apps.haitao.twatcher.twclient.activities.twutil.SMSUtil;

import java.util.regex.Pattern;

public class ChangePassActivity extends AppCompatActivity {

    private EditText phoneNum;

    private EditText newPassword;

    private EditText checkPassword;

    private EditText checkCode;

    private Button sendButton;

    private Button yesButton;

    private String phone;

    private String password;

    private String passwordAgain;

    private String verifyCode;

    private boolean isEmpty = true;

    private boolean isEmpty_a = true;

    private boolean isLegal = false;

    private boolean isSame = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        SMSUtil.assignEventHandler(new SMSUtil.SmsDealer() {
            @Override
            public void sendSuccess() {
                Toast.makeText(ChangePassActivity.this, "验证码已发送，请注意查收！"
                        , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void sendFail() {
                Toast.makeText(ChangePassActivity.this,
                        "验证码发送失败！请检查网络后再次尝试！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void passRight() {
                try {
                    HttpUtil.sendChangePasswordRequest(phone, password);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ChangePassActivity.this, "连接服务器异常！", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ChangePassActivity.this, "修改密码成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void passWrong() {
                Toast.makeText(ChangePassActivity.this, "验证码输入错误！", Toast.LENGTH_SHORT).show();
            }
        });
        configUIs();
        setUIs();
    }

    @Override
    protected void onDestroy() {
        SMSUtil.resignEventHandler();
        super.onDestroy();
    }


    private void configUIs() {
        phoneNum = findViewById(R.id.input_phone);
        newPassword = findViewById(R.id.input_new_pass);
        checkPassword = findViewById(R.id.input_new_pass_again);
        checkCode = findViewById(R.id.input_check);
        sendButton = findViewById(R.id.get_check);
        yesButton = findViewById(R.id.input_yes);
    }

    private void setUIs() {
        phoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                phone = phoneNum.getText() == null ? "" : phoneNum.getText().toString();
                String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
                Pattern pattern = Pattern.compile(regex);
                isLegal = pattern.matcher(phone).matches();
            }
        });
        newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isEmpty = newPassword.getText() == null || newPassword.getText().toString().equals("");
                password = isEmpty ? "18906632597" : newPassword.getText().toString();
                isSame = password.equals(passwordAgain);
            }
        });
        checkPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isEmpty_a = checkPassword.getText() == null || checkPassword.getText().toString().equals("");
                passwordAgain = isEmpty_a ? "18906632597" : checkPassword.getText().toString();
                isSame = password.equals(passwordAgain);
            }
        });
        checkCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                verifyCode = checkCode.getText() == null ? "666666" : checkCode.getText().toString();
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkInputWork()) {
                    return;
                }
                SMSUtil.requestVerificationCode("86", phone);
            }
        });
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkInputWork()) {
                    return;
                }
                SMSUtil.submitVerificationCode("86", phone, verifyCode);
            }
        });
    }

    private boolean checkInputWork() {
        if (isEmpty || isEmpty_a) {
            Toast.makeText(ChangePassActivity.this, "输入不得为空！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isLegal) {
            Toast.makeText(ChangePassActivity.this, "电话号码输入非法！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isSame) {
            Toast.makeText(ChangePassActivity.this, "两次密码不一致！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
