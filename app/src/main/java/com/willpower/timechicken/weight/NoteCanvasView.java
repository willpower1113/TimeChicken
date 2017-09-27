package com.willpower.timechicken.weight;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

/**
 * Created by Administrator on 2017/9/27.
 */

public class NoteCanvasView extends BaseSurfaceView implements SurfaceHolder.Callback, Runnable {

    private float startX, startY;

    private float pointX = -1;

    private float pointY = -1;

    private boolean isDrawingLine = false;

    public NoteCanvasView(Context context) {
        super(context);
    }

    public NoteCanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoteCanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected Rect getDirty() {
        return new Rect(0, 0, getWidth(), getWidth());
    }

    Path mPath = new Path();

    @Override
    protected void drawView() {
        //清屏~
//        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        mCanvas.drawPath(mPath, paint);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getX();    //获取手指移动的x坐标
        int y = (int) event.getY();    //获取手指移动的y坐标
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
