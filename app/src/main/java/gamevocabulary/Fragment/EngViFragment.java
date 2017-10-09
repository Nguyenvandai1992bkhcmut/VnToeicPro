package gamevocabulary.Fragment;

import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import gamevocabulary.Adapter.GameEngViAdapter;
import model.ModelWord;

/**
 * Created by giang on 7/3/17.
 */

public class EngViFragment extends GameFragment {

    public static final int TIME = 10 * 1000;
    private GameEngViAdapter mAdapter;

    public static EngViFragment newInstance(ModelWord question, ArrayList<String> answer) {
        EngViFragment fragment = new EngViFragment();
        return (EngViFragment) initArg(question, answer, fragment);
    }

    @Override
    protected void showResult() {
        for (int i = 0; i < mAnswer.size(); i++) {
            mAdapter.mShowBackgroundResults.get(i).onShowBackgroundResult();
        }

    }

    @Override
    protected void prepareAnswer() {
        mQuestionTxt.setText(mQuestion.getmWord().toUpperCase());

        mAdapter = new GameEngViAdapter(mContext, this, mQuestion, mAnswer);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected int getTime() {
        return TIME;
    }
}
