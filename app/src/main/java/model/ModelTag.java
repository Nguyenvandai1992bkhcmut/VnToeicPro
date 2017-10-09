package model;

/**
 * Created by dainguyen on 5/26/17.
 */

public class ModelTag {
    private int mTagId;
    private int mSectionId;
    private String mTagTitle;
    private String mImgToken;

    public ModelTag(int id_tag, int id_section, String title, String token) {
        this.mTagId = id_tag;
        this.mSectionId = id_section;
        this.mTagTitle = title;
        this.mImgToken = token;
    }


    public String getmImgToken() {
        return mImgToken;
    }

    public void setmImgToken(String mImgToken) {
        this.mImgToken = mImgToken;
    }

    public int getmSectionId() {
        return mSectionId;
    }

    public void setmSectionId(int mSectionId) {
        this.mSectionId = mSectionId;
    }

    public int getmTagId() {
        return mTagId;
    }

    public void setmTagId(int mTagId) {
        this.mTagId = mTagId;
    }

    public String getmTagTitle() {
        return mTagTitle;
    }

    public void setmTagTitle(String mTagTitle) {
        this.mTagTitle = mTagTitle;
    }
}
