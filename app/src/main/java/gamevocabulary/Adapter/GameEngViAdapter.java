package gamevocabulary.Adapter;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;

import gamevocabulary.Fragment.EngViFragment;
import gamevocabulary.Fragment.GameFragment;
import gamevocabulary.GameInterface;
import model.ModelWord;
import model.ModelWordGame;

/**
 * Created by giang on 7/3/17.
 */

public class GameEngViAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private final ModelWord mQuestion;
    private final ArrayList<String> mAnswer;
    public ArrayList<GameFragment.OnShowBackgroundResult> mShowBackgroundResults = new ArrayList<>();
    private EngViFragment mFragmentEngVi;
    private GameInterface mListener;


    public GameEngViAdapter(Context context, EngViFragment fragmentEngVi, ModelWord question, ArrayList<String> answer){
        this.mContext = context;
        this.mFragmentEngVi = fragmentEngVi;
        this.mQuestion = question;
        this.mAnswer = answer;

        if (context instanceof GameInterface) mListener = (GameInterface) context;
        else throw new ClassCastException("this context must implement GameInterface");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_game_engvi, parent, false);

        return new GameEngViItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final GameEngViItemViewHolder viewHolder = (GameEngViItemViewHolder) holder;
        if (mShowBackgroundResults.size() <= position) mShowBackgroundResults.add(viewHolder);
        viewHolder.mTextView.setText(mAnswer.get(position));

        for (int i = 0; i < mQuestion.getmMeanings().length; i++) {
            if (mQuestion.getmMeanings()[i].equals(viewHolder.mTextView.getText().toString())){
                viewHolder.setResult(true);
                break;
            }
        }

        viewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GameFragment.timerIsRunning()) {
                    mFragmentEngVi.cancelTimer();
                    viewHolder.onShowBackgroundResult();
                    showTrueAnswer();

                    mListener.changeQuestionDelay(viewHolder.isTrue, new ModelWordGame(mQuestion));
                }
            }
        });

    }

    private void showTrueAnswer() {
        for (int i = 0; i < mAnswer.size(); i++) {
            GameEngViItemViewHolder item = (GameEngViItemViewHolder) mShowBackgroundResults.get(i);
            if (item.isTrue) item.onShowBackgroundResult();
            item.mTextView.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return mAnswer.size();
    }

    private class GameEngViItemViewHolder extends RecyclerView.ViewHolder implements GameFragment.OnShowBackgroundResult{
        private TextView mTextView;
        private boolean isTrue = false;
        public GameEngViItemViewHolder(View view) {
            super(view);
            this.mTextView = (TextView) view.findViewById(R.id.rectangle_engvi);

            DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
            int width = (int) (displayMetrics.widthPixels * 0.9 - 60);
            int height = (int) (displayMetrics.heightPixels * 0.1);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            params.setMargins(30, 0, 30, 30);
            this.mTextView.setLayoutParams(params);

        }

        public void setResult(boolean isTrue){
            this.isTrue = isTrue;
        }

        @Override
        public void onShowBackgroundResult() {
            if (isTrue)mTextView.setBackgroundResource(R.drawable.rectangle_engvi_true);
            else mTextView.setBackgroundResource(R.drawable.rectangle_engvi_false);

            AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.bubble);
            animatorSet.setTarget(mTextView);
            animatorSet.start();
        }
    }
}
