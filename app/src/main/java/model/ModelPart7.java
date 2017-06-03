package model;

import java.io.Serializable;

/**
 * Created by giang on 6/2/17.
 */

public class ModelPart7 implements Serializable {

    private int mId;
    private String mExplanation;
    private int mLevel;
    private ModelPart7Passage[] mPassages;

    public ModelPart7(int id, ModelPart7Passage[] passages) {
        this.setId(id);
        this.setPassages(passages);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getExplanation() {
        return mExplanation;
    }

    public void setExplanation(String explanation) {
        mExplanation = explanation;
    }

    public int getLevel() {
        return mLevel;
    }

    public void setLevel(int level) {
        mLevel = level;
    }

    public ModelPart7Passage[] getPassages() {
        return mPassages;
    }

    public void setPassages(ModelPart7Passage[] passages) {
        mPassages = passages;
    }
}
