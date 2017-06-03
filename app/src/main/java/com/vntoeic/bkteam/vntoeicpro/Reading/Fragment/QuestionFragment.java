package com.vntoeic.bkteam.vntoeicpro.Reading.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vntoeic.bkteam.vntoeicpro.R;

import model.ModelPart7Question;

/**
 * Created by giang on 6/2/17.
 */

public class QuestionFragment extends Fragment {
    private static final String MODEL_PART7_QUESTION = "model_part7_question";
    private static QuestionFragment mInstance;
    private ModelPart7Question[] mQuestions;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        getData();

        return view;
    }

    private void getData() {
        Bundle bundle = getArguments();
        this.mQuestions = (ModelPart7Question[]) bundle.getSerializable(MODEL_PART7_QUESTION);
    }

    public static QuestionFragment getInstance(ModelPart7Question[] questions) {

        if (mInstance == null) {
            mInstance = new QuestionFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(MODEL_PART7_QUESTION, questions);
        mInstance.setArguments(bundle);
        return mInstance;
    }
}
