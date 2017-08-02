package gamevocabulary.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;

import gamevocabulary.Fragment.EngViFragment;
import gamevocabulary.Fragment.TypingFragment;
import gamevocabulary.Fragment.ViEngFragment;
import gamevocabulary.GameInterface;
import gamevocabulary.RandomHelper;
import model.ModelWord;
import model.ModelWordGame;
import vocabulary.fragment.WordDetailFragment;

/**
 * Created by giang on 7/10/17.
 */

public class PracticeActivity extends AppCompatActivity implements GameInterface{

    private static final String WORDS = "words";
    private static final int TIME_DELAY = 2 * 1000;
    private static final String TYPING_FRAGMENT = "typing fragment";
    private ModelWordGame[] mWords;
    private ProgressBar mProgressBar;
    private RelativeLayout mGotIt;
    private int mCount;
    private ModelWordGame[] mCurrentPack;
    private ModelWordGame mCurrentWord;
    private boolean onTypingFragment = false;

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

    private void setUpData() {
        Bundle bundle = getIntent().getExtras();
        ModelWord[] listWords = (ModelWord[]) bundle.getSerializable(WORDS);
        this.mWords = new ModelWordGame[listWords.length];
        for (int i = 0; i < listWords.length; i++) {
            mWords[i] = new ModelWordGame(listWords[i]);
        }

        mCurrentPack = mWords;
        this.mCount = RandomHelper.getTestTimes(mCurrentPack);
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

                        if (lastResult) {
                            mCurrentPack[checkIfAlreadyInPack(mCurrentPack, word)].add(1);
                            controlFragment();

                        } else {
                            mCurrentPack[checkIfAlreadyInPack(mCurrentPack, word)].add(0);
                            startWordDetailFragment(word);
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

    @Override
    public void showKeyBoard(Fragment fragment) {

        if (fragment instanceof TypingFragment) {
            fragment.getView().requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInputFromWindow(fragment.getView().getWindowToken(), InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void controlFragment() {

        if (mCount == 0) {
            //show result and finish


        } else
            controlFragmentInPack();

    }

    private void controlFragmentInPack() {
        if (mCurrentWord == null) {
            this.mCurrentWord = RandomHelper.getWord(mCurrentPack);
        }
        createGameFragment(mCurrentWord);
        mCount--;
        mCurrentWord = null;
    }

    private void createGameFragment(ModelWordGame wordGame) {

        int randomFragmentGame = (int) (Math.random() * 3);
        if (randomFragmentGame == 0) {

            //start Eng-Vi Game Fragment
            onTypingFragment = false;

            ArrayList<String> answers = RandomHelper.createAnswerEngVi(mWords, wordGame);
            EngViFragment fragment = EngViFragment.newInstance(wordGame.mWord, answers);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, fragment)
                    .commit();

        } else if (randomFragmentGame == 1){
            //start Vi-Eng Game Fragment

            onTypingFragment = false;

            ArrayList<String> answers = RandomHelper.createAnswerViEng(mWords, wordGame);
            ViEngFragment fragment = ViEngFragment.newInstance(wordGame.mWord, answers);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, fragment)
                    .commit();
        } else {
            //start Typing Game

            onTypingFragment = true;
            TypingFragment fragment = TypingFragment.newInstance(wordGame.mWord);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, fragment, TYPING_FRAGMENT)
                    .commit();
        }

    }

    private void startWordDetailFragment(ModelWordGame wordGame){

        onTypingFragment = false;

        mGotIt.setVisibility(View.VISIBLE);
        WordDetailFragment detailFragment = WordDetailFragment.newInstance(wordGame.mWord);
        mGotIt.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, detailFragment)
                .commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        TypingFragment typingFragment = (TypingFragment) getSupportFragmentManager().findFragmentByTag(TYPING_FRAGMENT);
        if (typingFragment != null && typingFragment.isVisible()) {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                typingFragment.showResult();
            } else if (keyCode == KeyEvent.KEYCODE_DEL) {
                typingFragment.addTextIntoCell("");
            } else {
                typingFragment.addTextIntoCell((char)event.getUnicodeChar() + "");
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    private void changeText(String s) {
        TypingFragment typingFragment = (TypingFragment) getSupportFragmentManager().findFragmentByTag(TYPING_FRAGMENT);
        if (typingFragment != null && typingFragment.isVisible()) {
            typingFragment.addTextIntoCell(s);
        }

    }

    private void checkResult() {

    }
}
