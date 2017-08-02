package model;

import java.io.Serializable;

import vocabulary.ColorConstant;

/**
 * Created by giang on 7/29/17.
 */

public class ModelColorFavorite implements Serializable{

    /**
     * mIndex must be one of color indexs defined in ColorConstant
     */
    public int mIndex;

    public ModelColorFavorite(int index) {
        this.mIndex = index;
    }

    @Override
    public String toString() {
        return ColorConstant.getColorName(mIndex);
    }

    public int getDrawable() {
        return ColorConstant.colorArr[mIndex];
    }
}
