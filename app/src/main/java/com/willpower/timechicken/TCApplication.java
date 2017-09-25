package com.willpower.timechicken;

import android.app.Application;

import com.willpower.timechicken.comm.Constants;
import com.willpower.timechicken.utils.SharedPreferences;

/**
 * Created by Administrator on 2017/9/25.
 */

public class TCApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        chooseThemeBySharedPreferences();
    }

    /**
     * 设置主题
     */
    private void chooseThemeBySharedPreferences(){
        String theme = (String) SharedPreferences.getInstance(this).getValue(Constants.SP_THEME, String.class);
        if (theme == null) {
            theme = Constants.THEME_DAY_SMALL;
            SharedPreferences.getInstance(this).putValue(theme, String.class);
        }
        switch (theme) {
            case Constants.THEME_DAY_BIG:
                setTheme(R.style.App_TextSize_Big_Day);
                break;
            case Constants.THEME_DAY_MIDDLE:
                setTheme(R.style.App_TextSize_Middle_Day);
                break;
            case Constants.THEME_DAY_SMALL:
                setTheme(R.style.App_TextSize_Small_Day);
                break;
            case Constants.THEME_NIGHT_BIG:
                setTheme(R.style.App_TextSize_Big_Night);
                break;
            case Constants.THEME_NIGHT_MIDDLE:
                setTheme(R.style.App_TextSize_Middle_Night);
                break;
            case Constants.THEME_NIGHT_SMALL:
                setTheme(R.style.App_TextSize_Small_Night);
                break;
        }
    }

}
