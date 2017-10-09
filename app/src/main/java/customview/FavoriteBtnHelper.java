package customview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vntoeic.bkteam.vntoeicpro.R;

/**
 * Created by giang on 7/18/17.
 */

public class FavoriteBtnHelper {

    public static int[] mDrawable = {
            R.mipmap.un_favorite,
            R.mipmap.circle_red,
            R.mipmap.circle_orrange,
            R.mipmap.circle_yellow,
            R.mipmap.circle_green,
            R.mipmap.circle_blue,
            R.mipmap.circle_purple,
    };

    public static LinearLayout CreateHorizontalFavorite(Context context){
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setPadding(5, 10, 5, 10);
        View parent = (View) layout.getParent();
        float cellWidth = parent.getWidth() / (float)mDrawable.length;

        for (int i = mDrawable.length - 1; i >= 0; i--) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new LinearLayout.LayoutParams((int) cellWidth, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setImageResource(mDrawable[i]);
            layout.addView(imageView);
        }

        return layout;
    }
}
