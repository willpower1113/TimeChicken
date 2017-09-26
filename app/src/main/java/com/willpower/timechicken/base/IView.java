package com.willpower.timechicken.base;


/**
 * Created by Administrator on 2017/7/29.
 */

public interface IView {
    /**
     * 常用Ui提示
     */
    void showLoading();

    void disLoading();

    void showLoading(String message);

    void showToast(String msg);

    void showSnake(String msg);
}
