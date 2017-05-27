package model;

/**
 * Created by dainguyen on 5/27/17.
 */

public class ModelFavoriteWord {
    private int mWordId;
    private String mDate;

    public ModelFavoriteWord(int mWordId, String mDate) {
        this.mWordId = mWordId;
        this.mDate = mDate;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public int getmWordId() {
        return mWordId;
    }

    public void setmWordId(int mWordId) {
        this.mWordId = mWordId;
    }
}
