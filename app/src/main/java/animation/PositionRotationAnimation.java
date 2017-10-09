package animation;

import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.view.MotionEvent;
import android.view.View;

import customview.CustomDialog;

/**
 * Created by giang on 7/20/17.
 */

public class PositionRotationAnimation {

    private static final float INITTIAL_ROTATION = 0f;
    private CustomDialog mDialog;
    private View mAnimatedView;
    private SpringAnimation mRotationAnimation, mXAnimation, mYAnimation;

    public PositionRotationAnimation(View animateView, CustomDialog customDialog) {
        if (animateView != null) {
            mRotationAnimation = SpringAnimationUtils.createSpringAnimation(animateView,
                    SpringAnimation.ROTATION, INITTIAL_ROTATION,
                    SpringForce.STIFFNESS_MEDIUM, SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
            this.mAnimatedView = animateView;
            this.mDialog = customDialog;
            mXView = mAnimatedView.getX();
            mYView = mAnimatedView.getY();
            this.mAnimatedView.setOnTouchListener(mTouchListener);
        }

    }

    private float mXView, mYView;
    private float mDx, mDy;


    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        public float currentRotation, previousRotation;
        public double centerX, centerY;
        public float x, y;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            centerX = v.getWidth() / 2.0;
            centerY = v.getHeight() / 2.0;
            x = event.getX();
            y = event.getY();

            switch (event.getActionMasked()){
                case MotionEvent.ACTION_DOWN:
                    mDx = v.getX() - event.getRawX();
                    mDy = v.getY() - event.getRawY();
                    // cancel animations
                    if (mXAnimation != null) mXAnimation.cancel();
                    if (mYAnimation != null) mYAnimation.cancel();
                    mRotationAnimation.cancel();
                    updateRotation(v);

                    break;
                case MotionEvent.ACTION_MOVE:
                    previousRotation = currentRotation;
                    updateRotation(v);

                    float angle = currentRotation - previousRotation;
                    v.setRotation(v.getRotation() + angle);
                    mAnimatedView.animate()
                            .x(event.getRawX() + mDx)
                            .y(event.getRawY() + mDy)
                            .setDuration(0)
                            .start();
                    break;
                case MotionEvent.ACTION_UP:
                    if (mAnimatedView.getY() - mYView >= Math.abs(mYView)) {
                        mXAnimation = SpringAnimationUtils.createSpringAnimation(mAnimatedView, SpringAnimation.X, 2 * Math.abs(mYView) * mAnimatedView.getX() / mAnimatedView.getY(),
                                SpringForce.STIFFNESS_MEDIUM, SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
                        mYAnimation = SpringAnimationUtils.createSpringAnimation(mAnimatedView, SpringAnimation.Y, 3 * mYView,
                                SpringForce.STIFFNESS_MEDIUM, SpringForce.DAMPING_RATIO_HIGH_BOUNCY);

                        mYAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                            @Override
                            public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                                mDialog.cancel();
                            }
                        });

                        mXAnimation.start();
                        mYAnimation.start();
                    } else {
                        mXAnimation = SpringAnimationUtils.createSpringAnimation(mAnimatedView, SpringAnimation.X, mXView,
                                SpringForce.STIFFNESS_MEDIUM, SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
                        mYAnimation = SpringAnimationUtils.createSpringAnimation(mAnimatedView, SpringAnimation.Y, mYView,
                                SpringForce.STIFFNESS_MEDIUM, SpringForce.DAMPING_RATIO_HIGH_BOUNCY);

                        mXAnimation.start();
                        mYAnimation.start();
                        mRotationAnimation.start();
                    }


                    break;
            }
            return true;
        }
        private void updateRotation(View view) {
            currentRotation = (float) (view.getRotation()
                    + Math.toDegrees(Math.atan2(x - centerX, centerY - y)));
        }
    };

}

