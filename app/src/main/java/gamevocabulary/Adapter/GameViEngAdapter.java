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

import gamevocabulary.Fragment.GameFragment;
import gamevocabulary.Fragment.ViEngFragment;
import gamevocabulary.GameInterface;
import model.ModelWord;
import model.ModelWordGame;

/**
 * Created by giang on 7/3/17.
 */

public class GameViEngAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private final ViEngFragment mFragment;
    private final ModelWord mQuestion;
    private final ArrayList<String> mAnswers;
    public ArrayList<GameFragment.OnShowBackgroundResult> mShowBackgroundResults = new ArrayList<>();
    private GameInterface mListener;

    public GameViEngAdapter(Context context, ViEngFragment fragment, ModelWord question, ArrayList<String> answers){
        this.mContext = context;
        this.mFragment = fragment;
        this.mQuestion = question;
        this.mAnswers = answers;

        if (context instanceof GameInterface) mListener = (GameInterface) context;
        else throw new ClassCastException("this context must implement GameInterface");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_game_vieng, parent, false);
        return new GameViEngItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final GameViEngItemViewHolder viewHolder = (GameViEngItemViewHolder) holder;
        if (mShowBackgroundResults.size() <= position) mShowBackgroundResults.add(viewHolder);
        viewHolder.mTextView.setText(mAnswers.get(position));

        if (mQuestion.getmWord().equals(viewHolder.mTextView.getText().toString())) viewHolder.setResult(true);

        viewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GameFragment.timerIsRunning()){
                    mFragment.cancelTimer();
                    viewHolder.onShowBackgroundResult();
                    showTrueAnswer();

                    mListener.changeQuestionDelay(viewHolder.isTrue, new ModelWordGame(mQuestion));
                }
            }
        });
    }

    private void showTrueAnswer() {
        for (int i = 0; i < mAnswers.size(); i++) {
            GameViEngItemViewHolder item = (GameViEngItemViewHolder) mShowBackgroundResults.get(i);
            if (item.isTrue) item.onShowBackgroundResult();
            item.mTextView.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return mAnswers.size();
    }

    private class GameViEngItemViewHolder extends RecyclerView.ViewHolder implements GameFragment.OnShowBackgroundResult{
        private final TextView mTextView;
        private boolean isTrue;

        public GameViEngItemViewHolder(View view) {
            super(view);
            this.mTextView = (TextView) view.findViewById(R.id.text_answer);

            DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
            int width = (int) (displayMetrics.widthPixels * 0.9);
            int height = (int) (displayMetrics.heightPixels * 0.5);

            int d = width < height ? width/2 : height/2;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(d, d);
            params.setMargins(30, 0, 30, 30);
            this.mTextView.setLayoutParams(params);
        }

        public void setResult(boolean isTrue) {
            this.isTrue = isTrue;
        }

        @Override
        public void onShowBackgroundResult() {
            if (isTrue) mTextView.setBackgroundResource(R.drawable.circle_vieng_true);
            else mTextView.setBackgroundResource(R.drawable.circle_vieng_false);

            AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.bubble);
            animatorSet.setTarget(mTextView);
            animatorSet.start();
        }
    }
}
