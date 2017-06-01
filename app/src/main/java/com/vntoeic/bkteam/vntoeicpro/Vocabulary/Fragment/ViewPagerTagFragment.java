package com.vntoeic.bkteam.vntoeicpro.Vocabulary.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vntoeic.bkteam.vntoeicpro.R;
import com.vntoeic.bkteam.vntoeicpro.Vocabulary.Activity.LessonPagerFragment;
import com.vntoeic.bkteam.vntoeicpro.Vocabulary.Activity.WordLessonPagerFragment;
import com.vntoeic.bkteam.vntoeicpro.Vocabulary.Adapter.TagPagerAdapter;

/**
 * Created by giang on 6/1/17.
 */

public class ViewPagerTagFragment extends Fragment {
    public static final String TAG_ID = "tag_id";
    private int mTagId;
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tag, container, false);
        setUpContent(view);
        setUpToolbar(view);
        return view;
    }

    private void setUpContent(View view) {
        Bundle bundle = getArguments();
        mTagId = bundle.getInt(TAG_ID);

        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);

        TagPagerAdapter adapter = new TagPagerAdapter(getChildFragmentManager());
        adapter.addFragment(LessonPagerFragment.create(mTagId));
        adapter.addFragment(WordLessonPagerFragment.create());

        mViewPager.setAdapter(adapter);
        ViewPagerChangePage listener = new ViewPagerChangePage();
        mViewPager.addOnPageChangeListener(listener);
    }

    private void setUpToolbar(View view) {

    }

    public class ViewPagerChangePage implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
