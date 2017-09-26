package com.willpower.timechicken.base;

/**
 * Created by Administrator on 2017/8/19.
 */

public abstract class BaseViewHolder<T extends BaseActivity> {
    protected T mView;
    public BaseViewHolder(T mView) {
        this.mView = mView;
    }
}
