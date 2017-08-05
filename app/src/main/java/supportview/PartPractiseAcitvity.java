package supportview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TimerTask;

import part5.Fragment5Manager;

import part7.Fragment7Manager;
import supportview.IManagerPart;
import testreading.FragmentTestReadManager;

/**
 * Created by dainguyen on 6/2/17.
 */

public class PartPractiseAcitvity extends AppCompatActivity{
    private static TextView text_correct;
    private TextView text_title;
    private static int mode =0;
    private static int countCorrect=0;
    private int position=0;
    private int time =0;
    private int key =0;
    private String title;
    public static  CountDownTimer timer ;
    private int timesave=0;
    private int part=0;
    private IManagerPart manager;
    private InterstitialAd interstitialAd;
    @Override
    public void onBackPressed() {
        if(timer!=null)timer.cancel();

        if(mode==1 && manager.getIsSubmit()==0){
            Intent data = new Intent();
            Bundle bundle = manager.getBunldeSave();
            bundle.putInt("time",timesave);
            data.putExtras(bundle);
            setResult(99,data);
        }
        mode=0;
        countCorrect=0;
        super.onBackPressed();
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part5_practise);

        MobileAds.initialize(this, "ca-app-pub-8931491699276169~6897306733");
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        getData();
        setUpLayout();
        if(mode==1)setTime();
    }

    private void setTime() {
        int count =3*60*1000;
        if(key==1 & mode==1){
            count= getIntent().getExtras().getInt("time");
        }
        createTime(count);
    }

    public void createTime(int count){
        timer= new CountDownTimer(count, 1000) {

            public void onTick(long millisUntilFinished) {
                int min = (int) (millisUntilFinished/60000);
                int second = (int) ((millisUntilFinished-min*60000)/1000);
                String mintime =String.valueOf(min);
                String secondtime=String.valueOf(second);
                if(min<9)mintime="0"+mintime;
                if(second<10)secondtime ="0"+ secondtime;
                text_correct.setText(mintime+":" +secondtime);

                timesave = (int) millisUntilFinished;
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                /*
                fun finnish
                auto submit
                 */
                text_correct.setText("00:00");
                manager.timeSubmit();
            }

        }.start();
    }

    public  static void stopTime(){
        timer.cancel();
    }


    public void setUpLayout(){
        text_correct = (TextView)findViewById(R.id.text_correct);
        text_title = (TextView)findViewById(R.id.text_title);
        text_title.setText(title);
        //8 full test reading

        if(part==5)manager = new Fragment5Manager();
        else if(part==6)manager = new Fragment7Manager();
        else if(part==7)manager = new Fragment7Manager();
        else if(part==8)manager = new FragmentTestReadManager();
        Bundle bundle = getIntent().getExtras();
        manager.setBundle(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.frame,(Fragment) manager).commit();

    }

    public static void updateTextCorrect(boolean re ,int count){
        if(mode!=1){
            if(re)countCorrect = countCorrect+1;
            text_correct.setText("Correct "+String.valueOf(countCorrect) + "/" + String.valueOf(count));
        }
    }


    public void getData() {
       Bundle b =getIntent().getExtras();
        if(b!=null){
            mode = b.getInt("mode");
            key =b.getInt("key");
            title =b.getString("title");
            position = b.getInt("position");
            time = b.getInt("time");
            part=  b.getInt("part");
        }
    }

    public void showAds(){
       // interstitialAd.loadAd(new AdRequest.Builder().build());
        //interstitialAd.show();;

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.i("Ads", "onAdLoaded");

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i("Ads", "onAdFailedToLoad");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                if(mode==1)timer.cancel();
                Log.i("Ads", "onAdOpened");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.i("Ads", "onAdLeftApplication");

            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                if(mode==1)createTime(timesave);
                interstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
    }





}
