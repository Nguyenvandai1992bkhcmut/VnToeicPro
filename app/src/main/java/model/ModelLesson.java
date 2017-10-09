package model;

/**
 * Created by dainguyen on 5/26/17.
 */

public class ModelLesson {
    private int mLessonId;
    private int mTagId;
    private String mLessonTitle;

    public ModelLesson(int id_lesson, int id_w_tag, String title) {
        this.mLessonId = id_lesson;
        this.mTagId = id_w_tag;
        this.mLessonTitle = title;
    }


    public int getmLessonId() {
        return mLessonId;
    }

    public void setmLessonId(int mLessonId) {
        this.mLessonId = mLessonId;
    }

    public String getmLessonTitle() {
        return mLessonTitle;
    }

    public void setmLessonTitle(String mLessonTitle) {
        this.mLessonTitle = mLessonTitle;
    }

    public int getmTagId() {
        return mTagId;
    }

    public void setmTagId(int mTagId) {
        this.mTagId = mTagId;
    }
}
