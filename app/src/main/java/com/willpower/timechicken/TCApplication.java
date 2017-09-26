package com.willpower.timechicken;

import android.app.Application;

import com.willpower.timechicken.comm.Constants;
import com.willpower.timechicken.utils.SharedPreferences;

/**
 * Created by Administrator on 2017/9/25.
 */

public class TCApplication extends Application {

    public static TCApplication getInstance;

    public int appTheme;

    @Override
    public void onCreate() {
        super.onCreate();
        getInstance = this;
        chooseThemeBySharedPreferences();
        setTheme(appTheme);
    }
    /**
     * 设置主题
     */
    private void chooseThemeBySharedPreferences(){
        String size = (String) SharedPreferences.getInstance(this).getValue(Constants.SP_TEXT, String.class);
        String dn = (String) SharedPreferences.getInstance(this).getValue(Constants.SP_DN, String.class);
        if (size == null) {
            size = Constants.THEME_SMALL;
            SharedPreferences.getInstance(this).putValue(Constants.SP_TEXT,size);
        }
        if (dn == null) {
            dn = Constants.THEME_DAY;
            SharedPreferences.getInstance(this).putValue(Constants.SP_DN, dn);
        }
        if (dn.equals(Constants.THEME_DAY)) {
            switch (size) {
                case Constants.THEME_SMALL:
                    appTheme = R.style.App_TextSize_Small_Day;
                    break;
                case Constants.THEME_MIDDLE:
                    appTheme = R.style.App_TextSize_Middle_Day;
                    break;
                case Constants.THEME_BIG:
                    appTheme = R.style.App_TextSize_Big_Day;
                    break;
            }
        } else {
            switch (size) {
                case Constants.THEME_SMALL:
                    appTheme = R.style.App_TextSize_Small_Night;
                    break;
                case Constants.THEME_MIDDLE:
                    appTheme = R.style.App_TextSize_Middle_Night;
                    break;
                case Constants.THEME_BIG:
                    appTheme = R.style.App_TextSize_Big_Night;
                    break;
            }
        }

    }

}
