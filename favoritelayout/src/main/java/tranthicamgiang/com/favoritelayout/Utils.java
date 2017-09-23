package tranthicamgiang.com.favoritelayout;

import android.content.res.Resources;

/**
 * Created by giang on 9/23/17.
 */

public class Utils {
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
