package com.vntoeic.bkteam.vntoeicpro.Reading.Part7;

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

import com.vntoeic.bkteam.vntoeicpro.BaseFragment;
import com.vntoeic.bkteam.vntoeicpro.R;
import com.vntoeic.bkteam.vntoeicpro.Reading.Fragment.PassageFragment;
import com.vntoeic.bkteam.vntoeicpro.Reading.Fragment.QuestionFragment;
import com.vntoeic.bkteam.vntoeicpro.Reading.Fragment.QuestionPagerFragment;

import model.EAnswer;
import model.ModelPart7;
import model.ModelPart7Passage;
import model.ModelPart7Question;

/**
 * Created by giang on 6/2/17.
 */

public class Part7ContentFragment extends BaseFragment {

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

        ModelPart7Question question1 = new ModelPart7Question();
        question1.setA("Job opportunities in sales");
        question1.setB("Changes in marketing regulations");
        question1.setC("Medicine for young people");
        question1.setD("Guidelines for laboratory technicians");
        question1.setQuestion("What is Ms. Wang's role in the conference?");
        question1.setPart7Id(0);
        question1.setPart7QuestionId(1);
        question1.setSol(EAnswer.C);

        ModelPart7Question question2 = new ModelPart7Question();
        question2.setA("Job opportunities in sales");
        question2.setB("Changes in marketing regulations");
        question2.setC("Medicine for young people");
        question2.setD("Guidelines for laboratory technicians");
        question2.setQuestion("What is Ms. Wang's role in the conference?");
        question2.setPart7Id(0);
        question2.setPart7QuestionId(1);
        question2.setSol(EAnswer.C);

        ModelPart7Passage passage1 = new ModelPart7Passage();
        passage1.setContent("anh");
        passage1.setPart7Id(0);
        passage1.setId(1);
        passage1.setToken("7b26375a-3d23-11e7-af78-40167e35abe9");
        passage1.setIsText(0);

        ModelPart7Passage passage2 = new ModelPart7Passage();
        passage2.setContent("We are pleased to announce the opening of two additional Murata stores. Join us on the following dates from 9 AM to 7 PM for special discounts, giveaways, and more. Enter our in-store raffle on opening day for a chance to win a living room set!");
        passage2.setPart7Id(0);
        passage2.setId(2);
        passage2.setToken("7b26375a-3d23-11e7-af78-40167e35abe9");
        passage2.setIsText(1);

        ModelPart7Passage[] passages = {passage1, passage2};


        mQuestions = new ModelPart7Question[]{question1, question2};
        mModelPart7 = new ModelPart7(1, passages);

//        getData();
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
        QuestionPagerFragment questionFragment = QuestionPagerFragment.getmInstance(mQuestions);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameQuestion, questionFragment)
                .addToBackStack(null)
                .commit();
    }

    private void setPassage() {

        PassageFragment passageFragment = PassageFragment.getInstance(mModelPart7);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framePassage, passageFragment)
                .addToBackStack(null)
                .commit();
    }

    private void setTopBar() {

    }

    @Override
    public void setUpContent(int contentLayoutId) {

    }

    @Override
    public void onOpen() {
        topNavi.setVisibility(View.VISIBLE);
        bottomNavi.setVisibility(View.VISIBLE);
        topNavi.onOpen();
        bottomNavi.onOpen();

    }

    @Override
    public void onClose() {

        topNavi.onClose();
        bottomNavi.onClose();
    }
}
