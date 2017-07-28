package part5;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Handler;

import model.ModelPart5;
import model.ModelPartCheck;
import model.ModelPartFavorite;

import sqlite.SqlitePart5;
import sqlite.SqlitePart6;
import supportview.FragmentPartExplan;
import supportview.IContentQuestion;
import supportview.IManagerPart;
import supportview.PartPractiseAcitvity;
import supportview.SendAsynTask;


/**
 * Created by dainguyen on 6/28/17.
 */

public class Fragment5Manager extends Fragment implements IContentQuestion, Fragment5Control.IPart5Control, IManagerPart {
    public FrameLayout frame_question;
    public FrameLayout frame_explan;
    public FrameLayout frame_blur;
    public ImageView img_float;
    public ImageView img_figure;
    public ImageView img_commment;
    public ImageView img_question;
    public ImageView img_explan;

    private ModelPart5 []data;
    public ArrayList<Integer>arrQuestion = new ArrayList<>();
    public ArrayList<String>arrchoose = new ArrayList<>();
    public ArrayList<String>arrresult = new ArrayList<>();
    private int key=0;
    public  int mode=0;
    public int index=0;
    public  int issubmit=0;
    public  int flag_jump=0;
    public int subject=0;

    public int isfigure =0;
    public int isquestion=1;
    public int isexplan=1;


    FragmentPart5Question fragmentPart5Question;
    FragmentPartExplan fragmentPart5Explan;
    Fragment5Control fragment5Control;


    private int isanimation =1;
    private int isaniquestion =1;


