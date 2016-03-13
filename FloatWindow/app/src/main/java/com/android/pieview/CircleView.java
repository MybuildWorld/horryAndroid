package com.android.pieview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.xdl.floatwindow.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDL on 2016-03-12.
 */
public class CircleView extends FrameLayout{

    public static final int CIRCLE_ANGLE = 360;

    private static final int MAX_LEVEL = 5;
    private static final int DEFAULT_INNER_RADIUS = 110;
    private static final int DEFAULT_RADIUS_INC = 130;

    private Point mCenter;
    private int mInnerRadius;
    private int mRadiusInc;

    private OnItemClickListener mItemClickListener = null;

    private List<List<PieItem>> mItemList;

    private Paint mNormalPaint;
    private Paint mPressedPaint;
    private Paint paintcircle;

    int mItemSize;
    private Context mContext;

    private PieItem mLastTouchItem = null;
    private boolean mIsLayoutItems = false;

    private boolean mIsTouchMove = false;
    public CircleView(Context context) {
        super(context);
        init(context);
        mContext = context;
        mItemSize = (int) context.getResources().getDimension(R.dimen.pie_button_item_size);
    }


    public CircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        mItemClickListener = listener;
    }

    private void init(Context ctx) {
        mItemList = new ArrayList<List<PieItem>>();
        Resources res = ctx.getResources();

        mInnerRadius = DEFAULT_INNER_RADIUS;
        mRadiusInc = DEFAULT_RADIUS_INC;
        setWillNotDraw(false);
        setDrawingCacheEnabled(false);
        mCenter = new Point(0, 0);
        mNormalPaint = new Paint();
        mNormalPaint.setColor(res.getColor(R.color.pie_button_bg));
        mNormalPaint.setAntiAlias(true);
        mPressedPaint = new Paint();
        mPressedPaint.setColor(res.getColor(R.color.pie_button_pressed));
        mPressedPaint.setAntiAlias(true);
        paintcircle=new Paint();
        paintcircle.setColor(res.getColor(R.color.circleColor));
        paintcircle.setAntiAlias(true);
    }

    public void addItem(PieItem item) {
        int level = item.getLevel();
        if (level >= MAX_LEVEL) {
            level = MAX_LEVEL - 1;
        }
        while (mItemList.size() <= level) {
            mItemList.add(new ArrayList<PieItem>());
        }
        mItemList.get(level).add(item);

    }

    public void setCenter(Point center) {
        mCenter = center;
    }

    public void setCenter(int x, int y) {
        if (mCenter == null) {
            mCenter = new Point(x, y);
        } else {
            mCenter.x = x;
            mCenter.y = y;
        }
    }

    private double angle2arc(int angle) {
        return (angle * Math.PI / 180);
    }

    private int arc2angle(double arc) {
        Log.i("arc to angle", "arc = " + arc);
        return (int) (arc * 180 / Math.PI);
    }

    /**
     * @param startAngle
     * @param endAngle
     * @param inner
     * @param outer
     * @param center
     * @return
     */
    private Path makePath(int startAngle, int endAngle, int inner, int outer, Point center) {
        Log.i("makePath", "start, end = " + startAngle + ", " + endAngle);
        Path path = new Path();
        RectF innerRect = new RectF(center.x - inner, center.y - inner,
                center.x + inner, center.y + inner);
        RectF outerRect = new RectF(center.x - outer, center.y - outer,
                center.x + outer, center.y + outer);
        path.arcTo(innerRect, startAngle, endAngle - startAngle, true);
        path.arcTo(outerRect, endAngle, startAngle - endAngle, false);
        path.close();
        return path;
    }

    /**
     *
     */
    private void layoutItems() {
        int intervalAngle = 0;
        int inner = mInnerRadius;
        int outer = mInnerRadius + mRadiusInc;
        int incInterval = 2;
        int startAngleInterval = 45;
        int levelStartAngle = 45;
        for (List<PieItem> list : mItemList) {
            int itemAngle = CIRCLE_ANGLE / list.size() - intervalAngle;
            int startAngle = levelStartAngle;
            Log.i("layout", "list.size = " + list.size());
            for (PieItem item : list) {
                View view = item.getView();
                view.measure(view.getLayoutParams().width,
                        view.getLayoutParams().height);
                int w = view.getMeasuredWidth();
                int h = view.getMeasuredHeight();
                //show in center
                int r = inner + (outer - inner) / 2;
                double arc = angle2arc(startAngle + itemAngle / 2 + intervalAngle / 2);
                int x = mCenter.x + (int) (r * Math.cos(arc)) - w / 2;
                int y = mCenter.y + (int) (r * Math.sin(arc)) - h / 2;
                view.layout(x, y, x + w, y + h);
                Path path = makePath(startAngle, startAngle + itemAngle, inner, outer, mCenter);
                item.setGeometry(startAngle, itemAngle, inner, outer, path);
                startAngle += itemAngle + intervalAngle;
            }
            inner += mRadiusInc + incInterval;
            outer += mRadiusInc + incInterval;
            levelStartAngle += startAngleInterval;
        }
    }

    private PieItem findItem(float x, float y) {
        // find the matching item:
        Log.i("findItem", "x, y = " + x + " ," + y);
        x -= mCenter.x;
        y -= mCenter.y;
        int r = (int) Math.sqrt(x * x + y * y);
        int angle = arc2angle(Math.atan2(y, x));
        angle %= 360;
        if (angle < 0) {
            angle += 360;
        }
        Log.i("findItem", "r, angle = " + r + " ," + angle);
        for (List<PieItem> list : mItemList) {
            for (PieItem item : list) {
                if (isPointInItem(r, angle, item)) {
                    return item;
                }
            }
        }
        return null;
    }

    /**
     * is point with radius r and angle angle in item
     *
     * @param r     radius
     * @param angle angle witch is belong [0, 360)
     * @param item
     * @return yes point in the item, otherwise false
     */
    private boolean isPointInItem(int r, int angle, PieItem item) {
        if (item.getInnerRadius() <= r && item.getOuterRadius() >= r) {
            while (angle + CIRCLE_ANGLE <= item.getStartAngle() + item.getSweep()) {
                angle += CIRCLE_ANGLE;
            }
            if (item.getStartAngle() <= angle && item.getStartAngle() + item.getSweep() >= angle) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private int mLastTouchX;
    private int mLastTouchY;

    @Override
    public boolean onTouchEvent(MotionEvent evt) {
        int x = (int) evt.getX();
        int y = (int) evt.getY();
        int action = evt.getActionMasked();
        if (MotionEvent.ACTION_DOWN == action) {
            mLastTouchX = x;
            mLastTouchY = y;
            mIsTouchMove = false;
            mLastTouchItem = findItem(x, y);
            if (mLastTouchItem != null) {
                Log.i("touch", "item != null startAngle = " + mLastTouchItem.getStartAngle());
                mLastTouchItem.setPressed(true);
                invalidate();
            } else {
                Log.i("touch", "item == null");
            }
            return true;
        } else if (MotionEvent.ACTION_UP == action) {
            Log.i("touch", "up");
            if (mLastTouchItem != null) {
                mLastTouchItem.setPressed(false);
                invalidate();
            }
            if(!mIsTouchMove){
                if(mItemClickListener != null && mLastTouchItem != null){
                    mItemClickListener.onItemClick(mLastTouchItem);
                }
            }
        } else if (MotionEvent.ACTION_CANCEL == action) {
//        	mIsTouchMove = true;
        } else if (MotionEvent.ACTION_MOVE == action) {
            if(x != mLastTouchX || y != mLastTouchY){
                mIsTouchMove = true;
            }
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawCircle(mCenter.x,mCenter.y,110,paintcircle);
        if(!mIsLayoutItems){
            layoutItems();
            mIsLayoutItems = true;
        }
        int state;
        for (List<PieItem> list : mItemList) {
            for (PieItem item : list) {
                Paint p = item.isPressed() ? mPressedPaint : mNormalPaint;
                state = canvas.save();
                drawPath(canvas, item.getPath(), p);
                canvas.restoreToCount(state);
                drawItem(canvas, item);
            }
        }
    }

    @SuppressLint("NewApi")
    private void drawItem(Canvas canvas, PieItem item) {
        View view = item.getView();
        int state = canvas.save();
        canvas.translate(view.getX(), view.getY());
        view.draw(canvas);
        canvas.restoreToCount(state);
        state = canvas.save();
        canvas.restoreToCount(state);
    }

    private void drawPath(Canvas canvas, Path path, Paint paint) {
        canvas.drawPath(path, paint);
    }

    public PieItem makeItem(int image, int level) {
        ImageView view = new ImageView(mContext);
        view.setImageResource(image);
        view.setMinimumWidth(mItemSize);
        view.setMinimumHeight(mItemSize);
        view.setScaleType(ImageView.ScaleType.CENTER);
        LayoutParams lp = new LayoutParams(mItemSize, mItemSize);
        view.setLayoutParams(lp);
        return new PieItem(view, level);
    }

    public static interface OnItemClickListener{
        public void onItemClick(PieItem item);
    }

}
