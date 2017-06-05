package com.vntoeic.bkteam.vntoeicpro.Reading.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vntoeic.bkteam.vntoeicpro.R;
import com.vntoeic.bkteam.vntoeicpro.Vocabulary.Activity.LessonPagerFragment;

import model.ModelPart7Question;

/**
 * Created by giang on 6/4/17.
 */

public class QuestionPagerFragment extends Fragment {

    private static final String LIST_QUESTION = "list_question";
    private static QuestionPagerFragment mInstance;
    private Context mContext;

    public static QuestionPagerFragment getmInstance(ModelPart7Question[] questions) {

        if (mInstance == null) {
            mInstance = new QuestionPagerFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(LIST_QUESTION, questions);
        mInstance.setArguments(bundle);

        return mInstance;
    }

    @Override
    public void onAttach(Context context) {
        this.mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_viewpager, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        ModelPart7Question[] questions = (ModelPart7Question[]) getArguments().getSerializable(LIST_QUESTION);
        QuestionPagerAdapter adapter = new QuestionPagerAdapter(getChildFragmentManager(), mContext, questions);
        viewPager.setAdapter(adapter);
        return view;
    }

    class QuestionPagerAdapter extends FragmentPagerAdapter{

        private final ModelPart7Question[] mQuestions;
        private final Context mContext;

        public QuestionPagerAdapter(FragmentManager fm, Context context, ModelPart7Question[] questions) {
            super(fm);
            this.mContext = context;
            this.mQuestions = questions;
        }

        @Override
        public Fragment getItem(int position) {
            return QuestionFragment.getInstance(mQuestions[position]);
//            return new QuestionFragment();
        }

        @Override
        public int getCount() {
            return mQuestions.length;
        }
    }
}
