package sqlite;

import model.ModelColorFavorite;
import model.ModelWord;
import model.ModelWordLesson;

/**
 * Created by giang on 7/29/17.
 */

public class SqliteFavoriteVocabulary {

    public ModelColorFavorite[] searchAllFavoriteTable() {
        return null;
    }

    /**
     *
     * @param colorIndex mIndex of ModelColorFavorite
     * @return All words are marked in that favorite table
     */
    public ModelWordLesson[] searchAllWordInFavoriteTable(int colorIndex) {
        return null;
    }


    /**
     *
     * @param wordId mWordId of ModelWord
     * @param colorIndex mIndex of ModelColorFavorite
     * @return
     *      if Already in a favorite table, return that favorite table's index (definded in ColorConstant)
     *      else return -1;
     */
    public int checkIfAlreadyInFavorite(int wordId, int colorIndex) {
        return -1;
    }

    public int checkIfAlreadyInFavorite(int wordId){
        return -1;
    }


    public void removeFromFavorite(int wordId, int colorIndex) {

    }

    public void insertIntoFavorite(int wordId, int colorIndex) {

    }
}
