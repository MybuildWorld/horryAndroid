package com.example.horryxiao.circleprogress;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by HorryXiao on 2016/4/4.
 */
public class Circlelayout extends View {
    private static final int mRadius=100;
    public  Point mCenter;
    public Point mleftPoint;
    public Point mrightPoint;
    public int mViewSize=120;
    private Paint mtopPaint;
    private Paint mbottomPaint;
    private Path topPath;
    private Path bottomPath;
    private boolean mAnimation=false;
    private int duration=5000;
    private long starttime;
    private long mplaytime;
    public Circlelayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width =getDefaultSize(mViewSize,widthMeasureSpec);
        int height=getDefaultSize(mViewSize,heightMeasureSpec);
        mViewSize=Math.min(width,height);
        setMeasuredDimension(mViewSize,mViewSize);
    }
    void init(){
        mbottomPaint=new Paint();
        mtopPaint=new Paint();
        topPath=new Path();
        mtopPaint.setColor(Color.WHITE);
        mtopPaint.setAntiAlias(true);
        mbottomPaint.setColor(Color.BLACK);
        mbottomPaint.setAntiAlias(true);
        bottomPath=new Path();
        topPath=new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.save();
        mCenter=new Point(getWidth()/2,getHeight()/2);
        float progress=getAnimationProgress();
        canvas.rotate(360*progress,mCenter.x,mCenter.y);

            mleftPoint = new Point(getWidth() / 2 - mRadius / 2, getHeight() / 2);
            mrightPoint = new Point(getWidth() / 2 + mRadius / 2, getHeight() / 2);
            RectF BigRectf = new RectF(mCenter.x - mRadius, mCenter.y - mRadius, mCenter.x + mRadius, mCenter.y + mRadius);
            RectF leftSmallRectf = new RectF(mleftPoint.x - mRadius / 2, mleftPoint.y - mRadius / 2, mleftPoint.x + mRadius / 2, mleftPoint.y + mRadius / 2);
            RectF rightSmallRectf = new RectF(mrightPoint.x - mRadius / 2, mrightPoint.y - mRadius / 2, mrightPoint.x + mRadius / 2, mrightPoint.y + mRadius / 2);
            topPath.addArc(rightSmallRectf, 0, 180);
            topPath.addArc(BigRectf, 180, 180);
            topPath.addArc(leftSmallRectf, 180, 180);
            topPath.setFillType(Path.FillType.EVEN_ODD);
            bottomPath.addArc(leftSmallRectf, 180, 180);
            bottomPath.addArc(BigRectf, 0, 180);
            bottomPath.addArc(rightSmallRectf, 0, 180);
            bottomPath.setFillType(Path.FillType.EVEN_ODD);
            canvas.drawPath(topPath, mtopPaint);
            canvas.drawPath(bottomPath, mbottomPaint);
            topPath.reset();
            bottomPath.reset();
        canvas.restore();
        if(mAnimation){
            postInvalidate();
        }
    }

    public float getAnimationProgress(){
        mplaytime=AnimationUtils.currentAnimationTimeMillis()-starttime;
        float progress=mplaytime/(float)duration;
        return  4*progress%1f;
    }
    public void startAnimation() {
        mAnimation=true;
        mplaytime%=duration;
        starttime= AnimationUtils.currentAnimationTimeMillis()-mplaytime;
        postInvalidate();
    }

}
