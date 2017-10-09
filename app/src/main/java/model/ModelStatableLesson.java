package model;

import java.io.Serializable;

/**
 * Created by giang on 6/29/17.
 */

public class ModelStatableLesson implements Serializable {
    public static final int STATE_CHECKED = 1;
    public static final int STATE_NOT_CHECK = 0;
    public int mState;
    public ModelLesson mLesson;

    public ModelStatableLesson(ModelLesson lesson) {
        this.mState = STATE_NOT_CHECK;
        this.mLesson = lesson;
    }

    public boolean isChecked() {
        return mState == STATE_CHECKED;
    }

    public void setCheck(boolean isCheck) {
        mState = isCheck? STATE_CHECKED : STATE_NOT_CHECK;
    }
}
