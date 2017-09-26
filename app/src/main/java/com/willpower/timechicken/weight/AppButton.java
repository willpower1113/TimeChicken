package com.willpower.timechicken.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.willpower.timechicken.R;

import static android.view.Gravity.CENTER;

/**
 * Created by Administrator on 2017/8/23.
 */

public class AppButton extends AppCompatButton implements GestureDetector.OnGestureListener {
    //-------------------------------------------constant--------------------------------------//
    /**
     * click
     */
    private static final int DEFAULT_SELECT_COLOR = Color.LTGRAY;
    private static final int DEFAULT_NORMAL_COLOR = Color.TRANSPARENT;
    private static final int DEFAULT_COVER_COLOR = Color.parseColor("#60FFFFFF");
    //clickModel
    private static final int CLICK_MODEL_CHANGE = 1;
    private static final int CLICK_MODEL_COVER = 2;
    /**
     * Shadow
     */
    private static final int DEFAULT_SHADOW_COLOR = Color.DKGRAY;
    private static final float DEFAULT_SHADOW_RADIOS = 3;
    private static final boolean DEFAULT_NO_SHADOW = false;
    private static final float DEFAULT_SHADOW_OFFSET_X = 0;
    private static final float DEFAULT_SHADOW_OFFSET_Y = 0;
    /**
     * Ripple
     */
    private static final long DEFAULT_RIPPLE_DURATION = 500;
    private static final int DEFAULT_RIPPLE_COLOR = Color.parseColor("#ffffff");
    private static final int DEFAULT_RIPPLE_ALPHA = 50;
    private static final boolean DEFAULT_NO_RIPPLE = false;
    private static final int POSITION_DOWN = 1;
    private static final int POSITION_UP = 2;
    /**
     * Stroke
     */
    private static final float DEFAULT_STROKE_WIDTH = 0;
    private static final int DEFAULT_STROKE_COLOR = Color.parseColor("#ffffff");
    /**
     * Fillet
     */
    private static final float DEFAULT_FILLET_RADIO_X = 0;
    private static final float DEFAULT_FILLET_RADIO_Y = 0;
    //-----------------------------------------------------variable-------------------------------------------
    /**
     * click
     */
    private int selectColor, normalColor, coverColor, clickModel;
    private boolean isTouching;
    /**
     * shadow
     */
    private int shadowColor;
    private float shadowRadios;
    private boolean appNoShadow;
    private float shadowOffsetX, shadowOffsetY;
    /**
     * Ripple
     */
    private long rippleDuration;
    private int rippleColor, rippleAlpha;
    private boolean appNoRipple;
    private int ripplePosition;
    private boolean isDrawingRipple;
    //inside
    private float rippleRadios;
    private float rippleX;
    private float rippleY;
    /**
     * Stroke
     */
    private float strokeWidth;
    private int strokeColor;
    /**
     * Fillet(圆角)
     */
    private float filletX;
    private float filletY;

    /**
     * 监听手势
     */
    private GestureDetector mGestureDetector;

    /**
     * params
     */
    private int viewWidth;
    private int viewHeight;


    public AppButton(Context context) {
        super(context);
    }

    public AppButton(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init(context, attrs);
        mGestureDetector = new GestureDetector(context, this);
    }

