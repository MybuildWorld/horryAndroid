package com.android.pieview;

import android.graphics.Path;
import android.graphics.PointF;
import android.view.View;

/**
 * Created by XDL on 2016-03-02.
 */
public class PieItem {
    private View mView;
    private int mLevel;
    private int mStart;
    private int mSweep;
    private int mInner;
    private int mOuter;
    private boolean mPressed;
    private Path mPath;
    private PointF mCenter = null;

    public PieItem(View view, int level) {
        mView = view;
        mLevel = level;
    }

    public void setPressed(boolean pressed) {
        mPressed = pressed;
        if (mView != null) {
            mView.setPressed(pressed);
        }
    }

    public boolean isPressed() {
        return mPressed;
    }

    public int getLevel() {
        return mLevel;
    }

    public void setCenter(PointF center){
        mCenter = center;
    }

    public PointF getCenter(){
        return mCenter;
    }

    public void setGeometry(int start, int sweep, int inside, int outside, Path p) {
        mStart = start;
        mSweep = sweep;
        mInner = inside;
        mOuter = outside;
        mPath = p;
    }

    public int getStartAngle() {
        return mStart;
    }

    public int getSweep() {
        return mSweep;
    }

    public int getInnerRadius() {
        return mInner;
    }

    public int getOuterRadius() {
        return mOuter;
    }

    public View getView() {
        return mView;
    }

    public Path getPath() {
        return mPath;
    }

}
