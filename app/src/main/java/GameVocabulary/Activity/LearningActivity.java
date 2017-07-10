package GameVocabulary.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;

import GameVocabulary.Fragment.EngViFragment;
import GameVocabulary.Fragment.ViEngFragment;
import GameVocabulary.GameInterface;
import GameVocabulary.RandomHelper;
import model.ModelWord;
import model.ModelWordGame;
import vocabulary.fragment.WordDetailFragment;

/**
 * Created by giang on 7/3/17.
 */

public class LearningActivity extends AppCompatActivity implements GameInterface{

    public static final String WORDS = "words";
    public static final int WORDS_IN_PACK = 4;
    private static final long TIME_DELAY = 2000;
    public static final int T = 5;


    private ModelWordGame[] mWords;
    private ProgressBar mProgressBar;
    private RelativeLayout mGotIt;
    private int mCount = 0;
    private ModelWordGame[] mCurrentPack = new ModelWordGame[WORDS_IN_PACK];
    private ModelWordGame mCurrentWord;
    private int mControlReviewFragment = T;
    private ModelWordGame[] mReviewPack;
    private boolean isReview = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaning);
        setUpData();
        bindView();
        bindData();
    }

    private void bindData() {
        mProgressBar.setMax(mWords.length);
        this.mGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGotIt.setVisibility(View.GONE);
                controlFragment();
            }
        });


    }

    private void bindView() {
        this.mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        this.mGotIt = (RelativeLayout) findViewById(R.id.layoutGotIt);

    }

    private void startPractice() {


    }


    private void controlFragment() {

        if (mCount == 0) {
            mCurrentPack = RandomHelper.getPack(mWords);
            this.mCount = RandomHelper.getTestTimes(mCurrentPack);
        }

        if (mControlReviewFragment == 0) {
            boolean isReview = startReviewFragment();
            mControlReviewFragment = T;

            if (!isReview) controlFragmentInPack();
        } else
            controlFragmentInPack();
    }

    private boolean startReviewFragment() {
        ModelWordGame reviewWord = randomReviewWord();
        if (reviewWord != null){
            isReview = true;
            createGameFragment(reviewWord);
            return true;
        }
        return false;
    }

    private ModelWordGame randomReviewWord() {
        mReviewPack = new ModelWordGame[mWords.length - mCurrentPack.length];
        int indexReviewList = 0;
        for (int i = 0; i < mWords.length; i++) {
            if (mWords[i].mMark >= 6 && checkIfAlreadyInPack(mCurrentPack, mWords[i]) == -1) {
                mReviewPack[indexReviewList] = mWords[i];
                indexReviewList++;
            }
        }

        if (mReviewPack.length == 0) return null;

        int random = (int) (Math.random() * indexReviewList);
        return mReviewPack[random];
    }

    private void controlFragmentInPack() {
        isReview = false;
        if (mCurrentWord == null) {
            this.mCurrentWord = RandomHelper.getWord(mCurrentPack);
        }
        if (mCurrentWord.isLearned == false) {
            mCurrentWord.isLearned = true;
            startWordDetailFragment(mCurrentWord);
        }
        else {
            createGameFragment(mCurrentWord);
            mCount--;
            mControlReviewFragment--;
            mCurrentWord = null;
        }
    }

    private void createGameFragment(ModelWordGame wordGame) {
        Toast.makeText(this, "Count = " + mCount + " and mControl = " + mControlReviewFragment, Toast.LENGTH_SHORT).show();
        int randomFragmentGame = (int) (Math.random() * 2);
        if (randomFragmentGame == 0) {

            //start Eng-Vi Game Fragment
            ArrayList<String> answers = RandomHelper.createAnswerEngVi(mWords, wordGame);
            EngViFragment fragment = EngViFragment.newInstance(wordGame.mWord, answers);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, fragment)
                    .commit();

        } else {
            //start Vi-Eng Game Fragment

            ArrayList<String> answers = RandomHelper.createAnswerViEng(mWords, wordGame);
            ViEngFragment fragment = ViEngFragment.newInstance(wordGame.mWord, answers);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, fragment)
                    .commit();
        }
    }

    private void setUpData() {
        Bundle bundle = getIntent().getExtras();
        ModelWord[] listWords = (ModelWord[]) bundle.getSerializable(WORDS);
        this.mWords = new ModelWordGame[listWords.length];
        for (int i = 0; i < listWords.length; i++) {
            mWords[i] = new ModelWordGame(listWords[i]);
        }
    }


    @Override
    public void changeQuestionDelay(final Boolean lastResult, final ModelWordGame word) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isReview) {
                            if (lastResult) {
                                mCurrentPack[checkIfAlreadyInPack(mCurrentPack, word)].add(1);
                                controlFragment();

                            } else {
                                mCurrentPack[checkIfAlreadyInPack(mCurrentPack, word)].add(0);
                                startWordDetailFragment(word);
                            }
                        } else {

                            if(lastResult){
                                mReviewPack[checkIfAlreadyInPack(mReviewPack, word)].add(1);
                                controlFragment();
                            } else {
                                mReviewPack[checkIfAlreadyInPack(mReviewPack, word)].add(0);
                                startWordDetailFragment(word);

                            }
                        }

                    }
                });
            }
        }, TIME_DELAY);
    }

    private int checkIfAlreadyInPack(ModelWordGame[] pack, ModelWordGame word) {
        for (int i = 0; i < pack.length; i++) {
            if (pack[i].equals(word)) return i;
        }
        return -1;
    }

    private void startWordDetailFragment(ModelWordGame word) {
        mGotIt.setVisibility(View.VISIBLE);
        WordDetailFragment detailFragment = WordDetailFragment.newInstance(word.mWord);
        mGotIt.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, detailFragment)
                .commit();
    }

    @Override
    public void showKeyBoard(Fragment fragment) {

    }
}
