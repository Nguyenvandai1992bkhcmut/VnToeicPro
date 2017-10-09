package model;

/**
 * Created by giang on 7/29/17.
 */

public class ModelAbstractFavoriteWord {

    public static final int ITEM = 0;
    public static final int SECTION = 1;

    public final int mType;
    public final ModelColorFavorite mModelColorFavorite;
    public final ModelWordLesson mModelWordLesson;
    public int mSectionPosition;
    public int mListPosition;

    public ModelAbstractFavoriteWord(int type, ModelColorFavorite modelColorFavorite, ModelWordLesson modelWord) {
        this.mType = type;
        this.mModelColorFavorite  = modelColorFavorite;
        this.mModelWordLesson = modelWord;
    }

    public boolean isSection() {
        return mType == SECTION;
    }

}
