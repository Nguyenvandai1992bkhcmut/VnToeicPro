package com.vntoeic.bkteam.vntoeicpro.Vocabulary.Activity;

import android.support.v4.app.Fragment;

/**
 * Created by giang on 5/29/17.
 */

public class WordLessonPagerFragment  extends Fragment{

    private static WordLessonPagerFragment mInstance;

    public static WordLessonPagerFragment create() {
        if (mInstance == null) {
            mInstance = new WordLessonPagerFragment();
        }

        return mInstance;
    }
}
