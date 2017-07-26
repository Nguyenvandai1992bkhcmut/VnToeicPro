package vocabulary;

import android.view.View;

import com.vntoeic.bkteam.vntoeicpro.R;

/**
 * Created by giang on 7/20/17.
 */

public class ColorConstant {
    public static final int RED = 0;
    public static final int ORANGE = 1;
    public static final int YELLOW = 2;
    public static final int GREEN = 3;
    public static final int BLUE = 4;
    public static final int PURPLE = 5;
    public static final int NONE = 6;


    public static final int[] colorArr = {
            R.color.circle_red,
            R.color.circle_orange,
            R.color.circle_yellow,
            R.color.circle_green,
            R.color.circle_blue,
            R.color.circle_purple,
            R.color.transparent_1
    };

    public static final int[] colorCircle = {
            R.mipmap.circle_red,
            R.mipmap.circle_orrange,
            R.mipmap.circle_yellow,
            R.mipmap.circle_green,
            R.mipmap.circle_blue,
            R.mipmap.circle_purple,
            R.mipmap.un_favorite
    };

    public static int getColorIndex(int color){
        switch (color){
            case R.color.circle_red: return RED;
            case R.color.circle_orange: return ORANGE;
            case R.color.circle_yellow: return YELLOW;
            case R.color.circle_green: return GREEN;
            case R.color.circle_blue: return BLUE;
            case R.color.circle_purple: return PURPLE;
            case R.color.transparent_1: return NONE;
        }
        return -1;
    }

    public static int getColorIndex(View view) {
        switch (view.getId()) {
            case R.id.btn_red: return RED;
            case R.id.btn_orrange: return ORANGE;
            case R.id.btn_yellow: return YELLOW;
            case R.id.btn_green: return GREEN;
            case R.id.btn_blue: return BLUE;
            case R.id.btn_purple: return PURPLE;
            case R.id.btn_un_favorite: return NONE;

        }
        return -1;
    }
}
