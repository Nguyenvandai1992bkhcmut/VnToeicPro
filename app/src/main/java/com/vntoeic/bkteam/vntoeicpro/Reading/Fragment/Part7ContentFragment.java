package com.vntoeic.bkteam.vntoeicpro.Reading.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.vntoeic.bkteam.vntoeicpro.R;

import model.ModelPart7;
import model.ModelPart7Question;

/**
 * Created by giang on 6/2/17.
 */

public class Part7ContentFragment extends Fragment {

    public static final String MODEL_PART7 = "model_part7";
    private static final String MODEL_PART7_QUESTION = "model_part7_question";
    private LinearLayout mTopBar;
    private FrameLayout mPassageLayout, mQuestionLayout;
    private ModelPart7 mModelPart7;
    private ModelPart7Question[] mQuestions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_part7, container, false);
        this.mTopBar = (LinearLayout) view.findViewById(R.id.topBar);
        this.mPassageLayout = (FrameLayout) view.findViewById(R.id.framePassage);
        this.mQuestionLayout = (FrameLayout) view.findViewById(R.id.frameQuestion);

        getData();
        setTopBar();
        setPassage();
        setQuestion();
        return view;
    }

    private void getData() {
        Bundle bundle = getArguments();
        mModelPart7 = (ModelPart7) bundle.getSerializable(MODEL_PART7);
        mQuestions = (ModelPart7Question[]) bundle.getSerializable(MODEL_PART7_QUESTION);
    }

    private void setQuestion() {
        QuestionFragment questionFragment = QuestionFragment.getInstance(mQuestions);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framePassage, questionFragment)
                .addToBackStack(null)
                .commit();
    }

    private void setPassage() {

        PassageFragment passageFragment = PassageFragment.getInstance(mModelPart7);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framePassage, passageFragment)
                .addToBackStack(null)
                .commit();
    }

    private void setTopBar() {

    }
}
