package sqlite;

;import android.graphics.PorterDuff;

import model.*;

/**
 * Created by dainguyen on 5/26/17.
 */

public class SqliteVocabulary {
    static {
        System.loadLibrary("jnizxy");
    }

    public native ModelSection[] searchAllSection();

    public native ModelTag[] searchTaginSection(int idsection);

    public native ModelLesson[] searchLessonTag(int idwordtag);

    public native ModelWord searchWordId(int id);

    public native ModelWordLesson[] searchWordLesson(int id_lesson);

    public native ModelFavoriteWord[]searchFavoriteWord();

    public native void insertFavoriteWord(ModelFavoriteWord modelFavoriteWord);

    public native ModelWordChecked[]searchWordChecked();

    public native ModelWordChecked[]searchWordCheckedId(int id);

    public native boolean checkFavoriteWord(int id);

    public native boolean checkWordChecked(int id);

    public native void insertWordChecked(ModelWordChecked checked);

    public native void deleteWordFavorite(int id);

    public native void deleteWordChecked(int id);
}
