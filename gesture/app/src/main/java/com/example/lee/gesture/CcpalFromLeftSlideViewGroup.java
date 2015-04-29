package com.example.lee.gesture;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

public class CcpalFromLeftSlideViewGroup extends ViewGroup {

    private Context mContext;
    private Scroller mScroller;
    private DisplayMetrics metric;
    private VelocityTracker mVelocityTracker = null;

    private static int SNAP_VELOCITY = 600; // the smallest scoll speed
    private int mTouchSlop = 100; // over the distance , we will think it begin to scroll
    private float mLastionMotionX = 0;

    private static final int VIEW_DISAPPER = 0;
    private static final int VIEW_SHOW = 1;
    private int _mShow = VIEW_DISAPPER;

    public CcpalFromLeftSlideViewGroup(Context context) {
        this(context, null, 0);
    }

    public CcpalFromLeftSlideViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CcpalFromLeftSlideViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWorkspace();
    }

    private void initWorkspace() {
        mContext = getContext();
        mScroller = new Scroller(mContext);
        metric = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metric);
    }

    private void bindBackEvent() {
        View child1 = getChildAt(0);
        child1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mScroller != null) {
                    View child1 = getChildAt(0);
                    int dx = child1.getWidth();

                    snapToBack(-dx, dx);
                    dismissMask();
                }
            }
        });
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cnt = this.getChildCount();
        for (int i = 0; i < cnt; i++) {
            View child = getChildAt(i);
            MarginLayoutParams cParams = (MarginLayoutParams) child.getLayoutParams();
            if (child.getVisibility() != View.GONE) {
                child.layout(cParams.leftMargin - getWidth(),
                        0,
                        0,
                        child.getMeasuredHeight());
            }
        }

        bindBackEvent();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        View child = getChildAt(0);
        MarginLayoutParams cParams = (MarginLayoutParams) child.getLayoutParams();

        measureChildren(widthMeasureSpec - cParams.rightMargin, heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int xDiff = (int) Math.abs(mLastionMotionX - ev.getX());
        if (xDiff <= mTouchSlop) {
            return false;
        }

        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        if (_mShow == VIEW_DISAPPER) {
            return false;
        }

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

        float x = event.getX();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastionMotionX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                int detaX = (int) (mLastionMotionX - x);
                if (detaX > 0) {
                    scrollBy(detaX, 0);
                    mLastionMotionX = x;
                }
                break;
            case MotionEvent.ACTION_UP:
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000);
                int velocityX = (int) velocityTracker.getXVelocity();
                if (velocityX < -SNAP_VELOCITY) {
                    snapToBack(getScrollX(), -getScrollX());
                } else {
                    snapToDestination();
                }
                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
        }

        return true;
    }

    private void snapToDestination() {
        int dx = -(getWidth() + getScrollX());

        mScroller.startScroll(getScrollX(), 0, dx, 0, 500);
        invalidate();

        startMask();
    }

    private void snapToBack(int startPos, int dx) {
        mScroller.startScroll(startPos, 0, dx, 0, 500);
        invalidate();

        dismissMask();
    }

    public void startMove() {
        View child1 = getChildAt(0);
        int dx = -child1.getWidth();
        mScroller.startScroll(0, 0, dx, 0, 500);
        invalidate();

        startMask();
    }

    private void startMask() {
        FrameLayout fl = (FrameLayout) this.getParent();
        fl.findViewById(R.id.lyMask).setVisibility(View.VISIBLE);

        _mShow = VIEW_SHOW;
    }

    private void dismissMask() {
        FrameLayout fl = (FrameLayout) this.getParent();
        fl.findViewById(R.id.lyMask).setVisibility(View.GONE);

        _mShow = VIEW_DISAPPER;
    }
}