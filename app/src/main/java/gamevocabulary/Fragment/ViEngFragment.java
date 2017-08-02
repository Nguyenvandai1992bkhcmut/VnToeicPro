package gamevocabulary.Fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import gamevocabulary.Adapter.GameViEngAdapter;
import model.ModelWord;

/**
 * Created by giang on 7/3/17.
 */

public class ViEngFragment extends GameFragment {
    private GameViEngAdapter mAdapter;
    private static final int TIME = 10 * 1000;


    public static ViEngFragment newInstance(ModelWord question, ArrayList<String> answer) {
        ViEngFragment fragment = new ViEngFragment();
        return (ViEngFragment) initArg(question, answer, fragment);
    }

    @Override
    protected void showResult() {

        for (int i = 0; i < mAnswer.size(); i++) {
            mAdapter.mShowBackgroundResults.get(i).onShowBackgroundResult();
        }
    }

    @Override
    protected void prepareAnswer() {
        mQuestionTxt.setText(mQuestion.getmMeanings()[(int) (Math.random() * mQuestion.getmMeanings().length)].toUpperCase());
        mAdapter = new GameViEngAdapter(mContext, this, mQuestion, mAnswer);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected int getTime() {
        return TIME;
    }
}
