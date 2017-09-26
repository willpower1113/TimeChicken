package com.willpower.timechicken.base;

import android.view.View;

/**
 * Created by Administrator on 2017/8/19.
 */

public abstract class BaseFragmentViewHolder<T extends BaseFragment> {
    protected T mView;

    public BaseFragmentViewHolder(T mView, View view) {
        this.mView = mView;
    }
}