    private final String LINKPART5= "Http://vntoeic.com/api/v1/part5/result/";



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getData();
    }

    public void getData(){
        Bundle bundle = getArguments();
        if(bundle!=null) {
            mode = bundle.getInt("mode");
            key = bundle.getInt("key");
            //mode ==0  unlimitQuestion
            if(mode==0){
                SqlitePart5  sqlitePart5 = new SqlitePart5();
                data=sqlitePart5.randomPart5(1);
                arrQuestion.add(data[0].getId());
                arrchoose.add("Not choose");
                arrresult.add(data[0].getResult());
            }
            else if(mode==1){
                // 40 question
                isanimation=0;
                if(key==0) {
                    SqlitePart5 sqlitePart5 = new SqlitePart5();
                    data = sqlitePart5.randomPart5(40);
                    for (int i = 0; i < 40; i++) {
                        arrQuestion.add(data[i].getId());
                        arrchoose.add("Not choose");
                        arrresult.add(data[i].getResult());
                    }
                }else  if(key==1){
                    arrchoose = bundle.getStringArrayList("choose");
                    ArrayList<String>quess = bundle.getStringArrayList("question");
                    for(int i=0;i<quess.size();i++){
                        arrQuestion.add(Integer.valueOf(quess.get(i)));

                    }
                    SqlitePart5 sqlite = new SqlitePart5();
                    data=new ModelPart5[arrQuestion.size()];;
                    for(int i =0;i<arrQuestion.size();i++){
                        ModelPart5 modelPart5[] = sqlite.searchPart5Id(arrQuestion.get(i));
                        data[i]=modelPart5[0];
                        arrresult.add(modelPart5[0].getResult());
                    }
                    index= bundle.getInt("begin");
                }
            }else if(mode==2){
                subject = bundle.getInt("subject");
                SqlitePart5  sqlitePart5 = new SqlitePart5();
                data=sqlitePart5.randomPart5Subject(subject,1);
                arrQuestion.add(data[0].getId());
                arrchoose.add("Not choose");
                arrresult.add(data[0].getResult());
            }else if(mode==3){
                SqlitePart5 sqlitePart5 = new SqlitePart5();
                data= sqlitePart5.searchPart5Favorite();
                for (int i = 0; i < data.length; i++) {
                    arrQuestion.add(data[i].getId());
                    arrchoose.add("Not choose");
                    arrresult.add(data[i].getResult());
                }
                index = bundle.getInt("position");
            }else if(mode==4){
                SqlitePart5 sqlitePart5 = new SqlitePart5();
                data= sqlitePart5.searchPart5Check();
                for (int i = 0; i < data.length; i++) {
                    arrQuestion.add(data[i].getId());
                    arrchoose.add("Not choose");
                    arrresult.add(data[i].getResult());
                }
                index = bundle.getInt("position");
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part5_manager,container,false);
        setUpLayout(view);
        return view;
    }

    public void setUpLayout(View view){
        frame_question = (FrameLayout)view.findViewById(R.id.frame_part5_question);
        frame_explan = (FrameLayout)view.findViewById(R.id.frame_part5_explan);
        frame_blur = (FrameLayout)view.findViewById(R.id.frame_blur);
        img_float = (ImageView)view.findViewById(R.id.img_float);
        img_figure = (ImageView)view.findViewById(R.id.img_figure);
        img_commment =(ImageView)view.findViewById(R.id.img_comment);
        img_question =(ImageView)view.findViewById(R.id.img_question);
        img_explan = (ImageView)view.findViewById(R.id.img_explan);
        setUpEventImageFloat();
        setContent();
        setContentExplan();

        setUpEventImageFigure();
        setUpEvenImageQuestion();
        setUpEvenImageExplan();
        setUpEvenImageComment();
    }

    public void setUpEvenImageExplan(){
        img_explan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(issubmit==1){
                    if(isexplan==0){
                        isexplan=1;
                        isanimation=1;
                        img_explan.setImageResource(R.mipmap.icon_explan_attach);
                        img_commment.setImageResource(R.mipmap.icon_comment_detach);
                        setContentExplan();
                    }
                }
            }
        });
    }

    public void setUpEvenImageComment(){
        img_commment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(issubmit==1){
                    if(isexplan==1){
                        isexplan=0;
                        isanimation=1;
                        img_explan.setImageResource(R.mipmap.icon_explan_detach);
                        img_commment.setImageResource(R.mipmap.icon_comment_attach);
                        //setlayout_COmment
                        getChildFragmentManager().beginTransaction().addToBackStack(null).remove(fragmentPart5Explan).commit();
                    }
                }
            }
        });
    }
    public void setUpEventImageFigure(){
        img_figure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (issubmit==1) {
                    if(isfigure==0){
                        isfigure=1;
                        isquestion=0;
                        img_figure.setImageResource(R.mipmap.icon_figure_attach);
                        img_question.setImageResource(R.mipmap.icon_question_detach);

                        isaniquestion=0;
                        setContent();
                        isaniquestion=1;
                    }
                }
            }
        });
    }

    public void setUpEvenImageQuestion(){
        img_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isquestion==0){
                    isfigure=0;
                    isquestion=1;
                    img_figure.setImageResource(R.mipmap.icon_figure_detach);
                    img_question.setImageResource(R.mipmap.icon_question_active);

                    isaniquestion=0;
                    setContent();
                    isaniquestion=1;
                }
            }
        });
    }

    public void showImage(){
        Animation scaleAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.scale_img);
        img_figure.setVisibility(View.VISIBLE);
        img_commment.setVisibility(View.VISIBLE);
        img_question.setVisibility(View.VISIBLE);
        img_explan.setVisibility(View.VISIBLE);
        img_figure.startAnimation(scaleAnimation);
        img_commment.startAnimation(scaleAnimation);
        img_question.startAnimation(scaleAnimation);
        img_explan.startAnimation(scaleAnimation);

    }

    public void hideImage(){
        Animation scaleAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.scale_img_out);
        img_figure.startAnimation(scaleAnimation);
        img_commment.startAnimation(scaleAnimation);
        img_question.startAnimation(scaleAnimation);
        img_explan.startAnimation(scaleAnimation);
        img_figure.setVisibility(View.GONE);
        img_question.setVisibility(View.GONE);
        img_commment.setVisibility(View.GONE);
        img_explan.setVisibility(View.GONE);
        defaultImage();
    }

    public void defaultImage(){
        img_figure.setImageResource(R.mipmap.icon_figure_detach);
        img_question.setImageResource(R.mipmap.icon_question_active);
        img_explan.setImageResource(R.mipmap.icon_explan_attach);
        img_commment.setImageResource(R.mipmap.icon_comment_detach);
        isquestion=1;
        isfigure=0;
        isexplan=1;

    }

    public void setUpEventImageFloat(){
        img_float.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(frame_blur.getChildCount()==0) {
                   showFragmentControl();
                    img_float.setVisibility(View.GONE);
                }

            }
        });

    }

    public void showFragmentControl(){
        fragment5Control = new Fragment5Control();
        fragment5Control.setIPart5Control(Fragment5Manager.this);
        Bundle bundle = new Bundle();
        bundle.putInt("id",arrQuestion.get(index));
        bundle.putIntegerArrayList("question",arrQuestion);
        bundle.putStringArrayList("choose",arrchoose);
        bundle.putStringArrayList("result",arrresult);
        bundle.putInt("mode",mode);
        bundle.putInt("submit",issubmit);
        fragment5Control.setArguments(bundle);
        getChildFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame_blur, fragment5Control).commit();
    }

    public void setContent(){


        Bundle bundle = new Bundle();
        bundle.putInt("mode",mode);
        bundle.putInt("question",index);
        bundle.putInt("submit",issubmit);
        bundle.putInt("figure",isfigure);
        if(mode==0||mode==2)bundle.putSerializable("data",data[0]);
        else bundle.putSerializable("data",data[index]);
        bundle.putString("select",arrchoose.get(index));
        fragmentPart5Question = new FragmentPart5Question();
        fragmentPart5Question.setArguments(bundle);
        fragmentPart5Question.setiContentQuestion(this);
        if(frame_question.getChildCount()>0) {
            if (isaniquestion == 1) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.scale_content_in, R.anim.scale_content_out).replace(frame_question.getId(), fragmentPart5Question).commit();
            }else{
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(frame_question.getId(), fragmentPart5Question).commit();
            }
        }else {
            if (isaniquestion == 1) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.scale_content_in, R.anim.scale_question_out).add(frame_question.getId(), fragmentPart5Question).commit();
            }else{
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(frame_question.getId(), fragmentPart5Question).commit();

            }
        }
    }

    public void setContentExplan(){

        Bundle bundle = new Bundle();
        if(mode==0||mode==2)bundle.putSerializable("data",data[0]);
        else bundle.putSerializable("data",data[index]);
        bundle.putInt("submit",issubmit);
        fragmentPart5Explan = new FragmentPartExplan();
        fragmentPart5Explan.setArguments(bundle);
        if(frame_explan.getChildCount()>0) {
            if (isanimation == 1) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.scale_explan_in, R.anim.scale_explan_out).replace(frame_explan.getId(), fragmentPart5Explan).commit();
            }else {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(frame_explan.getId(), fragmentPart5Explan).commit();

            }
        }else {
            if (isanimation == 1) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.scale_explan_in, R.anim.scale_explan_out).add(frame_explan.getId(), fragmentPart5Explan).commit();
            }else{
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(frame_explan.getId(), fragmentPart5Explan).commit();

            }
        }

    }

    @Override
    public void addChoose( int postion,String select) {
        if(mode!=1){
            //
            // send Sever
            sendResultToSever(String.valueOf(arrQuestion.get(index)),select);
            arrchoose.set(index,select);
            DateFormat df = new SimpleDateFormat("EEE d MMM yyyy, HH:mm:ss");
            String date = df.format(Calendar.getInstance().getTime());
            boolean re= false;
            if (data[0].getResult().equals(select)) re = true;
            SqlitePart5 sqlitePart5 = new SqlitePart5();
            sqlitePart5.updateTimePart(5,arrQuestion.get(index));
            if(re) sqlitePart5.insertPartCheck(new ModelPartCheck(5,arrQuestion.get(index),date,1));
            else sqlitePart5.insertPartCheck(new ModelPartCheck(5,arrQuestion.get(index),date,0));
            flag_jump=1;
            issubmit=1;
            isanimation=0;
            setContentExplan();
            showImage();
            PartPractiseAcitvity.updateTextCorrect(re,arrQuestion.size());
        }else if(mode==1){
            arrchoose.set(index,select);
        }
    }

    @Override
    public void nextContent() {
        isfigure =0;

        if(mode==0 || mode==2){
            // unlimit Question

            if(flag_jump==0)return;
            isanimation=1;
            SqlitePart5 sqlitePart5 = new SqlitePart5();
            if((index<arrQuestion.size()-1) && arrchoose.get(arrchoose.size()-1).equals("Not choose")){
                data=sqlitePart5.searchPart5Id(arrQuestion.get(arrQuestion.size()-1));
                index = arrQuestion.size()-1;
                flag_jump=0;
                issubmit=0;
            }else {

                if(mode==0)data = sqlitePart5.randomPart5(1);
                else  data = sqlitePart5.randomPart5Subject(subject,1);
                arrresult.add(data[0].getResult());
                arrQuestion.add(data[0].getId());
                arrchoose.add("Not choose");
                index = arrQuestion.size() - 1;
                flag_jump = 0;
                issubmit = 0;
            }
            hideImage();
            setContent();
            setContentExplan();


        }else if(mode==1){
            //test 40 question
            if(index!=39) {
                index++;
                defaultImage();
                setContent();
                setContentExplan();
            }
        }else if(mode==3 || mode==4){
            if(index!=data.length-1){
                index++;
                if(!arrchoose.get(index).equals("Not choose")){
                    issubmit=1;
                    showImage();
                }
                else {
                    hideImage();
                    issubmit=0;
                }
                isanimation=1;
                defaultImage();
                setContent();
                setContentExplan();
            }
        }


    }

    @Override
    public void backContent() {
        if(mode==0)return;
        else if(mode==1){
            if(index!=0) {
                index--;
                isfigure=0;
                defaultImage();
                setContent();
                setContentExplan();
            }
        }else if(mode==3 || mode==4){
            isfigure=0;
            if(index!=0) {
                index--;
                if (!arrchoose.get(index).equals("Not choose")) {
                    issubmit = 1;
                    showImage();
                } else {
                    hideImage();
                    issubmit = 0;
                }
                isanimation = 0;
                defaultImage();
                setContent();
                setContentExplan();
            }
        }
    }



    @Override
    public Bundle getBunldeQuestion(int position) {
        return null;
    }



    @Override
    public void removeFragmentControl() {
        img_float.setVisibility(View.VISIBLE);
        getChildFragmentManager().beginTransaction().addToBackStack(null).remove(fragment5Control).commit();
    }

    @Override
    public void changeQuestion(int index) {
        removeFragmentControl();
            if(mode==0|| mode==2){

                SqlitePart5 sqlitePart5 = new SqlitePart5();
                data=sqlitePart5.searchPart5Id(arrQuestion.get(index));
                this.index = index;
                if(arrchoose.get(index).equals("Not choose")){
                   issubmit=0;
                    hideImage();
                }else {
                    issubmit=1;
                    showImage();
                }
                flag_jump=1;
                isanimation=1;
                setContent();
                setContentExplan();


            }else if(mode==1 || mode==3 || mode==4){
                this.index= index;
                if(mode==3 || mode==4){
                    if(!arrchoose.get(index).equals("Not choose")){
                        issubmit=1;
                        showImage();
                    }
                    else{
                        issubmit=0;
                        hideImage();
                    }
                }
                isanimation=1;

                setContent();
                setContentExplan();
            }
    }

    @Override
    public void notfySubmit() {
        removeFragmentControl();
        if(mode==1) {
            if(arrchoose.contains("Not choose")&& issubmit==0){
                        /*
                        chua lam xong khong cho submmit
                         */
                String text = "Question " + String.valueOf(arrchoose.indexOf("Not choose")+1) +" not complete ";
                showDialogNofiSubmit(text);
                return;
            }else if(issubmit==0){
                showImage();
                PartPractiseAcitvity.stopTime();
                timeSubmit();
            }
        }else {
            String text = "Bạn chưa chọn đáp án! ";
            showDialogNofiSubmit(text);
        }
    }

    @Override
    public void back() {
        ((AppCompatActivity)getContext()).onBackPressed();
    }

    @Override
    public void showFeedback() {

    }

    public void showDialogNofiSubmit(String text){
        final Dialog dialog = new Dialog(getContext(),R.style.ActivityDialog);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        int width = (int) (getContext().getResources().getDisplayMetrics().widthPixels*0.8);
        params.width=width;
        params.height=WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.setContentView(R.layout.dialog_submit);
        dialog.getWindow().setWindowAnimations(R.style.PauseDialogAnimation);
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setAttributes(params);
        TextView textView = (TextView)dialog.findViewById(R.id.text_notify);
        TextView text_continues = (TextView)dialog.findViewById(R.id.text_start);
        textView.setText(text);
        text_continues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void timeSubmit(){
        img_figure.setEnabled(true);
        img_figure.setAlpha(1f);
        issubmit=1;
        isanimation=0;
        showImage();
        setContent();
        setContentExplan();
        isanimation=1;

        SqlitePart5 sqlitePart5 = new SqlitePart5();
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());
        for(int i= 0;i<arrchoose.size();i++){
            if(!arrchoose.get(i).equals("Not choose")){
                sendResultToSever(String.valueOf(arrQuestion.get(i)),arrchoose.get(i));
                sqlitePart5.updateTimePart(5,arrQuestion.get(i));
                if(arrchoose.get(i).equals(arrresult.get(i)))sqlitePart5.insertPartCheck(new ModelPartCheck(5,arrQuestion.get(i),date,1));
                else sqlitePart5.insertPartCheck(new ModelPartCheck(5,arrQuestion.get(i),date,0));
            }
        }
    }

    @Override
    public void sendResultToSever(String ... arr) {
        SendAsynTask sendAsynTask = new SendAsynTask();
        sendAsynTask.execute(LINKPART5,arr[0],arr[1]);
    }

    @Override
    public Bundle getBunldeSave() {

        Bundle bundle = new Bundle();
        //bundle.putInt("time",timesave);
        bundle.putInt("begin",index);

        bundle.putIntegerArrayList("question",arrQuestion);
        bundle.putStringArrayList("choose",arrchoose);

        return bundle;
    }

    @Override
    public int getIsSubmit() {
        return issubmit;
    }

    @Override
    public void setBundle(Bundle bundle) {
        this.setArguments(bundle);
    }


}
