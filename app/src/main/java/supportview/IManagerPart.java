package supportview;

import android.os.Bundle;

/**
 * Created by dainguyen on 7/4/17.
 */

public interface IManagerPart {
    public Bundle getBunldeSave();

    public int getIsSubmit();

    public void setBundle(Bundle bundle);

    public void timeSubmit();

    public void sendResultToSever(String ...arr);
}
