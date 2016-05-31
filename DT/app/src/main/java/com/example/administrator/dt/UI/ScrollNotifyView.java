package com.example.administrator.dt.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;


public class ScrollNotifyView extends ScrollView {

    private OnScrollViewListener mOnScrollViewListener;
    public ScrollNotifyView(Context context) {
        super(context);
    }

    public ScrollNotifyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollNotifyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface OnScrollViewListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    public void setOnScrollViewListener(OnScrollViewListener l) {
        mOnScrollViewListener = l;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mOnScrollViewListener.onScrollChanged(l, t, oldl, oldt);
    }
}
