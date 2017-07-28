package supportview;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.firebase.auth.ActionCodeResult;
import com.vntoeic.bkteam.vntoeicpro.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;

import model.IDataPart;
import model.IListenPart;
import model.ModelPart1;
import model.ModelPart2;
import model.ModelPart3;
import model.ModelPart4;
import model.ModelPartCheck;
import model.ModelPartFavorite;
import part3.FragmentContentPart12;
import part3.FragmentPart34Question;
import part3.FragmentViewPagerListen;
import part5.FragmentSummary;
import sqlite.ManagerPart;
import sqlite.SqlitePart1;
import sqlite.SqlitePart2;
import sqlite.SqlitePart3;
import sqlite.SqlitePart4;

/**
 * Created by dainguyen on 7/11/17.
 */

public class ListenPraticseActivity extends AppCompatActivity implements  IPartControlListen , DowloadTask.IDowload{

    private int mode;
    private int key,position;
    private String title;
    private int part;
    private int subject;


    private FrameLayout frame_control;
    private FrameLayout frame_content;
    private FrameLayout frame_ads;
    private SeekBar seekBar_audio;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private FragmentControlListening fragmentControlListening;
    private FragmentViewPagerListen fragmentViewPagerListen;


    private ImageView img_submit;
    private ImageView img_explan;
    private ImageView img_question;
    private ImageView img_figure;
    private ImageView img_comment;


    private TextView text_title;
    private TextView text_correct;
    private int correct=0;
    private int total=0;
    private AdView adView;


    private ArrayList<IListenPart> data = new ArrayList<>();

    private int index = 0;
    private int isFigure=0;
    private int isQuestion=1;
    private int isExplan=0;
    private int isComment=0;

    private int isPrepareData=0;



    private int isFinishAudio=0;
    private ArrayList<ArrayList<String>> arrChoose = new ArrayList<>();
    private ArrayList<ArrayList<String>> arrResult = new ArrayList<>();
    private ArrayList<Integer> arrQuestion = new ArrayList<>();

    private ArrayList<Integer> arrSubmit = new ArrayList<>();

    private Dialog dialog_load;
    private WindowManager.LayoutParams params;
    private ArrayList<Boolean>arrdowload;


    @Override
    public void onBackPressed() {

        if(mode==1 && arrSubmit.contains(0)){
            Intent data = new Intent();
            Bundle bundle = getBunldeSave();
            data.putExtras(bundle);
            setResult(99,data);
        }else{
                 /*
            Delete Data prepare for part
             */
            removeDataSave();
            setResult(99,null);
        }

        if(mediaPlayer!=null){
            if(mediaPlayer.isPlaying())mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onBackPressed();
        finish();
    }

    public void removeDataSave(){
        File fileAudio;
        File fileImage;
        for(int i=0;i<data.size();i++){
            if(part==1){
                fileImage = new File(data.get(i).getSrcFileImage());
                fileImage.delete();
            }
            fileAudio= new File(data.get(i).getSrcFile());
            fileAudio.delete();
        }
    }

    public Bundle getBunldeSave(){
        Bundle bundle= new Bundle();
        bundle.putInt("begin",index);
        bundle.putIntegerArrayList("question",arrQuestion);
        String s ="";

        for(int i=0;i<arrChoose.size();i++){

            for(int j=0;j<arrChoose.get(i).size();j++){
                if(j!=(arrChoose.get(i).size()-1)){
                    s+=arrChoose.get(i).get(j)+"!";

                }
                else{
                    s+=arrChoose.get(i).get(j);

                }
            }
            if(i!=arrChoose.size()-1)s+="~~~";

        }
        bundle.putString("choose",s);
        return bundle;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partlisten_practise);
        getData();
        setUpLayout();

    }

    public void setUpLayout() {
        img_submit = (ImageView) findViewById(R.id.img_submit);
        img_comment = (ImageView) findViewById(R.id.img_comment);
        img_explan = (ImageView) findViewById(R.id.img_explan);
        img_question = (ImageView) findViewById(R.id.img_question);
        img_figure = (ImageView) findViewById(R.id.img_figure);


        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText(title);

        text_correct =(TextView)findViewById(R.id.text_correct);
        setTextCorrect();

        seekBar_audio = (SeekBar) findViewById(R.id.seekbar_audio);
        seekBar_audio.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        frame_ads= (FrameLayout)findViewById(R.id.frame_ads);

        frame_control = (FrameLayout) findViewById(R.id.fram_control);
        setUpViewControl();

        frame_content = (FrameLayout) findViewById(R.id.frame_content);

        if(part!=2) {
           changeAds(1);

        }else{
            changeAds(0);
        }



        showContent();

        setUpEvenImgQuesiton();
        setUpEventImgSubmit();
        setUpEventImgFigure();
        setUpEvenImgExplan();
        setUpEvenImgComment();



    }

