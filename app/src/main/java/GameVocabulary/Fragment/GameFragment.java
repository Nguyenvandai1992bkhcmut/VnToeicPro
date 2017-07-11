package GameVocabulary.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;

import GameVocabulary.GameInterface;
import model.ModelWord;
import model.ModelWordGame;

/**
 * Created by giang on 7/3/17.
 */

public abstract class GameFragment extends Fragment {
    private static final String QUESTION = "question";
    private static final String ANSWER = "answer";
    private static final int TIME = 10 * 1000;
    protected Context mContext;
    protected GameInterface mListener;
    protected TextView mQuestionTxt;
    protected ProgressBar mProgressBar;
    protected RecyclerView mRecyclerView;
    protected ModelWord mQuestion;
    protected ArrayList<String> mAnswer;
    protected CountDownTimer mCountDownTimer;
    private static boolean CountDownTimerIsRunning;
    protected LinearLayout mRecyclerLayout;
    protected TextView mAnswerTxt;

    public interface OnShowBackgroundResult {
        void onShowBackgroundResult();
    }


    public static GameFragment initArg(ModelWord question,  ArrayList<String> answers, GameFragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(QUESTION, question);
        bundle.putStringArrayList(ANSWER, answers);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        if (mContext instanceof GameInterface) mListener = (GameInterface) mContext;
        else throw new ClassCastException("context must implement GameInterface");
    }

    @Override
    public void onStart() {
        super.onStart();
        mCountDownTimer.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_voca, container, false);
        bindView(view);
        bindData();

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        this.mQuestion = (ModelWord) bundle.getSerializable(QUESTION);
        this.mAnswer = bundle.getStringArrayList(ANSWER);
    }

    private void bindData() {

        mProgressBar.setMax(getTime());


        mCountDownTimer = new CountDownTimer(getTime(), 100) {

            @Override
            public void onTick(long millisUntilFinished) {
                CountDownTimerIsRunning = true;
                mProgressBar.setProgress((int) millisUntilFinished);
            }

            @Override
            public void onFinish() {
                cancelTimer();
                CountDownTimerIsRunning = false;

                showResult();
                mListener.changeQuestionDelay(false, new ModelWordGame(mQuestion));
            }
        };

        prepareAnswer();
    }

    protected abstract void showResult();

    protected abstract void prepareAnswer();

    protected abstract int getTime();

    public void cancelTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    private void bindView(View view) {
        mQuestionTxt = (TextView) view.findViewById(R.id.text_ques);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerLayout = (LinearLayout) view.findViewById(R.id.recycler_layout);
        mAnswerTxt = (TextView) view.findViewById(R.id.text_ans);
    }

    public static boolean timerIsRunning() {
        return CountDownTimerIsRunning;
    }
}
