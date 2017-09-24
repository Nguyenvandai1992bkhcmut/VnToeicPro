package model;

/**
 * Created by dainguyen on 5/26/17.
 */

public class ModelWord {
    private int mId;
<<<<<<< HEAD
=======
    private String mtoken;
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
    private String mWord;
    private String mPronounce;
    private String mExamples;
    private String []mMeanings;
    private String []mTypes;
    private String []mExplanations;
    private String []mSimilars;

<<<<<<< HEAD
    public ModelWord(int mId, String mWord, String mPronounce, String mExamples, String[] mMeanings, String[] mTypes, String[] mExplanations, String[] mSimilars) {
        this.mId = mId;
=======
    public ModelWord(int mId, String token,String mWord, String mPronounce, String mExamples, String[] mMeanings, String[] mTypes, String[] mExplanations, String[] mSimilars) {
        this.mId = mId;
        this.mtoken= token;
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
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
<<<<<<< HEAD
=======

    public String getToken() {
        return mtoken;
    }

    public void setToken(String token) {
        this.mtoken = token;
    }
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
}