    public void setTextCorrect(){
        text_correct.setText("Correct "+String.valueOf(correct)+"/"+String.valueOf(total));
    }

    public void setUpEvenImgQuesiton(){
        img_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isQuestion==0){
                    if(part==2)changeAds(0);
                    fragmentViewPagerListen.showQuestion();
                    isFigure=0;
                    isQuestion=1;
                    isComment=0;
                    isExplan=0;
                    img_figure.setImageResource(R.mipmap.icon_figure_detach);
                    img_question.setImageResource(R.mipmap.icon_question_active);
                    img_explan.setImageResource(R.mipmap.icon_explan_detach);
                    img_comment.setImageResource(R.mipmap.icon_comment_detach);
                }
            }
        });
    }


    public void setUpEventImgFigure(){
        img_figure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFigure==0){
                    fragmentViewPagerListen.showFigure();
                    isFigure=1;
                    isQuestion=0;
                    isComment=0;
                    isExplan=0;
                    img_figure.setImageResource(R.mipmap.icon_figure_attach);
                    img_question.setImageResource(R.mipmap.icon_question_detach);
                    img_explan.setImageResource(R.mipmap.icon_explan_detach);
                    img_comment.setImageResource(R.mipmap.icon_comment_detach);
                }
            }
        });
    }

    public void setUpEventImgSubmit() {
        img_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToSever();
                showImage();
                setTextCorrect();

                fragmentViewPagerListen.showQuestion();
                fragmentViewPagerListen.reLoadSummary();

            }
        });

    }

    public void setUpEvenImgExplan(){
        img_explan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isExplan==0){
                    fragmentViewPagerListen.showExplan();
                    if(part==2){
                        changeAds(1);
                    }
                    isExplan=1;
                    isQuestion=0;
                    isFigure=0;
                    isComment=0;
                    img_figure.setImageResource(R.mipmap.icon_figure_detach);
                    img_question.setImageResource(R.mipmap.icon_question_detach);
                    img_explan.setImageResource(R.mipmap.icon_explan_attach);
                    img_comment.setImageResource(R.mipmap.icon_comment_detach);

                }
            }
        });

    }

    public void setUpEvenImgComment(){
        img_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(frame_content.getChildCount()>0){

                }
            }
        });
    }

    public void sendToSever() {
        DateFormat df = new SimpleDateFormat("EEE d MMM yyyy, HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        if (mode == 0|| mode==2 || mode==3 || mode==4) {
            arrSubmit.set(index, 1);
            ManagerPart managerPart = new ManagerPart();
            for(int i=0;i<arrChoose.get(index).size();i++) {
                int ch = 0;
                SendAsynTask sendAsynTask = new SendAsynTask();
                sendAsynTask.execute(data.get(index).getLinkFigure(i+1),arrChoose.get(index).get(i));
                if (arrChoose.get(index).get(i).equals(arrResult.get(index).get(i))) {
                    ch = 1;
                    correct++;
                }
                managerPart.insertPartCheck(new ModelPartCheck(part, data.get(index).getId(), date, ch));
            }
            managerPart.updateTimePart(part,data.get(index+1).getId());
        }else if (mode==1){
            for(int i=0;i<arrSubmit.size();i++){
                arrSubmit.set(i,1);
            }
            for(int j=0;j<arrQuestion.size();j++) {
                ManagerPart managerPart = new ManagerPart();
                for (int i = 0; i < arrChoose.get(j).size(); i++) {
                    int ch = 0;
                    if (arrChoose.get(j).get(i).equals(arrResult.get(j).get(i))) {
                        ch = 1;
                        correct++;
                    }
                    managerPart.insertPartCheck(new ModelPartCheck(part, data.get(j).getId(), date, ch));
                }
                managerPart.updateTimePart(part, data.get(j).getId());
            }
        }
    }

    public void showImage() {

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.scale_img);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.scale_img_out);
        img_submit.startAnimation(animation1);
        img_submit.setVisibility(View.GONE);
        img_submit.setEnabled(false);

        img_question.setVisibility(View.VISIBLE);
        img_question.startAnimation(animation);
        img_explan.setVisibility(View.VISIBLE);
        img_explan.startAnimation(animation);
        img_comment.setVisibility(View.VISIBLE);
        img_comment.startAnimation(animation);
        img_figure.setVisibility(View.VISIBLE);
        img_figure.startAnimation(animation);
    }

    public void hideImage() {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.scale_img_out);
        img_question.startAnimation(animation);

        img_explan.startAnimation(animation);

        img_comment.startAnimation(animation);

        img_figure.startAnimation(animation);


        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                defaultImage();
                img_question.setVisibility(View.GONE);
                img_explan.setVisibility(View.GONE);
                img_comment.setVisibility(View.GONE);
                img_figure.setVisibility(View.GONE);
                img_submit.setVisibility(View.GONE);
                img_submit.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    public void defaultImage(){
        isQuestion=1;
        isExplan=0;
        isFigure=0;
        isComment=0;
        img_figure.setImageResource(R.mipmap.icon_figure_detach);
        img_question.setImageResource(R.mipmap.icon_question_active);
        img_explan.setImageResource(R.mipmap.icon_explan_detach);
        img_comment.setImageResource(R.mipmap.icon_comment_detach);


    }

    public void setUpViewControl() {

        fragmentControlListening = new FragmentControlListening();
        Bundle bundle = new Bundle();
        bundle.putInt("mode", mode);
        bundle.putInt("part", part);
        bundle.putSerializable("data", (Serializable) data.get(index));
        fragmentControlListening.setArguments(bundle);
        fragmentControlListening.setiPartControlListen(this);
        getSupportFragmentManager().beginTransaction().addToBackStack(null).add(frame_control.getId(), fragmentControlListening).commit();
    }

    public void getData() {
        Bundle b = getIntent().getExtras();
        if (b != null) {
            mode = b.getInt("mode");
            key = b.getInt("key");
            title = b.getString("title");
            position = b.getInt("position");
            part = b.getInt("part");
            subject = b.getInt("subject");
            setUpData();
        }
    }

    public void setUpData() {
        switch (part) {
            case 1:
                setUpDataPart1();
                break;
            case 2:
                setUpDataPart2();
                break;
            case 3:
                setUpDataPart3();
                break;
            case 4:
                setUpDataPart4();
                break;
        }
    }

    public void setUpDataPart1() {
        if (mode == 0|| mode==2) {
            SqlitePart1 sqlitePart1 = new SqlitePart1();
            ModelPart1 datapart1[] ;
            if(mode==0)datapart1= sqlitePart1.randomPart1(1);
            else datapart1= sqlitePart1.randomPart1Subject(subject,1);
            data.add(datapart1[0]);
            ArrayList<String> ch = new ArrayList<>();
            ArrayList<String> re = new ArrayList<>();
            arrChoose.add(ch);
            arrQuestion.add(datapart1[0].getId());
            ch.add("Not choose");
            re.add(datapart1[0].getSol());
            arrSubmit.add(0);
            arrResult.add(re);
            total+=arrResult.get(0).size();

        } else if (mode == 1) {
            if(key==0){
                SqlitePart1 sqlitePart1 = new SqlitePart1();
                ModelPart1 datapart1[] = sqlitePart1.randomPart1(10);


                for (int i = 0; i < 10; i++) {
                    data.add(datapart1[i]);
                    ArrayList<String> ch = new ArrayList<>();
                    ArrayList<String> re = new ArrayList<>();
                    arrQuestion.add(datapart1[i].getId());
                    ch.add("Not choose");
                    re.add(datapart1[i].getSol());
                    arrSubmit.add(0);
                    arrChoose.add(ch);
                    arrResult.add(re);
                    total+=arrResult.get(i).size();

                }
            }else if(key==1){
                Bundle bundle =getIntent().getExtras();
                arrQuestion = bundle.getIntegerArrayList("question");
                index = bundle.getInt("begin");
                SqlitePart1 sqlitePart1 = new SqlitePart1();
                for(int i=0;i<arrQuestion.size();i++){
                    ModelPart1 p1 = sqlitePart1.searchPart1Id(arrQuestion.get(i));
                    data.add(p1);
                    arrSubmit.add(0);
                    ArrayList<String>re = new ArrayList<>();
                    re.add(p1.getSol());
                    arrResult.add(re);
                    total+=arrResult.get(0).size();
                }

                String choosen = bundle.getString("choose");
                String arr[] = choosen.split("~~~");
                for (int i = 0; i < arr.length; i++) {
                    String arr1[] = arr[i].split("!");
                    ArrayList<String> ch = new ArrayList<>();
                    for (int j = 0; j < arr1.length; j++) {
                        ch.add(arr1[j]);
                    }
                    arrChoose.add(ch);
                }
            }


        }else if(mode ==3 || mode ==4){
            SqlitePart1  sqlitePart1 = new SqlitePart1();
            ModelPart1  datapart1[];
            if(mode==3)datapart1 = sqlitePart1.searchPart1Favorite();
            else datapart1 = sqlitePart1.searchPart1Check();
            for (int i = 0; i < datapart1.length; i++) {
                data.add(datapart1[i]);
                ArrayList<String> ch = new ArrayList<>();
                ArrayList<String> re = new ArrayList<>();
                arrQuestion.add(datapart1[i].getId());
                ch.add("Not choose");
                re.add(datapart1[0].getSol());
                arrSubmit.add(0);
                arrChoose.add(ch);
                arrResult.add(re);
                total+=arrResult.get(i).size();
                index= position;
            }
        }
    }

    public void setUpDataPart2() {
        if (mode == 0|| mode==2) {
            SqlitePart2 sqlitePart2 = new SqlitePart2();
            ModelPart2 datapart2[] ;
            if(mode==0)datapart2= sqlitePart2.randomPart2(1);
            else datapart2= sqlitePart2.randomPart2Subject(subject,1);
            data.add(datapart2[0]);
            ArrayList<String> ch = new ArrayList<>();
            ArrayList<String> re = new ArrayList<>();
            arrQuestion.add(datapart2[0].getId());
            ch.add("Not choose");
            re.add(datapart2[0].getSol());
            arrSubmit.add(0);
            arrChoose.add(ch);
            arrResult.add(re);
            total+=arrResult.get(0).size();

        } else if (mode == 1) {
            if(key==0) {
                SqlitePart2 sqlitePart2 = new SqlitePart2();
                ModelPart2 datapart2[] = sqlitePart2.randomPart2(30);
                for (int i = 0; i < datapart2.length; i++) {
                    data.add(datapart2[i]);
                    ArrayList<String> ch = new ArrayList<>();
                    ArrayList<String> re = new ArrayList<>();
                    arrQuestion.add(datapart2[i].getId());
                    ch.add("Not choose");
                    re.add(datapart2[i].getSol());
                    arrSubmit.add(0);
                    arrChoose.add(ch);
                    arrResult.add(re);
                    total += arrResult.get(i).size();
                }
            }else if(key==1){
                Bundle bundle =getIntent().getExtras();
                arrQuestion = bundle.getIntegerArrayList("question");
                index = bundle.getInt("begin");
                SqlitePart2 sqlitePart2 = new SqlitePart2();
                for(int i=0;i<arrQuestion.size();i++){
                    ModelPart2 p2 = sqlitePart2.searchPart2Id(arrQuestion.get(i));
                    data.add(p2);
                    arrSubmit.add(0);
                    ArrayList<String>re = new ArrayList<>();
                    re.add(p2.getSol());
                    arrResult.add(re);
                    total+=arrResult.get(0).size();

                }
                String choosen = bundle.getString("choose");
                String arr[] = choosen.split("~~~");
                for (int i = 0; i < arr.length; i++) {
                    String arr1[] = arr[i].split("!");
                    ArrayList<String> ch = new ArrayList<>();
                    for (int j = 0; j < arr1.length; j++) {
                        ch.add(arr1[j]);
                    }
                    arrChoose.add(ch);
                }
            }
        }else if(mode ==3 || mode ==4){
            SqlitePart2  sqlitePart2 = new SqlitePart2();
            ModelPart2  datapart2[];
            if(mode==3)datapart2 = sqlitePart2.searchPart2Favorite();
            else datapart2 = sqlitePart2.searchPart2Check();
            for (int i = 0; i < datapart2.length; i++) {
                data.add(datapart2[i]);
                ArrayList<String> ch = new ArrayList<>();
                ArrayList<String> re = new ArrayList<>();
                arrQuestion.add(datapart2[i].getId());
                ch.add("Not choose");
                re.add(datapart2[i].getSol());
                arrSubmit.add(0);
                arrChoose.add(ch);
                arrResult.add(re);
                total+=arrResult.get(i).size();
            }
            index= position;
        }
    }

    public void setUpDataPart3() {
        if (mode == 0|| mode==2) {
            SqlitePart3 sqlitePart3 = new SqlitePart3();
            ModelPart3 datapart3[] ;
            if(mode==0)datapart3= sqlitePart3.randomPart3(1);
            else datapart3= sqlitePart3.randomPart3Subject(subject,1);
            data.add(datapart3[0]);
            ArrayList<String> ch = new ArrayList<>();
            ArrayList<String> re = new ArrayList<>();
            arrQuestion.add(datapart3[0].getId());
            for (int j = 0; j < datapart3[0].getCountQuestion(); j++) {
                re.add(datapart3[0].getSol(j));
                ch.add("Not choose");
            }
            arrSubmit.add(0);
            arrChoose.add(ch);
            arrResult.add(re);
            total+=arrResult.get(0).size();

        } else if (mode == 1) {
            if(key==0) {
                SqlitePart3 sqlitePart3 = new SqlitePart3();
                ModelPart3 datapart3[] = sqlitePart3.randomPart3(10);
                for (int i = 0; i < 10; i++) {
                    data.add(datapart3[i]);
                    ArrayList<String> ch = new ArrayList<>();
                    ArrayList<String> re = new ArrayList<>();
                    arrQuestion.add(datapart3[i].getId());
                    for (int j = 0; j < datapart3[i].getCountQuestion(); j++) {
                        re.add(datapart3[i].getSol(j));
                        ch.add("Not choose");
                    }
                    arrSubmit.add(0);
                    arrChoose.add(ch);
                    arrResult.add(re);
                    total += arrResult.get(i).size();

                }
            }else if(key==1){
                Bundle bundle =getIntent().getExtras();
                arrQuestion = bundle.getIntegerArrayList("question");
                index = bundle.getInt("begin");
                SqlitePart3 sqlitePart3 = new SqlitePart3();
                for(int i=0;i<arrQuestion.size();i++){
                    ModelPart3 p3 = sqlitePart3.searchPart3Id(arrQuestion.get(i));
                    data.add(p3);
                    arrSubmit.add(0);
                    ArrayList<String>re = new ArrayList<>();
                    for (int j = 0; j < p3.getCountQuestion(); j++) {
                        re.add(p3.getSol(j));
                    }
                    arrResult.add(re);
                    total+=arrResult.get(0).size();

                }
                String choosen = bundle.getString("choose");
                String arr[] = choosen.split("~~~");
                for (int i = 0; i < arr.length; i++) {
                    String arr1[] = arr[i].split("!");
                    ArrayList<String> ch = new ArrayList<>();
                    for (int j = 0; j < arr1.length; j++) {
                        ch.add(arr1[j]);
                    }
                    arrChoose.add(ch);
                }
            }
        }else if(mode ==3 || mode ==4) {
            SqlitePart3 sqlitePart3 = new SqlitePart3();
            ModelPart3 datapart3[];
            if (mode == 3) datapart3 = sqlitePart3.searchPart3Favorite();
            else datapart3 = sqlitePart3.searchPart3Check();
            for (int i = 0; i <datapart3.length; i++) {
                data.add(datapart3[i]);
                ArrayList<String> ch = new ArrayList<>();
                ArrayList<String> re = new ArrayList<>();
                arrQuestion.add(datapart3[i].getId());
                for (int j = 0; j < datapart3[i].getCountQuestion(); j++) {
                    re.add(datapart3[i].getSol(j));
                    ch.add("Not choose");
                }
                arrSubmit.add(0);
                arrChoose.add(ch);
                arrResult.add(re);
                total+=arrResult.get(i).size();
            }
            index= position;
        }
    }

    public void setUpDataPart4() {
        if (mode == 0|| mode==2) {
            SqlitePart4 sqlitePart4 = new SqlitePart4();
            ModelPart4 datapart4[] ;
            if(mode==0)datapart4= sqlitePart4.randomPart4(1);
            else datapart4= sqlitePart4.randomPart4Subject(subject,1);
            data.add(datapart4[0]);
            ArrayList<String> ch = new ArrayList<>();
            ArrayList<String> re = new ArrayList<>();
            arrQuestion.add(datapart4[0].getId());
            for (int j = 0; j < datapart4[0].getCountQuestion(); j++) {
                re.add(datapart4[0].getSol(j));
                ch.add("Not choose");
            }
            arrSubmit.add(0);
            arrChoose.add(ch);
            arrResult.add(re);
            total+=arrResult.get(0).size();

        } else if (mode == 1) {
            if(key==0) {
                SqlitePart4 sqlitePart4 = new SqlitePart4();
                ModelPart4 datapart4[] = sqlitePart4.randomPart4(10);
                for (int i = 0; i < 10; i++) {
                    data.add(datapart4[i]);
                    ArrayList<String> ch = new ArrayList<>();
                    ArrayList<String> re = new ArrayList<>();
                    arrQuestion.add(datapart4[i].getId());
                    for (int j = 0; j < datapart4[i].getCountQuestion(); j++) {
                        re.add(datapart4[i].getSol(j));
                        ch.add("Not choose");
                    }
                    arrSubmit.add(0);
                    arrChoose.add(ch);
                    arrResult.add(re);
                    total += arrResult.get(i).size();
                }
            }else if(key==1) {
                Bundle bundle = getIntent().getExtras();
                arrQuestion = bundle.getIntegerArrayList("question");
                index = bundle.getInt("begin");
                SqlitePart4 sqlitePart4 = new SqlitePart4();
                for (int i = 0; i < arrQuestion.size(); i++) {
                    ModelPart4 p4 = sqlitePart4.searchPart4Id(arrQuestion.get(i));
                    data.add(p4);
                    arrSubmit.add(0);
                    ArrayList<String> re = new ArrayList<>();
                    for (int j = 0; j < p4.getCountQuestion(); j++) {
                        re.add(p4.getSol(j));
                    }
                    arrResult.add(re);
                    total += arrResult.get(0).size();

                }
                String choosen = bundle.getString("choose");
                String arr[] = choosen.split("~~~");
                for (int i = 0; i < arr.length; i++) {
                    String arr1[] = arr[i].split("!");
                    ArrayList<String> ch = new ArrayList<>();
                    for (int j = 0; j < arr1.length; j++) {
                        ch.add(arr1[j]);
                    }
                    arrChoose.add(ch);
                }
            }
        }else if(mode ==3 || mode ==4) {
            SqlitePart4 sqlitePart4 = new SqlitePart4();
            ModelPart4 datapart4[];
            if (mode == 3) datapart4 = sqlitePart4.searchPart4Favorite();
            else datapart4 = sqlitePart4.searchPart4Check();
            for (int i = 0; i < datapart4.length; i++) {
                data.add(datapart4[i]);
                ArrayList<String> ch = new ArrayList<>();
                ArrayList<String> re = new ArrayList<>();
                arrQuestion.add(datapart4[i].getId());
                for (int j = 0; j < datapart4[i].getCountQuestion(); j++) {
                    re.add(datapart4[i].getSol(j));
                    ch.add("Not choose");
                }
                arrSubmit.add(0);
                arrChoose.add(ch);
                arrResult.add(re);
                total+=arrResult.get(i).size();
            }
        }
    }

    @Override
    public void addResult(ArrayList<String> choose) {
        arrChoose.set(index, choose);
        if (mode == 0 || mode==2) {
            if (!arrChoose.get(index).contains("Not choose")) {
                if (img_submit.getVisibility() == View.GONE) {
                    img_submit.setVisibility(View.VISIBLE);
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.scale_img);
                    img_submit.startAnimation(animation);
                }
            }
        } else if (mode == 1) {
            boolean check =true;
            for(int i=0;i<arrChoose.size();i++){
                if(arrChoose.get(i).contains("Not choose")){
                    check= false;
                    break;
                }
            }
            if(check){
                if (img_submit.getVisibility() == View.GONE) {
                    img_submit.setVisibility(View.VISIBLE);
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.scale_img);
                    img_submit.startAnimation(animation);
                }
            }
        }else if(mode==3 || mode==4){
            if(!arrChoose.get(index).contains("Not choose")){
                if (img_submit.getVisibility() == View.GONE) {
                    img_submit.setVisibility(View.VISIBLE);
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.scale_img);
                    img_submit.startAnimation(animation);
                }
            }
        }
    }

    @Override
    public int getIsSumit() {
        return arrSubmit.get(index);
    }

    @Override
    public int getIsFinnishAudio() {
        if(part==3||part==4)return 0;
        return isFinishAudio;
    }


    @Override
    public void nextQuesiton() {
        if (mode == 0||mode==2) {
            if (arrSubmit.get(index)==0) {
                //Notify
                Toast.makeText(getApplicationContext(), "You forget submit", Toast.LENGTH_SHORT).show();
            } else {
                if(index<arrQuestion.size()-2){
                    index++;
                    fragmentViewPagerListen.reLoadContent();
                    fragmentViewPagerListen.reLoadSummary();
                    fragmentControlListening.setDefault();
                    defaultImage();
                    setUpControlAudio();
                }
                else {
                    if(isPrepareData==1) {
                         index++;
                        hideImage();
                        nextQuestionAfterPrepare();
                    }else{
                        Toast.makeText(getApplicationContext(),"Can't load file.Please check your internet!",Toast.LENGTH_LONG).show();
                        prepareDowload(index+1);
                    }
                }

            }
        } else if(mode==1) {
            if(arrChoose.get(index).contains("Not choose")){
                Toast.makeText(getApplicationContext(), "You should finnish these question!", Toast.LENGTH_SHORT).show();
            }else {
                if (index != data.size() - 1) index++;
                else index = 0;
                fragmentControlListening.setDefault();
                fragmentViewPagerListen.reLoadContent();
                fragmentViewPagerListen.reLoadSummary();
                setUpControlAudio();
            }

        }else if(mode==3|| mode==4){
            if (index != data.size() - 1) index++;
            else index = 0;
            if(arrSubmit.get(index)==0){
                hideImage();
            }else {
                showImage();
            }
            fragmentControlListening.setDefault();
            fragmentViewPagerListen.reLoadContent();
            setUpControlAudio();
        }

    }

    public void nextQuestionAfterPrepare(){
        fragmentViewPagerListen.reLoadContent();
        fragmentViewPagerListen.reLoadSummary();
        fragmentControlListening.setDefault();
        setUpControlAudio();
        setTextCorrect();
        setUpData();
        isPrepareData=0;
        prepareDowload(index+1);
    }

    public void prepareDowload(int index){
        arrdowload = new ArrayList<>();
        File fileAudio = new File(data.get(index).getSrcFile());
        if(!fileAudio.exists()){
            arrdowload.add(false);
            DowloadTask dowloadTask = new DowloadTask();
            dowloadTask.setiDowload(this);
            dowloadTask.execute(String.valueOf(0),data.get(index).getLinkDowload(),data.get(index).getSrcFile());
        }else arrdowload.add(true);

        if(part==1) {
            File fileImage = new File(data.get(index).getSrcFileImage());
            if (!fileImage.exists()) {
                arrdowload.add(false);
                DowloadTask dowloadTask = new DowloadTask();
                dowloadTask.setiDowload(this);
                dowloadTask.execute(String.valueOf(1), data.get(index).getLinkDowloadImage(), data.get(index).getSrcFileImage());
            } else arrdowload.add(true);
        }

        if(arrdowload.contains(false)){
            //showDialogDowload();
            isPrepareData=0;
        }else isPrepareData=1;
    }

    public void showDialogDowload(){

        if(dialog_load==null) {
            dialog_load = new Dialog(this,R.style.DialogNoBackGround);
             params = dialog_load.getWindow().getAttributes();
            int width = (int) (ListenPraticseActivity.this.getResources().getDisplayMetrics().widthPixels * 0.8);
            params.width = width;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog_load.setCanceledOnTouchOutside(false);
            dialog_load.setContentView(R.layout.dialog_load_data);
        }
        dialog_load.show();;
        dialog_load.getWindow().setAttributes(params);
    }
    @Override
    public void notifySuccess(int numbertask, boolean b, String url, String file) {
        arrdowload.set(numbertask,b);
        if(!b){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  //  Toast.makeText(getApplicationContext(),"Dowload failure. Please check your internet!",Toast.LENGTH_LONG).show();
                    //dialog_load.dismiss();
                    isPrepareData=0;
                    return;
                }
            });
        }else {
            if (!arrdowload.contains(false)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                           isPrepareData=1;
                           //if(dialog_load!=null && dialog_load.isShowing())dialog_load.dismiss();
                           if(fragmentViewPagerListen==null)showContentAfterDowload();
                        return;
                    }
                });
            }else{
                isPrepareData=0;
            }
        }
    }



    @Override
    public void backQuestion() {
        if(mode==3 || mode==4){
            if (index != 0) index--;
            if(arrSubmit.get(index)==0){
                hideImage();
            }else showImage();
            fragmentViewPagerListen.reLoadContent();
            setUpControlAudio();
            fragmentControlListening.setDefault();
        }else {
            if (arrSubmit.get(index)==0) {
                Toast.makeText(getApplicationContext(), "You need finnish this test before review!", Toast.LENGTH_LONG).show();
            } else {
                if (index != 0) {
                    index--;
                    fragmentViewPagerListen.reLoadContent();
                    setUpControlAudio();
                    fragmentControlListening.setDefault();
                    defaultImage();
                }
            }
        }

    }

    public void setUpControlAudio(){
        if (mediaPlayer != null) {
            if(mediaPlayer.isPlaying())mediaPlayer.stop();
            seekBar_audio.setProgress(0);
            mediaPlayer.release();
            mediaPlayer = null;
            fragmentControlListening.setDefault();
        }
    }

    @Override
    public void playAudio() {
        File dataAudio = new File(data.get(index).getSrcFile());
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                isFinishAudio=1;
            }
        });
        if (dataAudio.exists()) {
//            try {
//
//                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                mediaPlayer.setDataSource(data.get(index).getLinkDowload());
//                mediaPlayer.prepare(); // might take long! (for buffering, etc)
//                mediaPlayer.start();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
            try {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(data.get(index).getSrcFile());
                mediaPlayer.prepare(); // might take long! (for buffering, etc)
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
//        }
            seekBar_audio.setMax((mediaPlayer.getDuration()) / 100);
            Timer timer = new Timer();
            MyTimerTask timerTask = new MyTimerTask();
            timer.schedule(timerTask, 0, 200);
        }


    }


    @Override
    public void replayAudio() {
        if(arrSubmit.get(index)==1) {
            mediaPlayer.setLooping(true);
        }else{
            Toast.makeText(getBaseContext(),"You must submit!",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void pauseAudio() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }

    }

    @Override
    public void resumeAudio() {
        if(!mediaPlayer.isPlaying()){
                mediaPlayer.start();
        }
    }

    @Override
    public void changeQuestion(int index) {
        if(mode==0||mode==2){
            if(arrSubmit.contains(0))return;
            else {
                this.index= index;
                showContent();
                setUpControlAudio();
            }
        }else if(mode==1){
            if(arrSubmit.contains(0)){
                Toast.makeText(getApplicationContext(),"You need finnish this test before review!",Toast.LENGTH_LONG).show();
                return;
            }
            else {
                this.index= index;
                showContent();
                setUpControlAudio();
            }
        }
    }

    @Override
    public boolean checkFavorite() {
        ManagerPart managerPart = new ManagerPart();
        return managerPart.checkPartFavorite(part,data.get(index).getId());
    }

    @Override
    public void favoriteQuesiton(boolean b) {
        if(b){
            DateFormat df = new SimpleDateFormat("EEE d MMM yyyy, HH:mm:ss");
            String date = df.format(Calendar.getInstance().getTime());
            if(!checkFavorite()) {
                ManagerPart managerPart = new ManagerPart();
                managerPart.insertPartFavorite(new ModelPartFavorite(part, data.get(index).getId(), date));
            }
        }else{
            if(checkFavorite()){
                ManagerPart managerPart = new ManagerPart();
                managerPart.deletePartFavorite(part, data.get(index).getId());
            }
        }
    }

    @Override
    public Bundle getBunldeQuestion() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", (Serializable) data.get(index));
        bundle.putInt("mode", mode);
        bundle.putInt("part", part);
        bundle.putInt("submit", arrSubmit.get(index));
        bundle.putStringArrayList("choose", arrChoose.get(index));
        bundle.putStringArrayList("result", arrResult.get(index));
        bundle.putInt("index", index);
        bundle.putInt("figure",isFigure);
        int count =0;
        for(int i=0;i<index;i++){
            count+=data.get(i).getCountQuestion();
        }
        bundle.putInt("indexQuesiton",count);
        return bundle;
    }

    @Override
    public Bundle getBunldeSummary() {
        Bundle bundle = new Bundle();
        bundle.putInt("mode", mode);
        bundle.putInt("part", part);
        bundle.putIntegerArrayList("arrsubmit", arrSubmit);
        ArrayList<String> choose = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < arrChoose.size(); i++) {
            String ch = "";
            String re = "";
            for (int j = 0; j < arrChoose.get(i).size(); j++) {
                if (j == 0) {
                    ch += arrChoose.get(i).get(j);
                    re += arrResult.get(i).get(j);
                } else {
                    ch += "!" + arrChoose.get(i).get(j);
                    re += "!" + arrResult.get(i).get(j);
                }
            }
            choose.add(ch);
            result.add(re);
        }
        bundle.putStringArrayList("choose", choose);
        bundle.putStringArrayList("result", result);
        bundle.putIntegerArrayList("question", arrQuestion);
        bundle.putInt("index", index);
        int count=0;
        for(int i=0;i<index;i++){
            count+=data.get(i).getCountQuestion();
        }
        bundle.putInt("indexQuesiton",count);
        return bundle;
    }
    @Override
    public Bundle getBunldeExplan(){
        Bundle bundle = new Bundle();
        bundle.putInt("submit",arrSubmit.get(index));
        bundle.putSerializable("data", (Serializable) data.get(index));
        bundle.putInt("part", part);
        return bundle;
    }


    public void showContent() {
        prepareDowload(index);
        if(isPrepareData==1) {
           showContentAfterDowload();
        }
    }

    public void showContentAfterDowload(){
        fragmentViewPagerListen = new FragmentViewPagerListen();
        Bundle bundle = new Bundle();
        bundle.putInt("part", part);
        fragmentViewPagerListen.setArguments(bundle);
        fragmentViewPagerListen.setiPartControlListen(this);
        getSupportFragmentManager().beginTransaction().addToBackStack(null).add(frame_content.getId(), fragmentViewPagerListen).commit();

        if(mode==0|| mode==2){
            ManagerPart managerPart = new ManagerPart();
            managerPart.updateTimePart(part,data.get(index).getId());
            setUpData();
            prepareDowload(index+1);
        }
    }

    public void changeAds(int k){
            if(adView!=null) frame_ads.removeView(adView);
            // banner
            adView = new AdView(getApplicationContext());
            adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
            if(k==0) {
                adView.setAdSize(AdSize.MEDIUM_RECTANGLE);

            }else{
                adView.setAdSize(AdSize.BANNER);
            }
            adView.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.bg_listem));
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdOpened() {
                    super.onAdOpened();
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    if(frame_ads.getChildCount()==1)frame_ads.addView(adView);
                }

                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                }
            });

    }


    public class MyTimerTask extends java.util.TimerTask {

        @Override
        public void run() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                final int mCurrentPosition = mediaPlayer.getCurrentPosition() / 100;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        seekBar_audio.setProgress(mCurrentPosition);
                    }
                });

            }
        }
    }

}
