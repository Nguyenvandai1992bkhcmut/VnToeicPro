package listenvocabularry;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.Locale;

import listenvocabularry.fragment.ListenVocabularyFragment;
import listenvocabularry.service.PlayService;
import model.ModelWord;

/**
 * Created by giang on 7/15/17.
 */

public class ListenActivity extends AppCompatActivity {

    public static final String ID_LESSON = "idLesson";
    public static final String LIST_WORDS = "list words";
    private static final String LISTEN_FRAGMENT = "listen fragment";
    private PlayService mService;

    private BroadcastReceiver mReceiverPauseIntent = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ListenVocabularyFragment fragment = (ListenVocabularyFragment) getSupportFragmentManager().findFragmentByTag(LISTEN_FRAGMENT);
            fragment.pause();
        }
    };

    private BroadcastReceiver mReceiverActiveIntent = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int item = intent.getIntExtra(PlayService.ITEM, -1);
            boolean isActive = intent.getBooleanExtra(PlayService.ACTIVE, false);

            ListenVocabularyFragment fragment = (ListenVocabularyFragment) getSupportFragmentManager().findFragmentByTag(LISTEN_FRAGMENT);
            fragment.updateItem(item, isActive);
        }
    };


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayService.LocalBinder binder = (PlayService.LocalBinder) service;
            mService = binder.getService();
            if (mFragment != null) {
                changeRate(mFragment.getRate());
            }
            mService.changeLanguage(Locale.ENGLISH);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private String[] mWords;
    private ListenVocabularyFragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_vocabularry);

        ModelWord[] words = (ModelWord[]) getIntent().getSerializableExtra(LIST_WORDS);

        this.mWords = new String[words.length];
        for (int i = 0; i < mWords.length; i++) {
            mWords[i] = words[i].getmWord();
        }


        mFragment = ListenVocabularyFragment.newInstance(mWords);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.contentLayout, mFragment, LISTEN_FRAGMENT)
                .commit();


    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this, PlayService.class);
        intent.putExtra(LIST_WORDS, mWords);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiverPauseIntent, new IntentFilter(PlayService.BROADCAST_PAUSE));
        registerReceiver(mReceiverActiveIntent, new IntentFilter(PlayService.BROADCAST_ITEM_ACTIVE));
    }

    public void play() {
        mService.play();
    }

    public void pause(){
        mService.pause();
    }

    public void changeRate(float rate) {
        mService.changeRate(rate);
    }

    public void changeReplay(boolean isReplay) {
        mService.changeReplay(isReplay);
    }

    public void onClickItem(int position) {
        mService.onClickItem(position);
    }

    public void changeSpeaker(Locale locale) {
        mService.changeLanguage(locale);
    }
}
