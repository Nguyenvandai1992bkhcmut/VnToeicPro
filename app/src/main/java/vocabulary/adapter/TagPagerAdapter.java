package vocabulary.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import vocabulary.fragment.LessonPagerFragment;

/**
 * Created by giang on 6/27/17.
 */

public class TagPagerAdapter extends FragmentPagerAdapter {
    private final LessonPagerFragment.LessonInterface mListener;
    private ArrayList<Fragment> mFragments = new ArrayList<>();


    public TagPagerAdapter(FragmentManager fm, LessonPagerFragment.LessonInterface listener) {
        super(fm);
        this.mListener = listener;
    }

    public void addFragment(Fragment fragment) {
        this.mFragments.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public void changeToWordLessonFragment(int adapterPosition) {
        mListener.changeToWordLessonFragment(adapterPosition);
    }
}
