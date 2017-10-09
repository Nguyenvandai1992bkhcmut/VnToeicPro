package customview.statable_viewgroup;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.vntoeic.bkteam.vntoeicpro.R;

/**
 * Created by giang on 6/28/17.
 */

public class StatableViewGroup extends RelativeLayout implements View.OnClickListener{

    public static final int STATE_CHECKED = 1;
    public static final int STATE_NOT_CHECK = 0;

    public boolean isEnable;

    public interface OnStateChange {
        void onStateChanged(int state);
        void onEnableChanged(boolean enable);
    }

    public int mCurrentState;
    private Context mContext;
    private OnStateChange mListener;

    public StatableViewGroup(Context context) {
        this(context, null);
    }

    public boolean setListener(OnStateChange onStateChange) {
        mListener = onStateChange;
        mListener.onStateChanged(mCurrentState);
        return true;
    }

    public StatableViewGroup(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatableViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StatableViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        isEnable = false;

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StatableViewGroup, 0, 0);
        this.mCurrentState = typedArray.getBoolean(R.styleable.StatableViewGroup_state_pressed, false)? STATE_CHECKED : STATE_NOT_CHECK;
        typedArray.recycle();
    }

    public void setEnable(boolean enable) {
        this.isEnable = enable;
        if (mListener != null) {
            mListener.onEnableChanged(isEnable);
        }
    }

    @Override
    public void onClick(View v) {
        if (isEnable) {
            mCurrentState = mCurrentState == STATE_NOT_CHECK? STATE_CHECKED : STATE_NOT_CHECK;
            if (mListener != null) mListener.onStateChanged(mCurrentState);
        }

    }

    public boolean isCheck() {
        return isEnable && mCurrentState == STATE_CHECKED;
    }

    public void setStateChecked(boolean isCheck) {
        if (isEnable && isCheck) mCurrentState = STATE_CHECKED;
        else mCurrentState = STATE_NOT_CHECK;
    }
}
