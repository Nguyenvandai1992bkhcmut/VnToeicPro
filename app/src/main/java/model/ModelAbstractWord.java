package model;

import java.io.Serializable;

/**
 * Created by giang on 6/30/17.
 */

public class ModelAbstractWord implements Serializable{
    public static final int ITEM = 0;
    public static final int SECTION = 1;

    public final int mType;
    public final String mText;
    public final ModelWordLesson mWord;
    public int mLessonId;
    public int sectionPosition;
    public int listPosition;

    public ModelAbstractWord(int type, String text, int lessonId, ModelWordLesson word) {
        this.mType = type;
        this.mLessonId = lessonId;
        this.mText = text;
        this.mWord = word;
    }

    public boolean isSection() {
        return mType == SECTION;
    }

    @Override
    public String toString() {
        return mText;
    }
}
