package model;

import java.io.Serializable;

/**
 * Created by giang on 7/4/17.
 */

public class ModelMeaning implements Serializable{
    public int mWordId;
    public String mMeaning, mType, mExplanation, mSimilar;

    public ModelMeaning(int wordId, String meaning, String type, String explanation, String similar) {
        this.mMeaning = meaning;
        this.mType = type;
        this.mWordId = wordId;
        this.mExplanation = explanation;
        this.mSimilar = similar;
    }

    public int getWordId() {
        return mWordId;
    }

    public String getMeaning() {
        return mMeaning;
    }

    public String getType() {
        return mType;
    }

    public String getExplanation() {
        return mExplanation;
    }

    public String getSimilar() {
        return mSimilar;
    }
}
