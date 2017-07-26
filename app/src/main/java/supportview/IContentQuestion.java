package supportview;

import android.os.Bundle;

/**
 * Created by dainguyen on 7/10/17.
 */

public interface IContentQuestion {

    public void nextContent();

    public void backContent();

    public void addChoose(int position,String choose);

    public Bundle getBunldeQuestion(int position);

}
