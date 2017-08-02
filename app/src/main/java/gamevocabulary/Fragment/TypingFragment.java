package gamevocabulary.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import gamevocabulary.Adapter.GameTypingAdapter;
import model.ModelWord;
import model.ModelWordGame;

/**
 * Created by giang on 7/11/17.
 */

public class TypingFragment extends GameFragment {
    public static final int TIME = 10 * 1000;
    private ArrayList<OnShowBackgroundResult> mShowBackgroundResults = new ArrayList<>();
    private int mIntoCell = 0;

    public static TypingFragment newInstance(ModelWord question) {
        TypingFragment fragment = new TypingFragment();
        return (TypingFragment) initArg(question, null, fragment);
    }

    @Override
    public void showResult() {

        mAnswerTxt.setText(mQuestion.getmWord());
        mAnswerTxt.setVisibility(View.VISIBLE);

        for (int i = 0; i < mQuestion.getmWord().length(); i++) {
            mShowBackgroundResults.get(i).onShowBackgroundResult();
        }

        mListener.changeQuestionDelay(checkResult(), new ModelWordGame(mQuestion));
    }


    public boolean checkResult() {
        for (int i = 0; i < mShowBackgroundResults.size(); i++) {
            GameTypingAdapter.GameTypingItemViewHolder holder = (GameTypingAdapter.GameTypingItemViewHolder) mShowBackgroundResults.get(i);
            if (!holder.isTrue) return false;
        }
        return true;
    }

    @Override
    protected void prepareAnswer() {

        mQuestionTxt.setText(mQuestion.getmMeanings()[(int) (Math.random() * mQuestion.getmMeanings().length)]);
        String word = mQuestion.getmWord();
        mRecyclerView.setVisibility(View.GONE);

        while (true) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            RecyclerView recyclerView = new RecyclerView(mContext);
            recyclerView.setLayoutManager(linearLayoutManager);

            DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
            int pxWidth = displayMetrics.widthPixels;
            int margin = (int) (pxWidth * 0.08);

            int dpWidth = Math.round(pxWidth / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
            int nCell = dpWidth / 52 - 1;

            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) mRecyclerView.getLayoutParams();
            marginLayoutParams.setMargins(margin, 2, margin, 0);
            recyclerView.setLayoutParams(marginLayoutParams);

            if (word.length() > nCell) {
                String subWord = word.substring(0, nCell);
                word = word.substring(nCell, word.length());

                GameTypingAdapter adapter = new GameTypingAdapter(mContext, this, subWord, mShowBackgroundResults);

                recyclerView.setAdapter(adapter);
                mRecyclerLayout.addView(recyclerView);

            } else {

                GameTypingAdapter adapter = new GameTypingAdapter(mContext, this, word, mShowBackgroundResults);

                recyclerView.setAdapter(adapter);
                mRecyclerLayout.addView(recyclerView);
                break;
            }
        }
    }

    @Override
    protected int getTime() {
        return TIME;
    }

    public void addTextIntoCell(String s) {

        if (s.equals("")) {
            if (mIntoCell == 0);
            else {
                mIntoCell--;
                GameTypingAdapter.GameTypingItemViewHolder holder = (GameTypingAdapter.GameTypingItemViewHolder) mShowBackgroundResults.get(mIntoCell);
                holder.mTextView.setText(s);
                holder.setResult(s.equals(mQuestion.getmWord().charAt(mIntoCell) + ""));
            }
        } else if (mIntoCell < mQuestion.getmWord().length()){
            GameTypingAdapter.GameTypingItemViewHolder holder = (GameTypingAdapter.GameTypingItemViewHolder) mShowBackgroundResults.get(mIntoCell);
            holder.mTextView.setText(s);
            holder.setResult(s.equals(mQuestion.getmWord().charAt(mIntoCell) + ""));
            mIntoCell++;
        }
    }
}
