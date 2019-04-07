package com.apps.haitao.twatcher.twclient.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.apps.haitao.twatcher.twclient.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class SelectIdentityActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_identity);
        CircleImageView childImage = (CircleImageView)findViewById(R.id.child_identity);
        CircleImageView parentImage = (CircleImageView)findViewById(R.id.parent_identity);
        TextView childText = (TextView)findViewById(R.id.child_identity_text);
        TextView parentText = (TextView)findViewById(R.id.parent_identity_text);
        childImage.setOnClickListener(this);
        parentImage.setOnClickListener(this);
        childText.setOnClickListener(this);
        parentText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent lastIntent = getIntent();
        Intent intent = new Intent(SelectIdentityActivity.this, SelectGenderActivity.class);
        switch (v.getId()) {
            case R.id.child_identity:
            case R.id.child_identity_text:
                intent.putExtra("Identity", "child");
                break;
            case R.id.parent_identity:
            case R.id.parent_identity_text:
                intent.putExtra("Identity", "parent");
                break;
            default:
                break;
        }
        intent.putExtras(lastIntent.getExtras() == null ? new Bundle() : lastIntent.getExtras());
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        finish();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }
}
