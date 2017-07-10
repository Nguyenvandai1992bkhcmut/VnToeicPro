package customview.custom_checkbox;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

/**
 * Created by giang on 6/28/17.
 */

public class CustomCheckBox extends RelativeLayout implements Checkable {

    private View mRootView;
    private ImageView mImageView;
    private TextView mTextView;

    private ECheckable mState;
    private boolean isShow;
    private boolean isAnswer;
    private boolean isSol = false;
    private int mId = 0;
    private CheckBoxController mController = null;

    private final int RES_CHECKED = R.mipmap.icon_checked;
    private final int RES_NOTCHECKED = R.mipmap.icon_notchecked;
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

    public CustomCheckBox(Context context, boolean isSol, int id, CheckBoxController controller) {
        super(context);
        init(context, isSol, id, controller);
        controller.add(this);
    }



    public CustomCheckBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomCheckBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }



    private void init(Context context, boolean isSol, int id, CheckBoxController controller) {

        init(context);
        this.isSol = isSol;
        this.mId = id;
        this.mController = controller;
    }

    public boolean setController(CheckBoxController controller){
        this.mController = controller;
        this.mController.add(this);
        return true;
    }

    private void init(Context context) {
        mRootView = inflate(context, R.layout.custom_checkbox, this);
        mImageView = (ImageView) mRootView.findViewById(R.id.imageView);
        mTextView = (TextView) mRootView.findViewById(R.id.textView);
        setState(ECheckable.NOT_CHECKED);
        isShow = false;
        isAnswer = false;

        mRootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
    }

    public void setText(String s) {
        mTextView.setText(s);
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
//                setState(ECheckable.NOT_CHECKED);
                break;
            }
            case NOT_CHECKED:{
                setState(ECheckable.CHECKED);
                if (mController != null) mController.itemOnChangeState(mId);
                break;
            }
        }
    }

    @Override
    public void setShow(boolean isShow) {
        this.isShow = isShow;
    }
}
