package model;
import java.io.Serializable;

public class ModelWordLesson implements Serializable{
    private ModelWord mWord;
    private int mLessonId;

    public ModelWordLesson(int mLessonId, ModelWord mWord) {
        this.mLessonId = mLessonId;
        this.mWord = mWord;
    }

    public ModelWordLesson(int mLessonId, int mId, String mWord, String mPronounce, String mExamples, String[] mMeanings, String[] mTypes, String[] mExplanations, String[] mSimilars) {
        this.mLessonId = mLessonId;
        ModelWord mword = new ModelWord(mId,mWord,mPronounce,mExamples,mMeanings,mTypes,mExplanations,mSimilars);
        this.mWord = mword;
    }

    public int getmLessonId() {
        return mLessonId;
    }

    public void setmLessonId(int mLessonId) {
        this.mLessonId = mLessonId;
    }

    public ModelWord getmWord() {
        return mWord;
    }

    public void setmWord(ModelWord mWord) {
        this.mWord = mWord;
    }
}
