package com.willpower.timechicken.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.willpower.timechicken.utils.ActivityManager;


/**
 * 懒加载Fragment基类
 */
public abstract class BaseFragment extends Fragment implements IView {

    protected final String TAG = getClass().getSimpleName();
    /**
     * 视图是否已经初初始化
     */
    protected boolean isInit = false;
    protected boolean isLoad = false;

    protected Context context;
    public View view;

    /**
     * 视图销毁的时候讲Fragment是否初始化的状态变为false
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInit = false;
        isLoad = false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        if (arguments != null) {
            handlerArguments(arguments);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(setFragmentLayoutId(), container, false);
        context = getContext();
        isInit = true;
        isCanLoadData();
        return view;
    }

    /**
     * 视图是否已经对用户可见，系统的方法，setUserVisibleHint在onCreate之前调用
     * 1.第一次创建Fragment时，加载数据会报空指针异常，所以要加个判断是否已经初始化
     * 2.当Fragment已经初始化，再次可见时会直接调用此方法，此时可以直接加载数据
     * 所以要在两个地方调用加载数据
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }
        if (getUserVisibleHint()) {
            setUpView();
            isInit = false;
            isLoad = true;
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
    }

    /**
     * 设置Fragment布局Id
     */
    protected abstract int setFragmentLayoutId();

    /**
     * 当视图初始化并且对用户可见的时候去真正的加载数据
     */
    protected abstract void setUpView();

    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以调用此方法
     */
    protected void stopLoad() {
    }

    /**
     * 处理从Activity传过来的参数
     *
     * @param arguments
     */
    protected void handlerArguments(Bundle arguments) {
    }


    public void showLoading(@Nullable String message) {
    }

    public void showLoading() {
    }

    public void disLoading() {
    }

    public void showToast(String msg) {
    }

    public void showSnake(String msg) {
    }

    protected void jumpActivity(Intent intent) {
        ActivityManager.getIntence().finishActivity(intent.getComponent().getClassName());//防止多次弹出
        startActivity(intent);
    }

    protected void jumpActivityForResult(Intent intent, int requestCode) {
        ActivityManager.getIntence().finishActivity(intent.getComponent().getClassName());//防止多次弹出
        startActivityForResult(intent, requestCode);
    }
}
