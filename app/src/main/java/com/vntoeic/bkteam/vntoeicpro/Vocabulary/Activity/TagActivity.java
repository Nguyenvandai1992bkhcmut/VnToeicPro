package com.vntoeic.bkteam.vntoeicpro.Vocabulary.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.vntoeic.bkteam.vntoeicpro.R;
import com.vntoeic.bkteam.vntoeicpro.Vocabulary.Adapter.TagPagerAdapter;

/**
 * Created by giang on 5/29/17.
 */

public class TagActivity extends AppCompatActivity {

    public static final String TAG_ID = "Tag Id";
    private ViewPager mViewPager;
    private int mTagId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tag);

        setUpToolbar();
        setUpContent();
    }

    private void setUpContent() {
        Intent intent = getIntent();
        mTagId = intent.getExtras().getInt(TAG_ID);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        TagPagerAdapter adapter = new TagPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(LessonPagerFragment.create(mTagId));
        adapter.addFragment(WordLessonPagerFragment.create());

        mViewPager.setAdapter(adapter);
        ViewPagerChangePage listener = new ViewPagerChangePage();
        mViewPager.addOnPageChangeListener(listener);
    }

    private void setUpToolbar() {

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
