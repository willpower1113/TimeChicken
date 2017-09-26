package com.willpower.timechicken.activity.setting;

import android.support.annotation.IdRes;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.willpower.timechicken.R;
import com.willpower.timechicken.activity.home.HomeActivity;
import com.willpower.timechicken.base.BaseActivity;
import com.willpower.timechicken.comm.Constants;
import com.willpower.timechicken.utils.ActivityManager;
import com.willpower.timechicken.utils.SharedPreferences;

/**
 * Created by Administrator on 2017/9/25.
 * 模式设置
 */

public class AppSettingActivity extends BaseActivity {
    RadioGroup mRadios;
    RadioButton radio_small, radio_middle, radio_big;
    Switch mSwitch;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_app_set;
    }

    @Override
    protected void initView() {
        setToolbarTitle("个性化设置");
        init();
        initRadioGroup();
        initSwitch();
        initMenu();
    }

    private void init() {
        mRadios = (RadioGroup) findViewById(R.id.mRadios);
        radio_small = (RadioButton) findViewById(R.id.radio_small);
        radio_middle = (RadioButton) findViewById(R.id.radio_middle);
        radio_big = (RadioButton) findViewById(R.id.radio_big);
        mSwitch = (Switch) findViewById(R.id.switch_n_d_theme);
    }

    private void initRadioGroup() {
        String textSize = (String) SharedPreferences.getInstance(this).getValue(Constants.SP_TEXT, String.class);
        switch (textSize) {
            case Constants.THEME_SMALL:
                radio_small.setChecked(true);
                break;
            case Constants.THEME_MIDDLE:
                radio_middle.setChecked(true);
                break;
            case Constants.THEME_BIG:
                radio_big.setChecked(true);
                break;
        }
        mRadios.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.radio_small:
                        SharedPreferences.getInstance(AppSettingActivity.this).putValue(Constants.SP_TEXT, Constants.THEME_SMALL);
                        break;
                    case R.id.radio_middle:
                        SharedPreferences.getInstance(AppSettingActivity.this).putValue(Constants.SP_TEXT, Constants.THEME_MIDDLE);
                        break;
                    case R.id.radio_big:
                        SharedPreferences.getInstance(AppSettingActivity.this).putValue(Constants.SP_TEXT, Constants.THEME_BIG);
                        break;
                }
            }
        });
    }

    private void initSwitch() {
        String dn = (String) SharedPreferences.getInstance(this).getValue(Constants.SP_DN, String.class);
        if (dn.equals(Constants.THEME_DAY)) {
            mSwitch.setChecked(false);
        } else {
            mSwitch.setChecked(true);
        }
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.getInstance(AppSettingActivity.this).putValue(Constants.SP_DN, Constants.THEME_NIGHT);
                } else {
                    SharedPreferences.getInstance(AppSettingActivity.this).putValue(Constants.SP_DN, Constants.THEME_DAY);
                }
            }
        });
    }

    private void initMenu() {
        setToolbarAction("保存修改",new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.restartApplication(context, HomeActivity.class);
            }
        });
    }

}
