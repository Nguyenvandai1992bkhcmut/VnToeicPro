package model;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by dainguyen on 5/26/17.
 */

public class ModelWord implements Serializable{
    private int mId;
    private String mWord;
    private String mPronounce;
    private String mExamples;
    private String []mMeanings;
    private String []mTypes;
    private String []mExplanations;
    private String []mSimilars;

    public ModelWord(int mId, String mWord, String mPronounce, String mExamples, String[] mMeanings, String[] mTypes, String[] mExplanations, String[] mSimilars) {
        this.mId = mId;
        this.mWord = mWord;
        this.mPronounce = mPronounce;
        this.mExamples = mExamples;
        this.mMeanings = mMeanings;
        this.mTypes = mTypes;
        this.mExplanations = mExplanations;
        this.mSimilars = mSimilars;
    }

    public String getmExamples() {
        return mExamples;
    }

    public void setmExamples(String mExamples) {
        this.mExamples = mExamples;
    }

    public String getmWord() {
        return mWord;
    }

    public void setmWord(String mWord) {
        this.mWord = mWord;
    }

    public String[] getmTypes() {
        return mTypes;
    }

    public void setmTypes(String[] mTypes) {
        this.mTypes = mTypes;
    }

    public String[] getmMeanings() {
        return mMeanings;
    }

    public void setmMeanings(String[] mMeanings) {
        this.mMeanings = mMeanings;
    }

    public String getmPronounce() {
        return mPronounce;
    }

    public void setmPronounce(String mPronounce) {
        this.mPronounce = mPronounce;
    }

    public String[] getmSimilars() {
        return mSimilars;
    }

    public void setmSimilars(String[] mSimilars) {
        this.mSimilars = mSimilars;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String[] getmExplanations() {
        return mExplanations;
    }

    public void setmExplanations(String[] mExplanations) {
        this.mExplanations = mExplanations;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ModelWord &&
                ((ModelWord) obj).getmWord().equals(mWord)
                && ((ModelWord) obj).getmId() == mId;
    }
}
