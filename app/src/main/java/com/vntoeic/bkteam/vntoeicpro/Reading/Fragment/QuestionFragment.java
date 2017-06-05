package com.vntoeic.bkteam.vntoeicpro.Reading.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.CustomComponent.CustomCheckBox.CheckBoxController;
import com.vntoeic.bkteam.vntoeicpro.CustomComponent.CustomCheckBox.CustomCheckBox;
import com.vntoeic.bkteam.vntoeicpro.R;

import model.EAnswer;
import model.ModelPart7Question;

/**
 * Created by giang on 6/2/17.
 */

public class QuestionFragment extends Fragment {
    private static final String MODEL_PART7_QUESTION = "model_part7_question";
    private static QuestionFragment mInstance;
    private ModelPart7Question mQuestion;


    private TextView mQuestionTxt;
    private CustomCheckBox mA, mB, mC, mD;
    private Context mContext;
    private CheckBoxController mController;


    @Override
    public void onAttach(Context context) {
        this.mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        getData();
        bindView(view);
        return view;
    }

    private void bindView(View view) {
        mController = new CheckBoxController();
        mQuestionTxt = (TextView) view.findViewById(R.id.textView);
        mQuestionTxt.setText(mQuestion.getQuestion());
        LinearLayout questionLayout = (LinearLayout) view.findViewById(R.id.question_layout);
        mA = initQuestion(mQuestion.getA(), mQuestion.getSol() == EAnswer.A, 0, questionLayout);
        mB = initQuestion(mQuestion.getB(), mQuestion.getSol() == EAnswer.B, 1, questionLayout);
        mC = initQuestion(mQuestion.getC(), mQuestion.getSol() == EAnswer.C, 2, questionLayout);
        mD = initQuestion(mQuestion.getD(), mQuestion.getSol() == EAnswer.D, 3, questionLayout);
    }

    private CustomCheckBox initQuestion(String s, boolean isSol, int id, LinearLayout questionLayout) {
        CustomCheckBox checkBox = new CustomCheckBox(mContext, isSol, id, mController);
        checkBox.setText(s);
        questionLayout.addView(checkBox, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return checkBox;
    }

    private void getData() {
        Bundle bundle = getArguments();
        mQuestion = (ModelPart7Question) bundle.getSerializable(MODEL_PART7_QUESTION);
    }

    public static QuestionFragment getInstance(ModelPart7Question question) {

        if (mInstance == null) {

        }

        mInstance = new QuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(MODEL_PART7_QUESTION, question);
        mInstance.setArguments(bundle);
        return mInstance;
    }
}
