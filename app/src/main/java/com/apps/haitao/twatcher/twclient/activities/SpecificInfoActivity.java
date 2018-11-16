package com.apps.haitao.twatcher.twclient.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.apps.haitao.twatcher.twclient.R;
import com.apps.haitao.twatcher.twclient.activities.adapters.SpecificInfoAdapter0;
import com.apps.haitao.twatcher.twclient.activities.tables.TW_Users;
import com.apps.haitao.twatcher.twclient.activities.tables.User_Specific_Infos;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class SpecificInfoActivity extends AppCompatActivity {

    private List<User_Specific_Infos> userSpecificInfosList;
    //
    private ListView specificInfoListView;

    private Button loginOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_info);
        configControls();
        initUserSpecificInfo();
        SpecificInfoAdapter0 adapter0 = new SpecificInfoAdapter0(this, R.layout.specific_info_item, userSpecificInfosList);
        specificInfoListView.setAdapter(adapter0);
        loginOutButton = (Button)findViewById(R.id.login_out);
        loginOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SpecificInfoActivity.this, LoginActivity.class));
            }
        });
    }

    private void configControls() {
        specificInfoListView = (ListView) findViewById(R.id.specific_info_list);

    }

    private void initUserSpecificInfo(){
        Intent intent = getIntent();
        List<TW_Users> twUsers = LitePal.where("name = ?", intent.getStringExtra("account")).find(TW_Users.class);
        if(twUsers == null) {
            return;
        }
        TW_Users twUser = twUsers.get(0);
        userSpecificInfosList = new ArrayList<>();
        userSpecificInfosList.add(new User_Specific_Infos("账户",
                twUser.getName() == null ? "" : twUser.getName() ));
        userSpecificInfosList.add(new User_Specific_Infos("真实姓名",
                twUser.getUser_name() == null ? "待完善" : twUser.getUser_name()));
        userSpecificInfosList.add(new User_Specific_Infos("身份证号",
                twUser.getId_card_num() == null ? "待完善" : twUser.getId_card_num()));
        userSpecificInfosList.add(new User_Specific_Infos("电话号码", String.valueOf(twUser.getPhone_num())));
        userSpecificInfosList.add(new User_Specific_Infos("电子邮箱",
                twUser.getEmail() == null ? "" : twUser.getEmail()));

    }
}
