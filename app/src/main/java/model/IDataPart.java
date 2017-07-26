package model;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by dainguyen on 7/4/17.
 */

public interface IDataPart {

    public String getExplan();

    public ModelWord[] getListWord();

    public String getContent();
    public int getId();

    public View getViewContent(Context context);

}
