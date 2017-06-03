package com.vntoeic.bkteam.vntoeicpro.CustomComponent.CustomCheckBox;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

/**
 * Created by giang on 6/2/17.
 */

public class CustomCheckBox extends RelativeLayout implements Checkable{

    private View mRootView;
    private ImageView mImageView;
    private TextView mTextView;

    private ECheckable mState;
    private boolean isShow;
    private boolean isAnswer;

    private final int RES_CHECKED = R.mipmap.icon_choosen;
    private final int RES_NOTCHECKED = R.mipmap.icon_notChoosen;
    private final int RES_TRUE = R.mipmap.icon_true;
    private final int RES_FALSE = R.mipmap.icon_false;

    public boolean isAnswer() {
        return isAnswer;
    }

    public ECheckable getState() {
        return mState;
    }

    public void setState(ECheckable state) {
        if (isShow && (state == ECheckable.TRUE || state == ECheckable.FALSE))
            setCheck(state);
        else if (!isShow && (state == ECheckable.CHECKED || state == ECheckable.NOT_CHECKED))
            setCheck(state);
        else
            setCheck(ECheckable.NOT_CHECKED);
    }

    public boolean isShow() {
        return isShow;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;

    }

    public CustomCheckBox(Context context) {
        super(context);
        init(context);
    }



    public CustomCheckBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomCheckBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }



    private void init(Context context) {

        mRootView = inflate(context, R.layout.custom_checkbox, this);
        mImageView = (ImageView) mRootView.findViewById(R.id.imageView);
        mTextView = (TextView) mRootView.findViewById(R.id.textView);
        mState = ECheckable.NOT_CHECKED;
        isShow = false;
        isAnswer = false;

        mRootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
    }

    private void onStateChanged() {
        setDrawable(getDrawable());
    }

    public int getDrawable() {

        switch (mState) {
            case CHECKED: {
                return RES_CHECKED;
            }

            case TRUE: {
                return RES_TRUE;
            }
            case FALSE: {
                return RES_FALSE;
            }
            default:
                return RES_NOTCHECKED;

        }
    }

    private void setDrawable(int resId) {
        mImageView.setImageResource(resId);
    }



    @Override
    public void setCheck(ECheckable eCheckable) {
        mState = eCheckable;
        onStateChanged();
    }

    @Override
    public ECheckable getCheck() {
        return mState;
    }

    @Override
    public void toggle() {
        switch (mState){
            case CHECKED:{
                this.mState = ECheckable.NOT_CHECKED;
                break;
            }
            case NOT_CHECKED:{
                this.mState = ECheckable.CHECKED;
                break;
            }
        }
    }

    @Override
    public void setShow(boolean isShow) {
        this.isShow = isShow;
    }
}