    public AppButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.AppButton);
        selectColor = ta.getColor(R.styleable.AppButton_selectColor, DEFAULT_SELECT_COLOR);
        normalColor = ta.getColor(R.styleable.AppButton_normalColor, DEFAULT_NORMAL_COLOR);
        coverColor = ta.getColor(R.styleable.AppButton_coverColor, DEFAULT_COVER_COLOR);
        clickModel = ta.getInt(R.styleable.AppButton_clickModel, CLICK_MODEL_COVER);
        shadowColor = ta.getColor(R.styleable.AppButton_appShadowColor, DEFAULT_SHADOW_COLOR);
        shadowRadios = ta.getFloat(R.styleable.AppButton_appShadowRadios, DEFAULT_SHADOW_RADIOS);
        appNoShadow = ta.getBoolean(R.styleable.AppButton_appNoShadow, DEFAULT_NO_SHADOW);
        shadowOffsetX = ta.getFloat(R.styleable.AppButton_appShadowOffsetX, DEFAULT_SHADOW_OFFSET_X);
        shadowOffsetY = ta.getFloat(R.styleable.AppButton_appShadowOffsetY, DEFAULT_SHADOW_OFFSET_Y);
        rippleDuration = DEFAULT_RIPPLE_DURATION;
        rippleColor = ta.getColor(R.styleable.AppButton_appRippleColor, DEFAULT_RIPPLE_COLOR);
        rippleAlpha = ta.getInt(R.styleable.AppButton_appRippleAlpha, DEFAULT_RIPPLE_ALPHA);
        appNoRipple = ta.getBoolean(R.styleable.AppButton_appNoRipple, DEFAULT_NO_RIPPLE);
        ripplePosition = ta.getInt(R.styleable.AppButton_ripplePosition, POSITION_DOWN);
        strokeWidth = ta.getFloat(R.styleable.AppButton_appStrokeWidth, DEFAULT_STROKE_WIDTH);
        strokeColor = ta.getColor(R.styleable.AppButton_appStrokeColor, DEFAULT_STROKE_COLOR);
        filletX = ta.getFloat(R.styleable.AppButton_filletX, DEFAULT_FILLET_RADIO_X);
        filletY = ta.getFloat(R.styleable.AppButton_filletY, DEFAULT_FILLET_RADIO_Y);
        ta.recycle();
        setBackgroundColor(Color.TRANSPARENT);
        setGravity(CENTER);
        isTouching = false;
        isDrawingRipple = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        viewWidth = getWidth();
        viewHeight = getHeight();
        RectF rectF = new RectF(shadowRadios, shadowRadios, viewWidth - shadowRadios, viewHeight - shadowRadios);
        if (strokeWidth > 0) {
            //绘制主体和边框
            drawStrokeAndFill(canvas, rectF);
        } else {
            //绘制主体
            drawFill(canvas, rectF);
        }
        super.onDraw(canvas);
        //绘制覆盖层
        drawCover(canvas, rectF);
        //绘制波纹
        drawRippleWithAnim(canvas);
    }

    //********************************************************TouchEvent********************************************//
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_CANCEL || event.getActionMasked() == MotionEvent.ACTION_UP) {
            setTouching(false);
        }
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        setTouching(true);
        if (!appNoRipple) {
            rippleX = e.getX();
            rippleY = e.getY();
        }
        if (!appNoRipple && ripplePosition == POSITION_DOWN) {
            startRippleAnim();
        }
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // Auto-generated method stub
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (!appNoRipple && ripplePosition == POSITION_UP) {
            startRippleAnim();
        }
        setTouching(false);
        performClick();
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        // Auto-generated method stub
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        //长安时，手动触发长安事件
        performLongClick();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // Auto-generated method stub
        return false;
    }
    //********************************************************TouchEvent********************************************//

    //********************************************************draw********************************************//
    //绘制主体和边框
    private void drawStrokeAndFill(Canvas canvas, RectF rectF) {
        drawFill(canvas, rectF);
        Paint stroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        stroke.setStyle(Paint.Style.STROKE);
        stroke.setStrokeWidth(strokeWidth);
        stroke.setColor(strokeColor);
        if (!appNoShadow) {
            setLayerType(LAYER_TYPE_SOFTWARE, null);
            stroke.setShadowLayer(shadowRadios, shadowOffsetX, shadowOffsetY, shadowColor);
        }
        canvas.drawRoundRect(rectF, filletX, filletY, stroke);
    }

    //绘制主体
    private void drawFill(Canvas canvas, RectF rectF) {
        Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
        fill.setStyle(Paint.Style.FILL);
        setPaintColor(fill);
        if (!appNoShadow) {
            setLayerType(LAYER_TYPE_SOFTWARE, null);
            fill.setShadowLayer(shadowRadios, shadowOffsetX, shadowOffsetY, shadowColor);
        }
        canvas.drawRoundRect(rectF, filletX, filletY, fill);
    }

    //绘制覆盖层
    private void drawCover(Canvas canvas, RectF rectF) {
        if (clickModel != CLICK_MODEL_COVER || !isTouching) return;
        Paint cover = new Paint(Paint.ANTI_ALIAS_FLAG);
        cover.setStyle(Paint.Style.FILL);
        cover.setColor(coverColor);
        canvas.drawRoundRect(rectF, filletX, filletY, cover);
    }

    //绘制波纹
    private void drawRippleWithAnim(Canvas canvas) {
        if (isDrawingRipple) {
            RectF rectRect = new RectF(rippleX - rippleRadios, rippleY - rippleRadios, rippleX + rippleRadios, rippleY + rippleRadios);
            Paint ripplePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            ripplePaint.setColor(rippleColor);
            ripplePaint.setAlpha(rippleAlpha);
            canvas.drawOval(rectRect, ripplePaint);
        }
    }
    /*
   设置画笔颜色
    */
    void setPaintColor(Paint paint) {
        if (clickModel == CLICK_MODEL_CHANGE) {
            if (isTouching) {
                paint.setColor(selectColor);
            } else {
                paint.setColor(normalColor);
            }
        } else {
            paint.setColor(normalColor);
        }
    }

    //********************************************************draw********************************************//

    /**
     * 波纹动画
     */
    private void startRippleAnim() {
        isDrawingRipple = true;//开始绘制波纹的标识
        RippleAnimtorUtils.rippleAnim(viewWidth, rippleX, rippleDuration, new RippleAnimtorUtils.OnRippleAnimListener() {
            @Override
            public void onAnimUpdate(float value, float progress) {
                rippleRadios = value;
                postInvalidate();
            }

            @Override
            public void onAnimFinish() {
                isDrawingRipple = false;
                postInvalidate();
            }
        });
    }

    private void setTouching(boolean isTouching) {
        this.isTouching = isTouching;
        postInvalidate();
    }
}
