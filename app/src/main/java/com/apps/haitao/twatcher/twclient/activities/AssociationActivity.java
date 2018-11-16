package com.apps.haitao.twatcher.twclient.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.haitao.twatcher.twclient.R;
import com.apps.haitao.twatcher.twclient.activities.twutil.HttpUtil;
import com.apps.haitao.twatcher.twclient.activities.twutil.LogUtil;
import com.apps.haitao.twatcher.twclient.activities.twutil.Utility;

import java.io.IOException;

public class AssociationActivity extends AppCompatActivity {

    private EditText inputPhoneEdit;

    private Button yesButton;

    private Intent dealIntent;

    //
    private String backNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_association);
        yesButton = findViewById(R.id.input_yes);
        inputPhoneEdit = findViewById(R.id.input_phone);
        dealIntent = getIntent();
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String parentId = dealIntent.getStringExtra("parentId");
                        LogUtil.d(Utility.WATCH_TAG, "!!!" + parentId);
                        String phoneNum = inputPhoneEdit.getText().toString();
                        try {
                            backNews = HttpUtil.sendCreateAssociationRequest(parentId, phoneNum);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (backNews != null) {
                    Toast.makeText(AssociationActivity.this, backNews, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AssociationActivity.this, "反馈异常!", Toast.LENGTH_SHORT).show();
                    LogUtil.e(Utility.WATCH_TAG, "反馈异常!");
                }
            }
        });

    }
}
