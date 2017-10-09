package vocabulary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vntoeic.bkteam.vntoeicpro.R;

import model.ModelLesson;
import model.ModelStatableLesson;
import sqlite.SqliteVocabulary;
import vocabulary.adapter.LessonAdapter;
import vocabulary.adapter.TagPagerAdapter;

/**
 * Created by giang on 6/27/17.
 */

public class LessonPagerFragment extends Fragment {
    private static final String TAG_ID = "Tag Id";
    private int mTagId;
    private SqliteVocabulary mSqliteVocabulary = new SqliteVocabulary();
    private ModelLesson[] mLessons;
    private static LessonPagerFragment mInstance;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private TagPagerAdapter mPagerAdapter;
    private boolean mSingleMode = true;
    public LessonAdapter mAdapter;

    public static LessonPagerFragment create(int tagId) {
        if (mInstance == null) {
            mInstance = new LessonPagerFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putInt(TAG_ID, tagId);
        mInstance.setArguments(bundle);

        return mInstance;
    }

    public void setSingleMode(boolean singleMode) {
        mSingleMode = singleMode;
        mAdapter.changeMode(mSingleMode);
    }

    public interface LessonInterface{
        void changeToWordLessonFragment(int position);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext  = context;
    }

    @Override
    public void onResume() {
        mAdapter.notifyDataSetChanged();

        super.onResume();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewPager viewPager = (ViewPager) container;
        mPagerAdapter = (TagPagerAdapter) viewPager.getAdapter();
        mTagId = getArguments().getInt(TAG_ID);

        View root = inflater.inflate(R.layout.fragment_lesson_page, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mLessons = mSqliteVocabulary.searchLessonTag(mTagId);
        ModelStatableLesson[] statableLessons = new ModelStatableLesson[mLessons.length];
        for (int i = 0; i < statableLessons.length; i++){
            statableLessons[i] = new ModelStatableLesson(mLessons[i]);
        }
        mAdapter = new LessonAdapter(mContext, statableLessons, mPagerAdapter);
        mRecyclerView.setAdapter(mAdapter);

        setUpContent();
        return root;
    }

    private void setUpContent() {

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
