package com.apps.haitao.twatcher.twclient.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.apps.haitao.twatcher.twclient.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class SelectGenderActivity extends AppCompatActivity implements View.OnClickListener{

    private String identity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_gender);
        CircleImageView maleImage = (CircleImageView)findViewById(R.id.gender_male);
        CircleImageView femaleImage = (CircleImageView)findViewById(R.id.gender_female);
        TextView maleText = (TextView)findViewById(R.id.gender_male_text);
        TextView femaleText = (TextView)findViewById(R.id.gender_female_text);
        Intent intent = getIntent();
        identity = intent.getStringExtra("Identity");
        switch (identity) {
            case "child":
                maleImage.setImageResource(R.drawable.child_boy);
                femaleImage.setImageResource(R.drawable.child_girl);
                break;
            case "parent":
                maleImage.setImageResource(R.drawable.parent_father);
                femaleImage.setImageResource(R.drawable.parent_mother);
                break;
            default:
                break;
        }
        maleImage.setOnClickListener(this);
        femaleImage.setOnClickListener(this);
        maleText.setOnClickListener(this);
        femaleImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent lastIntent = getIntent();
        Intent intent = new Intent(SelectGenderActivity.this, WatchActivity.class);
        switch (v.getId()) {
            case R.id.gender_male:
            case R.id.gender_male_text:
                intent.putExtra("Identity", identity);
                intent.putExtra("Gender", "male");
                break;
            case R.id.gender_female:
            case R.id.gender_female_text:
                intent.putExtra("Identity", identity);
                intent.putExtra("Gender", "female");
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
