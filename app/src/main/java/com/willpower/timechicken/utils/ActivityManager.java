package com.willpower.timechicken.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity管理工具
 */

public class ActivityManager {
    private static ActivityManager manager;
    private List<Activity> activityList;

    private ActivityManager() {
        activityList = new ArrayList<>();
    }

    public static ActivityManager getIntence() {
        if (manager == null) {
            manager = new ActivityManager();
        }
        return manager;
    }

    /**
     * 检查activity是否在运行
     *
     * @param activityName
     * @return
     */
    public boolean hasActivity(String activityName) {
        for (Activity activity : activityList
                ) {
            if (activity != null) {
                if (activityName.equals(activity.getClass().getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断mainactivity是否处于栈顶
     *
     * @return true在栈顶false不在栈顶
     */
    public static boolean isTaskTop(Context context, String activityName) {
        android.app.ActivityManager manager = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String name = manager.getRunningTasks(1).get(0).topActivity.getClassName();
        return name.equals(activityName);
    }

    /**
     * 添加Activity到管理工具
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityList != null) {
            activityList.add(activity);
        }
    }

    /**
     * 移除Activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activityList != null && activityList.contains(activity)) {
            activityList.remove(activity);
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (Activity activity : activityList) {
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 结束单个Activity
     */
    public void finishActivity(String activityName) {
        for (Activity activity : activityList) {
            if (activity != null) {
                if (activity.getClass().getName().equals(activityName)) {
                    activity.finish();
                }
            }
        }
    }

    /**
     * 重启程序
     *
     * @param context
     * @param activityClass
     */
    public static void restartApplication(Context context, Class activityClass) {
        getIntence().finishAllActivity();
        Intent intent = new Intent(context, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 判断程序是否在运行
     */
    public static boolean isAppRun(Context context) {
        android.app.ActivityManager am = (android.app.ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<android.app.ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (android.app.ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(context.getPackageName()) || info.baseActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
