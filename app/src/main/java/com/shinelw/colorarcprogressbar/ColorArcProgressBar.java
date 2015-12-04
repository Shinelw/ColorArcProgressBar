package com.shinelw.colorarcprogressbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

/**
 * colorful arc progress bar
 * Created by shinelw on 12/4/15.
 */
public class ColorArcProgressBar extends View{

    private int mWidth;
    private int mHeight;
    //直径
    private int diameter = 450;
    private RectF bgRect;
    //圆心
    private float centerX;
    private float centerY;
    private Paint allArcPaint;
    private Paint progressPaint;
    private Paint vTextPaint;
    private Paint hintPaint;
    private Paint degreePaint;
    private float startAngle = 135;
    private float sweepAngle = 270;
    private float currentAngle = 0;
    private float lastAngle;
    private int[] colors = {Color.GREEN, Color.YELLOW, Color.RED, Color.RED};
    private float maxValues;
    private float curValues;
    private int bgArcWidth = 10;
    private int progressWidth = 5;
    private int textSize = 80;
    private int hintSize = 40;
    private int aniSpeed = 1000;
    private ValueAnimator progressAnimator;

    // sweepAngle / maxValues 的值
    private float k;

    public ColorArcProgressBar(Context context) {
        super(context);
        initView();
    }

    public ColorArcProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ColorArcProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.EXACTLY || widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthMeasureSpec;
        }else {
            mWidth = 0;
        }

        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            mHeight = dipToPx(200);
        }else {
            mHeight = heightMeasureSpec;
        }

        setMeasuredDimension(70 + diameter, 70 + diameter);
    }

    private void initView() {

        //弧形的矩阵区域
        bgRect = new RectF(35, 35, diameter + 35, diameter + 35);

        //圆心
        centerX = (diameter + 70)/2;
        centerY = (diameter + 70)/2;

        //外部刻度线
        degreePaint = new Paint();
        degreePaint.setColor(Color.WHITE);

        //整个弧形
        allArcPaint = new Paint();
        allArcPaint.setAntiAlias(true);
        allArcPaint.setStyle(Paint.Style.STROKE);
        allArcPaint.setStrokeWidth(bgArcWidth);
        allArcPaint.setColor(Color.BLACK);
        allArcPaint.setStrokeCap(Paint.Cap.ROUND);

        //当前进度的弧形
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setStrokeWidth(progressWidth);
        progressPaint.setColor(Color.GREEN);

        //当前速度显示文字
        vTextPaint = new Paint();
        vTextPaint.setTextSize(textSize);
        vTextPaint.setColor(Color.WHITE);
        vTextPaint.setTextAlign(Paint.Align.CENTER);

        //显示“km/h”文字
        hintPaint = new Paint();
        hintPaint.setTextSize(hintSize);
        hintPaint.setColor(Color.WHITE);
        hintPaint.setTextAlign(Paint.Align.CENTER);


    }

    @Override
    protected void onDraw(Canvas canvas) {

        //画刻度线
        for (int i = 0; i < 40; i++) {
            if (i > 15 && i < 25) {
                canvas.rotate(9, centerX, centerY);
                continue;
            }
            if (i%5 == 0) {
                degreePaint.setStrokeWidth(5);
                canvas.drawLine(centerX, centerY - diameter/2 - 13, centerX, centerY - diameter/2 - 30, degreePaint);
            }else {
                degreePaint.setStrokeWidth(1);
                canvas.drawLine(centerX, centerY - diameter/2 - 13, centerX, centerY - diameter/2 - 20, degreePaint);
            }

            canvas.rotate(9, centerX, centerY);
        }

        //整个弧
        canvas.drawArc(bgRect,startAngle, sweepAngle, false, allArcPaint);

        //设置渐变色
        SweepGradient sweepGradient = new SweepGradient(centerX, centerY, colors, null);
        Matrix matrix = new Matrix();
        matrix.setRotate(130, centerX, centerY);
        sweepGradient.setLocalMatrix(matrix);
        progressPaint.setShader(sweepGradient);

        //当前进度
        canvas.drawArc(bgRect, startAngle, currentAngle, false, progressPaint);
        canvas.drawText(String.valueOf(curValues), centerX, centerY, vTextPaint);
        canvas.drawText("Km/h",centerX, centerY +(int)vTextPaint.getTextSize(), hintPaint);

        invalidate();

    }

    /**
     * 设置最大值
     * @param maxValues
     */
    public void setMaxValues(float maxValues) {
        this.maxValues = maxValues;
        k = sweepAngle/maxValues;
    }

    /**
     * 设置当前值
     * @param currentValues
     */
    public void setCurrentValues(float currentValues) {
        if (currentValues > maxValues) {
            currentValues = maxValues;
        }
        this.curValues = currentValues;
        lastAngle = currentAngle;
        setAnimation(lastAngle, currentValues * k);
    }

    /**
     * 设置整个圆弧宽度
     * @param bgArcWidth
     */
    public void setBgArcWidth(int bgArcWidth) {
        this.bgArcWidth = bgArcWidth;
    }

    /**
     * 设置进度宽度
     * @param progressWidth
     */
    public void setProgressWidth(int progressWidth) {
        this.progressWidth = progressWidth;
    }

    /**
     * 设置速度文字大小
     * @param textSize
     */
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    /**
     * 设置单位文字大小
     * @param hintSize
     */
    public void setHintSize(int hintSize) {
        this.hintSize = hintSize;
    }

    /**
     * 设置直径大小
     * @param diameter
     */
    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public void setAniSpeed() {

    }
    /**
     * 为进度设置动画
     * @param last
     * @param current
     */
    private void setAnimation(float last, float current) {
        progressAnimator = ValueAnimator.ofFloat(last, current);
        progressAnimator.setDuration(aniSpeed);
        progressAnimator.setTarget(currentAngle);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentAngle= (float) animation.getAnimatedValue();
            }
        });
        progressAnimator.start();
    }


    /**
     * dip 转换成px
     * @param dip
     * @return
     */
    private int dipToPx(int dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int)(dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }
}
