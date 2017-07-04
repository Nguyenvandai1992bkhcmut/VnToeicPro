package part5;

import android.app.Dialog;
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
import supportview.IManagerPart;
import supportview.PartPractiseAcitvity;
import supportview.SendAsynTask;


/**
 * Created by dainguyen on 6/28/17.
 */

public class Fragment5Manager extends Fragment implements FragmentPart5Question.IPart5Manager , Fragment5Control.IPart5Control, IManagerPart {
    public FrameLayout frame_question;
    public FrameLayout frame_explan;
    public FrameLayout frame_blur;
    public ImageView img_float;
    public ImageView img_figure;
    public ImageView img_commment;

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

    FragmentPart5Question fragmentPart5Question;
    FragmentPartExplan fragmentPart5Explan;
    Fragment5Control fragment5Control;

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
        setUpEventImageFloat();
        setContent();
        setContentExplan();
        setUpEventImageFigure();
    }

    public void setUpEventImageFigure(){
        img_figure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (issubmit==1) {
                    fragmentPart5Question.showFigure();
                }
            }
        });
    }

    public void showImage(){
        Animation scaleAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.scale_img);
        img_figure.setVisibility(View.VISIBLE);
        img_commment.setVisibility(View.VISIBLE);
        img_figure.startAnimation(scaleAnimation);
        img_commment.startAnimation(scaleAnimation);
    }

    public void hideImage(){
        Animation scaleAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.scale_img_out);
        img_figure.startAnimation(scaleAnimation);
        img_commment.startAnimation(scaleAnimation);
        img_figure.setVisibility(View.GONE);
        img_commment.setVisibility(View.GONE);
    }

    public void setUpEventImageFloat(){
        img_float.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(frame_blur.getChildCount()==0) {
                   showFragmentControl();
                }else {
                    getChildFragmentManager().beginTransaction().addToBackStack(null).remove(fragment5Control).commit();
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
        if(frame_question.getChildCount()>0){
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).remove(fragmentPart5Question).commit();
        }

        Bundle bundle = new Bundle();
        bundle.putInt("mode",mode);
        bundle.putInt("question",index);
        bundle.putInt("submit",issubmit);
        if(mode==0||mode==2)bundle.putSerializable("data",data[0]);
        else bundle.putSerializable("data",data[index]);
        bundle.putString("select",arrchoose.get(index));
        fragmentPart5Question = new FragmentPart5Question();
        fragmentPart5Question.setArguments(bundle);
        fragmentPart5Question.setIPart5Manager(this);

        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(frame_question.getId(),fragmentPart5Question).commit();
    }

    public void setContentExplan(){
        if(frame_explan.getChildCount()>0){
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).remove(fragmentPart5Explan).commit();
        }
        Bundle bundle = new Bundle();
        if(mode==0||mode==2)bundle.putSerializable("data",data[0]);
        else bundle.putSerializable("data",data[index]);
        bundle.putInt("submit",issubmit);
        fragmentPart5Explan = new FragmentPartExplan();
        fragmentPart5Explan.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(frame_explan.getId(),fragmentPart5Explan).commit();

    }

    @Override
    public void addResult( String select) {
        if(mode!=1){
            //
            // send Sever
            sendResultToSever(String.valueOf(arrQuestion.get(index)),select);
            arrchoose.set(index,select);
            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
            String date = df.format(Calendar.getInstance().getTime());
            boolean re= false;
            if (data[0].getResult().equals(select)) re = true;
            SqlitePart5 sqlitePart5 = new SqlitePart5();
            sqlitePart5.updateTimePart(5,arrQuestion.get(index));
            if(re) sqlitePart5.insertPartCheck(new ModelPartCheck(5,arrQuestion.get(index),date,1));
            else sqlitePart5.insertPartCheck(new ModelPartCheck(5,arrQuestion.get(index),date,0));
            flag_jump=1;
            issubmit=1;
            setContentExplan();
            showImage();
            PartPractiseAcitvity.updateTextCorrect(re,arrQuestion.size());
        }else if(mode==1){
            arrchoose.set(index,select);
        }
    }

    @Override
    public void nextQues() {
        if(mode==0 || mode==2){
            // unlimit Question
            if(flag_jump==0)return;
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
                setContent();
                setContentExplan();
            }
        }


    }

    @Override
    public void backQues() {
        if(mode==0)return;
        else if(mode==1){
            if(index!=0) {
                index--;
                setContent();
                setContentExplan();
            }
        }else if(mode==3 || mode==4){
            if(index!=0){
                index--;
            }
            if(!arrchoose.get(index).equals("Not choose")){
                issubmit=1;
                showImage();
            }
            else{
                hideImage();
                issubmit=0;
            }
            setContent();
            setContentExplan();
        }
    }

    @Override
    public void removeFragmentControl() {
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
                setContent();
                setContentExplan();
            }
    }

    @Override
    public void notfySubmit() {
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
    public void showDialogNofiSubmit(String text){
        final Dialog dialog = new Dialog(getContext(),R.style.ActivityDialog);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width=WindowManager.LayoutParams.WRAP_CONTENT;
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
        showImage();
        setContent();
        setContentExplan();

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
