package gamevocabulary;

import android.support.v4.app.Fragment;

import model.ModelWordGame;

/**
 * Created by giang on 7/3/17.
 */

public interface GameInterface {
    void changeQuestionDelay(final Boolean lastResult, ModelWordGame word);
    void showKeyBoard(Fragment fragment);
}
