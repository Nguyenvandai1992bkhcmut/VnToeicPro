package model;

/**
 * Created by dainguyen on 5/26/17.
 */

public class ModelSection {
    int mSectionId;
    String mSectionTitle;

    public ModelSection(int id, String title) {
        this.mSectionId = id;
        this.mSectionTitle = title;
    }


    public String getmSectionTitle() {
        return mSectionTitle;
    }

    public void setmSectionTitle(String mSectionTitle) {
        this.mSectionTitle = mSectionTitle;
    }

    public int getmSectionId() {
        return mSectionId;
    }

    public void setmSectionId(int mSectionId) {
        this.mSectionId = mSectionId;
    }
}
