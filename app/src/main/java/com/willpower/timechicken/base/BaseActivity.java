package com.willpower.timechicken.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.willpower.timechicken.R;
import com.willpower.timechicken.TCApplication;
import com.willpower.timechicken.utils.ActivityManager;

/**
 * MVP activity基类
 */
public abstract class BaseActivity extends AppCompatActivity implements IView {
    protected final String TAG = getClass().getSimpleName();
    protected Activity context;

    /***********************************************************生命周期*****************************************/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getIntence().removeActivity(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(TCApplication.getInstance.appTheme);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        ActivityManager.getIntence().addActivity(this);
        context = this;
        initView();
        initToolBar();//设置顶部栏
        handlerSavedInstanceState(savedInstanceState);//保存Activity状态
    }

    @CallSuper
    public void handlerSavedInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }
    }

    /***********************************************************生命周期*****************************************/

    /***********************************************************抽象函数*****************************************/
    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void initView();
    /***********************************************************抽象函数*****************************************/

    /* 设置顶部栏 */
    private void initToolBar() {
        setToolbarNavigation();
    }
    /***********************************************************Private函数*****************************************/

    /***********************************************************工具函数封装*****************************************/
    protected void jumpActivity(Intent intent) {
        ActivityManager.getIntence().finishActivity(intent.getComponent().getClassName());//防止多次弹出
        startActivity(intent);
    }

    protected void jumpActivityForResult(Intent intent, int requestCode) {
        ActivityManager.getIntence().finishActivity(intent.getComponent().getClassName());//防止多次弹出
        startActivityForResult(intent, requestCode);
    }

    public void showLoading() {
    }

    public void showLoading(String message) {
    }

    public void disLoading() {
    }

    public void showToast(String msg) {
    }

    public void showSnake(String msg) {
    }

    /***********************************************************工具函数封装*****************************************/

    /***********************************************************顶部栏设置*****************************************/
    /*标题*/
    protected void setToolbarTitle(CharSequence title) {
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        if (toolbarTitle != null) {
            toolbarTitle.setText(title);
        }
    }

    /*菜单按钮*/
    protected void setToolbarMenu(View.OnClickListener listener) {
        ImageButton toolbarMenu = (ImageButton) findViewById(R.id.toolbar_menu);
        if (toolbarMenu != null) {
            toolbarMenu.setVisibility(View.VISIBLE);
            toolbarMenu.setOnClickListener(listener);
        }
    }

    /*自定义按钮*/
    protected void setToolbarAction(CharSequence action, View.OnClickListener listener) {
        TextView toolbarAction = (TextView) findViewById(R.id.toolbar_action);
        if (toolbarAction != null) {
            toolbarAction.setVisibility(View.VISIBLE);
            toolbarAction.setText(action);
            toolbarAction.setOnClickListener(listener);
        }
    }

    /*返回按钮*/
    protected void setToolbarNavigation() {
        ImageButton toolbarBack = (ImageButton) findViewById(R.id.toolbar_back);
        if (toolbarBack != null) {
            toolbarBack.requestFocus();//软键盘不自动弹出
            toolbarBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BaseActivity.this.onBackPressed();
                }
            });
        }
    }
    /*******************************************************顶部栏设置*****************************************/
}