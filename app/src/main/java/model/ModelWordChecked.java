package model;

/**
 * Created by dainguyen on 5/27/17.
 */

public class ModelWordChecked {
    private int mWordId;
    private int mLessonId;
    private int mResult;
    private String mTimeChecked;

    public ModelWordChecked(int mWordId, int mLessonId, int mResult, String mTimeChecked) {
        this.mWordId = mWordId;
        this.mLessonId = mLessonId;
        this.mResult = mResult;
        this.mTimeChecked = mTimeChecked;
    }

    public int getmLessonId() {
        return mLessonId;
    }

    public void setmLessonId(int mLessonId) {
        this.mLessonId = mLessonId;
    }

    public int getmResult() {
        return mResult;
    }

    public void setmResult(int mResult) {
        this.mResult = mResult;
    }

    public String getmTimeChecked() {
        return mTimeChecked;
    }

    public void setmTimeChecked(String mTimeChecked) {
        this.mTimeChecked = mTimeChecked;
    }

    public int getmWordId() {
        return mWordId;
    }

    public void setmWordId(int mWordId) {
        this.mWordId = mWordId;
    }
}
