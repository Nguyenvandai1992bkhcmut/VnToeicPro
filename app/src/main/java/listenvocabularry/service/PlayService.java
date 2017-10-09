package listenvocabularry.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Locale;

import listenvocabularry.ListenActivity;

/**
 * Created by giang on 7/15/17.
 */

public class PlayService extends Service {
    public static final String ITEM = "item";
    public static final String ACTIVE = "active";
    private TextToSpeech mTextToSpeech;
    private Locale mLocal = Locale.ENGLISH;
    private LocalBinder mBinder = new LocalBinder();
    private String[] mWords;
    private int i = 0;
    private boolean isPlay = false;
    private boolean isRepeat = false;
    public static final String BROADCAST_PAUSE = "listenvocabularry.service.updateUI_pause";
    public static final String BROADCAST_ITEM_ACTIVE = "listenvocabularry.service.updateUI_item_active";
    private Intent mBroadCastPause, mBroadCastActive;
    private boolean isClicked = false;

    @Override
    public void onCreate() {
        mTextToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                mTextToSpeech.setLanguage(mLocal);
                mTextToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    @Override
                    public void onStart(String utteranceId) {

                    }

                    @Override
                    public void onDone(String utteranceId) {
                        if (isClicked) {
                            int position = findItemPositionByString(utteranceId);
                            mBroadCastActive.putExtra(ITEM, position);
                            mBroadCastActive.putExtra(ACTIVE, false);
                            sendBroadcast(mBroadCastActive);

                            if (isPlay) controlSpeaking();
                        } else {
                            mBroadCastActive.putExtra(ITEM, i);
                            mBroadCastActive.putExtra(ACTIVE, false);
                            sendBroadcast(mBroadCastActive);

                            if (!isRepeat) i++;
                            if (isPlay) controlSpeaking();
                        }


                    }

                    @Override
                    public void onError(String utteranceId) {

                    }
                });
            }
        });

        mBroadCastPause = new Intent(BROADCAST_PAUSE);
        mBroadCastActive = new Intent(BROADCAST_ITEM_ACTIVE);
    }

    private int findItemPositionByString(String s) {
        for (int i = 0; i < mWords.length; i++){
            if (mWords[i].equals(s)) return i;
        }
        return 0;
    }

    private void controlSpeaking() {
        if (i >= mWords.length){
            isPlay = false;
            i = 0;
            sendBroadcast(mBroadCastPause);
            return;
        }
        mBroadCastActive.putExtra(ITEM, i);
        mBroadCastActive.putExtra(ACTIVE, true);
        sendBroadcast(mBroadCastActive);

        speak(mWords[i]);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mWords = intent.getStringArrayExtra(ListenActivity.LIST_WORDS);
        return mBinder;
    }

    public void changeReplay(boolean isRepeat) {
        this.isRepeat = isRepeat;
    }

    public class LocalBinder extends Binder{
        public PlayService getService() {
            return PlayService.this;
        }
    }

    public void speak(String word) {
        if (isClicked) isClicked = false;
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, word);

        mTextToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, map);
    }

    public void play(){
        isPlay = true;
        controlSpeaking();
    }

    public void pause(){
        isPlay = false;
    }

    public void onClickItem(int position){

        this.isClicked = true;
        this.i = position;
    }

    public void changeRate(float rate) {
        mTextToSpeech.setSpeechRate(rate);
    }

    public void changeLanguage(Locale loc) {
        mTextToSpeech.setLanguage(loc);
    }
}
