package tranthicamgiang.com.foldingeffect;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by giang on 9/10/17.
 */

public class FoldingViewGroup extends FrameLayout implements FoldingLayout.FoldListener{

    private float mStartX, mStartY, mMoveX, mMoveY;
    private FoldingLayout mFoldingView;
    private View mTranslationView;
    private boolean isNotStartScroll = false;
    private GestureDetectorCompat mGestureDetector;
    private float mTranslation = -1f;
    private float mFactor = 1f;
    private int mTouchSlop;
    private boolean isNewGesture = false;

    public FoldingViewGroup(Context context) {
        this(context, null);
    }

    public FoldingViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mGestureDetector = new GestureDetectorCompat(context, new ScrollGestureDetecture());
    }


    public float getFoldFactor() {
        return mFactor;
    }

    public void closeNav() {

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, -mFoldingView.getWidth());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mTranslation = (float) valueAnimator.getAnimatedValue();
                mFactor = Math.abs(mTranslation) / mFoldingView.getWidth();
                mFoldingView.setFoldFactor(mFactor);
            }
        });

        valueAnimator.setDuration(300);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();
    }

    public void openNav() {

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(-mFoldingView.getWidth(), 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mTranslation = (float) valueAnimator.getAnimatedValue();
                mFactor = Math.abs(mTranslation) / mFoldingView.getWidth();
                mFoldingView.setFoldFactor(mFactor);
            }
        });

        valueAnimator.setDuration(300);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();
    }

    @Override
    public void onFold(int currentWidth) {
        mTranslationView.setTranslationX(currentWidth);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mStartX = ev.getX();
            mStartY = ev.getY();
            isNewGesture = true;
        }

        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            mMoveX = ev.getX();
            mMoveY = ev.getY();
            if (Math.abs(mStartX - mMoveX) >= Math.abs(mStartY - mMoveY)){
                isNotStartScroll = true;
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event) || foldBack(event);
    }

    private boolean foldBack(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) Log.d("Fling", "action up");
        if (event.getAction() == MotionEvent.ACTION_UP){

            isNotStartScroll = false;
            if (mFoldingView.getCurrentWidth() <= (float)mFoldingView.getWidth() / 2.0f) {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mFoldingView.getCurrentWidth());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        mFactor = Math.abs((float)mTranslation/(float)mFoldingView.getWidth());
                        float value = (float) valueAnimator.getAnimatedValue();
                        mTranslation -= value;
                        if (mTranslation < -mFoldingView.getWidth()) mTranslation = -mFoldingView.getWidth();

                        mFoldingView.setFoldFactor(mFactor);
                    }
                });

                valueAnimator.setInterpolator(new DecelerateInterpolator());
                valueAnimator.setDuration(1000);
                valueAnimator.start();
            } else {

                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,mFoldingView.getWidth() - mFoldingView.getCurrentWidth());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        mFactor = Math.abs((float)mTranslation/(float)mFoldingView.getWidth());
                        float value = (float) valueAnimator.getAnimatedValue();
                        mTranslation += value;
                        if (mTranslation > 0) mTranslation = 0;

                        mFoldingView.setFoldFactor(mFactor);
                    }
                });

                valueAnimator.setInterpolator(new DecelerateInterpolator());
                valueAnimator.setDuration(1000);
                valueAnimator.start();
            }
            return true;
        }
        return false;
    }

    class ScrollGestureDetecture extends GestureDetector.SimpleOnGestureListener {


        @Override
        public boolean onDown(MotionEvent e) {
            mStartX = e.getX();
            mStartY = e.getY();
            isNotStartScroll = true;
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            if (mTranslation == -1f) mTranslation = mFoldingView.getCurrentWidth() - mFoldingView.getWidth();

            float dX = distanceX;
            if (isNewGesture) {
                dX = mStartX - e2.getX();
            }

            isNewGesture = false;

            Log.d("distanceX", dX + "");

            mFactor = Math.abs((float)mTranslation/(float)mFoldingView.getWidth());
            mTranslation -= dX;
            int touchSlop = dX < 0? -mTouchSlop : mTouchSlop;
            mTranslation = isNotStartScroll? mTranslation + touchSlop : mTranslation;

            if (mTranslation < -mFoldingView.getWidth()) mTranslation =  -mFoldingView.getWidth();
            if (mTranslation > 0) mTranslation = 0;
            isNotStartScroll = false;

            mFoldingView.setFoldFactor(mFactor);

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            isNotStartScroll = false;
            if (velocityX < 0) {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mFoldingView.getCurrentWidth());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        mFactor = Math.abs((float)mTranslation/(float)mFoldingView.getWidth());
                        float value = (float) valueAnimator.getAnimatedValue();
                        mTranslation -= value;
                        if (mTranslation < -mFoldingView.getWidth()) mTranslation = -mFoldingView.getWidth();

                        mFoldingView.setFoldFactor(mFactor);
                    }
                });

                valueAnimator.setInterpolator(new DecelerateInterpolator());
                valueAnimator.setDuration(1000);
                valueAnimator.start();
            } else {

                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,mFoldingView.getWidth() - mFoldingView.getCurrentWidth());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        mFactor = Math.abs((float)mTranslation/(float)mFoldingView.getWidth());
                        float value = (float) valueAnimator.getAnimatedValue();
                        mTranslation += value;
                        if (mTranslation > 0) mTranslation = 0;

                        mFoldingView.setFoldFactor(mFactor);
                    }
                });

                valueAnimator.setInterpolator(new DecelerateInterpolator());
                valueAnimator.setDuration(1000);
                valueAnimator.start();
            }
            return true;
        }
    }

    public void setView(View foldingView, View translationView) {
        this.mFoldingView = (FoldingLayout) foldingView;
        mFactor = mFoldingView.getFoldFactor();
        this.mTranslationView = translationView;
    }
}
