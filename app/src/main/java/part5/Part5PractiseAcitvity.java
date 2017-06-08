package part5;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

/**
 * Created by dainguyen on 6/2/17.
 */

public class Part5PractiseAcitvity extends AppCompatActivity{
    private static TextView text_correct;
    private TextView text_title;
    private static int mode =0;
    private static int countCorrect=0;
    private int time =0;
    private int key =0;
    private String title;
    CountDownTimer timer ;

    @Override
    public void onBackPressed() {
        if(timer!=null)timer.cancel();
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
       timer= new CountDownTimer(1*60*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int min = (int) (millisUntilFinished/60000);
                int second = (int) ((millisUntilFinished-min*60000)/1000);
                String mintime =String.valueOf(min);
                String secondtime=String.valueOf(second);
                if(min<9)mintime="0"+mintime;
                if(second<10)secondtime ="0"+ secondtime;

                text_correct.setText(mintime+":" +secondtime);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                /*
                fun finnish
                auto submit
                 */
                FragmentPart5Manager.issubmit=1;

            }

        }.start();
    }


    public void setUpLayout(){
        text_correct = (TextView)findViewById(R.id.text_correct);
        text_title = (TextView)findViewById(R.id.text_title);
        text_title.setText(title);
        FragmentPart5Manager fragmentPart5Manager = new FragmentPart5Manager();
        Bundle bundle = new Bundle();
        bundle.putInt("mode",mode);
        bundle.putInt("key",key);
        fragmentPart5Manager.setArguments(bundle);
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
        }
    }



}
