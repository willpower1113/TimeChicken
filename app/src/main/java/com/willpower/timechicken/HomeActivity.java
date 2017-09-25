package com.willpower.timechicken;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.willpower.timechicken.activity.setting.AppSettingActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void jump(View view) {
        startActivity(new Intent(this, AppSettingActivity.class));
    }
}
