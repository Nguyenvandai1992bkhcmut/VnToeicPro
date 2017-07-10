package vocabulary.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import model.ModelWord;
import model.ModelWordLesson;
import vocabulary.fragment.WordDetailFragment;

/**
 * Created by giang on 7/2/17.
 */

public class WordPagerAdapter extends FragmentPagerAdapter {
    private int mPos;
    private ModelWord[] mWords;
    
    public WordPagerAdapter(FragmentManager fm, ModelWordLesson[] wordLessons, int curPos){
        super(fm);
        ModelWord[] words = new ModelWord[wordLessons.length];
        for (int i = 0; i < wordLessons.length; i++) {
            words[i] = wordLessons[i].getmWord();
        }
        this.mWords = words;
        this.mPos = curPos;
    }
    public WordPagerAdapter(FragmentManager fm, ModelWord[] words) {
        super(fm);
        this.mWords = words;
    }

    @Override
    public Fragment getItem(int position) {
        return WordDetailFragment.newInstance(mWords[(position + mPos) % mWords.length]);
    }

    @Override
    public int getCount() {
        return mWords.length;
    }
}
