package vocabulary.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vntoeic.bkteam.vntoeicpro.R;

import vocabulary.adapter.TagPagerAdapter;

/**
 * Created by giang on 6/27/17.
 */

public class TagVocabularyFragment extends Fragment{

    private static final String TAG_ID = "tag id";
    private int mTagId;
    private ViewPager mViewPager;
    private FragmentManager mFragmentManager ;



    public static TagVocabularyFragment newInstance(int tagId) {
        TagVocabularyFragment fragment = new TagVocabularyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TAG_ID, tagId);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tag, container, false);
        mFragmentManager = getChildFragmentManager();
        setUpContent(view);
        return view;
    }

    private void setUpContent(View view) {
        Bundle bundle = getArguments();
        mTagId = bundle.getInt(TAG_ID);

        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);

        TagPagerAdapter adapter = new TagPagerAdapter(mFragmentManager);
        adapter.addFragment(LessonPagerFragment.create(mTagId));
        adapter.addFragment(WordLessonPagerFragment.create(mTagId));

        mViewPager.setAdapter(adapter);
//        ViewPagerChangePage listener = new ViewPagerChangePage();
//        mViewPager.addOnPageChangeListener(listener);
    }
}
