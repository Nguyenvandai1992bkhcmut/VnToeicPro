package favoritevocabulary;

import model.ModelAbstractWord;
import model.ModelFavoriteWord;

/**
 * Created by giang on 10/18/17.
 */

public interface AdapterDataModel {
    void add(int lessonTag, int wordId);
    ModelFavoriteWord remove(int lessonTag, int wordId);
    void prepareList();
}
