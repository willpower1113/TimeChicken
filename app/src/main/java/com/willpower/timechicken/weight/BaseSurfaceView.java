package com.willpower.timechicken.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by Administrator on 2017/9/11.
 * 常用SurfaceView模板
 */

public abstract class BaseSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable, View.OnTouchListener {
    protected final String TAG = getClass().getSimpleName();
    protected SurfaceHolder mHolder;
    //用于绘图的canvas
    protected Canvas mCanvas;
    //子线程标志位
    protected boolean isDrawingNow;

    public BaseSurfaceView(Context context) {
        super(context);
        initSurfaceView();
    }

    public BaseSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        initSurfaceView();
    }

    public BaseSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSurfaceView();
    }

    private void initSurfaceView() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        //设置背景透明
        setZOrderOnTop(true);
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
        //----
        setFocusable(true);
        setKeepScreenOn(true);
        setOnTouchListener(this);
        setFocusableInTouchMode(true);
    }

    /**
     * 设置画布大小
     */
    protected abstract Rect getDirty();

    /**
     * 绘制方法
     */
    protected abstract void drawView();

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDrawingNow = true;
        //开启绘制线程
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDrawingNow = false;
    }

    @Override
    public void run() {
        while (isDrawingNow) {
            try {
                mCanvas = mHolder.lockCanvas(getDirty());
                //draw something
                drawView();
            } catch (Exception e) {
                //绘制异常
                e.printStackTrace();
            } finally {
                if (null != mCanvas) {
                    mHolder.unlockCanvasAndPost(mCanvas);//提交内容
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
