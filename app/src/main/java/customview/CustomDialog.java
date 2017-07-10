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

import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;

import model.ModelWordLesson;

/**
 * Created by giang on 7/5/17.
 */

public class CustomDialog extends Dialog implements View.OnClickListener {
    public static int DURATION = 300;
    private Context mContext;
    private ArrayList<ModelWordLesson> mWordLessons;
    private boolean mCanView;
    private IViewListener mIViewListener;
    private View.OnClickListener mOnClickListener;

    public interface IViewListener {
        public void onView();
    }
    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(Context context, IViewListener viewListener, int themeResId, boolean canView, ArrayList<ModelWordLesson> wordLessons) {
        super(context, themeResId);
        mContext = context;
        mWordLessons = wordLessons;
        mCanView = canView;
        mIViewListener = viewListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View customView = LayoutInflater.from(mContext).inflate(R.layout.dialog_voca_3, null, false);
        TextView learnTxt = (TextView) customView.findViewById(R.id.learn_txt);
        TextView practiceTxt = (TextView) customView.findViewById(R.id.practice_txt);
        TextView viewTxt = (TextView) customView.findViewById(R.id.view_txt);
        if (!mCanView) viewTxt.setVisibility(View.GONE);

        this.setContentView(customView);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        Window window = getWindow();
        params.copyFrom(window.getAttributes());
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);

        learnTxt.setOnClickListener(this);
        practiceTxt.setOnClickListener(this);
        viewTxt.setOnClickListener(this);

        final RelativeLayout relativeLayout = (RelativeLayout) customView.findViewById(R.id.main);
        relativeLayout.setOnClickListener(this);

        setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                ShowAnimation(relativeLayout);
            }
        });

        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    public void CancelAnimation(final View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float screenHeight = displayMetrics.heightPixels;

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 2, 1).setDuration(DURATION),
                ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 2, 1).setDuration(DURATION),
                ObjectAnimator.ofFloat(view, "alpha", 1, 0).setDuration(DURATION * 3/2)
        );
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                cancel();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animatorSet.start();

    }

    public void ShowAnimation(final View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float screenHeight = displayMetrics.heightPixels;

        ValueAnimator positionAnimator = ValueAnimator.ofFloat(-screenHeight/2, 0);
        positionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setTranslationY(value);
            }
        });

        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(screenHeight, "rotation", -60f, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(positionAnimator).with(rotationAnimator);
        animatorSet.setInterpolator(new BounceInterpolator());

        animatorSet.setDuration(500);
        animatorSet.start();

    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.learn_txt: {
//                Intent intent = new Intent(mContext, LearningActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("data", mWordLessons);
//                intent.putExtras(bundle);
//                mContext.startActivity(intent);
//                CustomDialog.this.cancel();
//                break;
//            }
//            case R.id.practice_txt: {
//                Intent intent = new Intent(mContext, PracticeActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("data", mWordLessons);
//                intent.putExtras(bundle);
//                mContext.startActivity(intent);
//                CustomDialog.this.cancel();
//                break;
//            }
//            case R.id.view_txt: {
//                mIViewListener.onView();
//                CustomDialog.this.cancel();
//                break;
//            }
//            case R.id.main: {
//                CancelAnimation(v);
//                break;
//            }
//        }

        mOnClickListener.onClick(v);
    }
}
