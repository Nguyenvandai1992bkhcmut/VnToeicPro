package model;

import java.io.Serializable;

/**
 * Created by giang on 6/2/17.
 */

public class ModelPart7Question implements Serializable {
    private int mPart7QuestionId,mPart7Id;
    private String mQuestion, mA, mB, mC, mD;
    private EAnswer mSol;

    public int getPart7QuestionId() {
        return mPart7QuestionId;
    }

    public void setPart7QuestionId(int part7QuestionId) {
        mPart7QuestionId = part7QuestionId;
    }

    public int getPart7Id() {
        return mPart7Id;
    }

    public void setPart7Id(int part7Id) {
        mPart7Id = part7Id;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public String getA() {
        return mA;
    }

    public void setA(String a) {
        mA = a;
    }

    public String getB() {
        return mB;
    }

    public void setB(String b) {
        mB = b;
    }

    public String getC() {
        return mC;
    }

    public void setC(String c) {
        mC = c;
    }

    public String getD() {
        return mD;
    }

    public void setD(String d) {
        mD = d;
    }

    public EAnswer getSol() {
        return mSol;
    }

    public void setSol(EAnswer sol) {
        mSol = sol;
    }
}
