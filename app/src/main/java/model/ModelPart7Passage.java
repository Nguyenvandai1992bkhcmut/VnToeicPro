package model;

import java.io.Serializable;

/**
 * Created by giang on 6/2/17.
 */

public class ModelPart7Passage implements Serializable {
    private int mId;
    private int mPart7Id;
    private int isText;
    private String mToken;
    private String mContent;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getPart7Id() {
        return mPart7Id;
    }

    public void setPart7Id(int part7Id) {
        mPart7Id = part7Id;
    }

    public int getIsText() {
        return isText;
    }

    public void setIsText(int isText) {
        this.isText = isText;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
}
