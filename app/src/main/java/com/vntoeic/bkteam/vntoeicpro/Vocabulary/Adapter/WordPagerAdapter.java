package com.vntoeic.bkteam.vntoeicpro.Vocabulary.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import model.ModelWord;
import model.ModelWordLesson;

/**
 * Created by giang on 5/30/17.
 */

public class WordPagerAdapter extends FragmentPagerAdapter {

    private ModelWordLesson[] mWords;
    public WordPagerAdapter(FragmentManager fm, ModelWordLesson[] words) {
        super(fm);
        this.mWords = words;
    }

    @Override
    public Fragment getItem(int position) {
        return WordPageFragment.create(mWords[position]);
    }

    @Override
    public int getCount() {
        return mWords.length;
    }
}
