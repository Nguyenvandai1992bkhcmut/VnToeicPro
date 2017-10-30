package favoritevocabulary;

import model.ModelWord;

/**
 * Created by giang on 10/18/17.
 */
public interface OnItemClick {
    void onItemClick(int oldPos, ModelWord currentWord, int currentLessonId);
    void onCloseFavoriteView(int index);
}
