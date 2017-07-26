package customview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;

import animation.PositionRotationAnimation;
import model.ModelWordLesson;

/**
 * Created by giang on 7/5/17.
 */

public class CustomDialog extends Dialog implements View.OnClickListener{
    public static int DURATION = 300;
    private Context mContext;
    private DialogOnClickListener mOnClickListener;
    private View mPracticing;
    private View mListening;
    private RelativeLayout outSideLayout;
    private View mCardview;
    private View mViewDetail;
    private View mLearning;


    public interface DialogOnClickListener {
        void dialogOnClick(int index);
    }


    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View customView = LayoutInflater.from(mContext).inflate(R.layout.dialog_voca_3, null, false);
        bindView(customView);
        this.setContentView(customView);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        Window window = getWindow();
        params.copyFrom(window.getAttributes());
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);

        final RelativeLayout relativeLayout = (RelativeLayout) customView.findViewById(R.id.main);
        relativeLayout.setOnClickListener(this);

        setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                ShowAnimation();
            }
        });

        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    private void bindView(View view) {
        this.outSideLayout = (RelativeLayout) view.findViewById(R.id.main);
        this.mViewDetail = view.findViewById(R.id.img_viewDetail);
        this.mViewDetail.setOnClickListener(this);
        this.mLearning = view.findViewById(R.id.img_learning);
        this.mLearning.setOnClickListener(this);
        this.mPracticing = view.findViewById(R.id.img_practice);
        this.mPracticing.setOnClickListener(this);
        this.mListening = view.findViewById(R.id.img_listening);
        this.mListening.setOnClickListener(this);
        this.mCardview = view.findViewById(R.id.cardview);
    }


    public void ShowAnimation() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float screenHeight = displayMetrics.heightPixels;

        ValueAnimator positionAnimator = ValueAnimator.ofFloat(-screenHeight/2, 0);
        positionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mCardview.setTranslationY(value);
            }
        });

        ValueAnimator alphaAnimator = ValueAnimator.ofFloat(0, 1);
        alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                outSideLayout.setAlpha(value);
            }
        });

        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(screenHeight, "rotation", -60f, 0);
        rotationAnimator.setTarget(mCardview);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(positionAnimator).with(rotationAnimator).with(alphaAnimator);
        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                PositionRotationAnimation combinedAnimation = new PositionRotationAnimation(mCardview, CustomDialog.this);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animatorSet.setDuration(500);
        animatorSet.start();

    }

    public void setOnClickListener(DialogOnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_viewDetail: {
                mOnClickListener.dialogOnClick(1);
                CustomDialog.this.dismiss();
                break;
            }
            case R.id.img_learning: {
                mOnClickListener.dialogOnClick(2);
                CustomDialog.this.dismiss();
                break;
            }
            case R.id.img_practice: {
                mOnClickListener.dialogOnClick(3);
                CustomDialog.this.dismiss();
                break;
            }

            case R.id.img_listening:{
                mOnClickListener.dialogOnClick(4);
                CustomDialog.this.dismiss();
                break;
            }
            case R.id.main: {
//                CancelAnimation(v);
                break;
            }
        }

        Toast.makeText(mContext, "on click", Toast.LENGTH_SHORT).show();

    }
}
