package supportview;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by dainguyen on 7/12/17.
 */

public interface IPartControlListen {

    public void nextQuesiton();

    public void backQuestion();

    public void playAudio();

    public void replayAudio();

    public void pauseAudio();

    public void resumeAudio();

    public void changeQuestion(int index);

    public boolean checkFavorite();

    public void favoriteQuesiton(boolean b);

    public void addResult(ArrayList<String> choose);

    public int getIsSumit();

    public int getIsFinnishAudio();

    public Bundle getBunldeQuestion();

    public Bundle getBunldeSummary();
    public Bundle getBunldeExplan();


}
