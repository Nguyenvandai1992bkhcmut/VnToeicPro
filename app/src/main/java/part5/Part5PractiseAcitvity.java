package part5;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by dainguyen on 6/2/17.
 */

public class Part5PractiseAcitvity extends AppCompatActivity{
    private static TextView text_correct;
    private TextView text_title;
    private static int mode =0;
    private static int countCorrect=0;
    private int position=0;
    private int time =0;
    private int key =0;
    private String title;
    public CountDownTimer timer ;
    FragmentPart5Manager fragmentPart5Manager;
    private int timesave=0;
    @Override
    public void onBackPressed() {
        if(timer!=null)timer.cancel();

        if(mode==1 && fragmentPart5Manager.issubmit==0){
            Intent data = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt("time",timesave);
            bundle.putInt("begin",fragmentPart5Manager.index);
            ArrayList<String>arr = new ArrayList<>();

            bundle.putIntegerArrayList("question",fragmentPart5Manager.arryQuestion);
            bundle.putStringArrayList("choose",fragmentPart5Manager.arrayChoose);
            data.putExtras(bundle);
            setResult(99,data);

        }
        super.onBackPressed();
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part5_practise);
        getData();
        setUpLayout();
        if(mode==1)setTime();
    }

    private void setTime() {
        int count =time*60*1000;
        if(key==1 & mode==1){
            count= getIntent().getExtras().getInt("time");
        }
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
                fragmentPart5Manager.issubmit=1;

            }

        }.start();
    }


    public void setUpLayout(){
        text_correct = (TextView)findViewById(R.id.text_correct);
        text_title = (TextView)findViewById(R.id.text_title);
        text_title.setText(title);
        fragmentPart5Manager = new FragmentPart5Manager();

        fragmentPart5Manager.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.frame,fragmentPart5Manager).commit();

    }

    public static void updateTextCorrect(boolean re ,int count){
        if(mode==0 || mode==2){
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
            time = b.getInt("Time");
        }
    }



}
