package part5;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

import model.ModelPart5;
import model.ModelPartCheck;
import sqlite.SqlitePart5;
import supportview.BaseFragment;
import supportview.CustomNavigation;

import static com.vntoeic.bkteam.vntoeicpro.R.styleable.CustomNavigation;

/**
 * Created by dainguyen on 6/2/17.
 */

public class FragmentPart5Manager extends BaseFragment implements CustomNavigation.OnItemClickedListener, FragmentSummary.IClickItemSubm , FragmentPart5Question.IAddResult{

    private static FragmentPart5 fragmentPart5;
    FragmentSummary fragmentsubmit ;
    private  ModelPart5 data[];
    public   int index=0;
    private  int mode =0;
    private  int key=0;

    private  int flag_jump=0;
    public   ArrayList<Integer>arryQuestion= new ArrayList<>();
    public   ArrayList<String>arrayChoose= new ArrayList<>();
    public   ArrayList<String>arrayResult = new ArrayList<>();

    public  int issubmit=0;

    @Override
    public  void addResult(int question,  String choose){
        if(mode==0 || mode==2){
            SendAsynTask sendAsynTask = new SendAsynTask();
            sendAsynTask.execute(String.valueOf(question),choose);

            fragmentPart5.showExplan();
            fragmentPart5.setFlagFigure(1);
            boolean re = false;
            SqlitePart5 sqlitePart5 = new SqlitePart5();
            if(key==0) {
                arryQuestion.add(question);
                arrayChoose.add(choose);
                if (data[index].getResult().equals(choose)) re = true;
                arrayResult.add(data[index].getResult());
                Part5PractiseAcitvity.updateTextCorrect(re, arryQuestion.size());
                sqlitePart5.updateTimePart(5, data[index].getId());
            }else{
                arrayChoose.set(arryQuestion.indexOf(question),choose);
            }

            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
            String date = df.format(Calendar.getInstance().getTime());
            if(re) sqlitePart5.insertPartCheck(new ModelPartCheck(5,question,date,1));
            else sqlitePart5.insertPartCheck(new ModelPartCheck(5,question,date,0));
            flag_jump=1;
        }else if(mode==1){
            arrayChoose.set(arryQuestion.indexOf(question),choose);
        }
    }

    @Override
    public void next() {
        nextQuestion();
    }

    @Override
    public void back() {
        backQuestion();
    }


    @Override
    public void nextQuestion() {
        Bundle bundle = new Bundle();
        if ((mode == 0 || mode==2) && flag_jump==1 ) {
            if(mode==0 && key==0) {
                SqlitePart5 sqlitePart5 = new SqlitePart5();
                data = sqlitePart5.randomPart5(1);
            }else if(mode==0 & key !=0){
                if(index==data.length-1)index=0;
                else index++;
            }
            else {
                SqlitePart5 sqlitePart5 = new SqlitePart5();
                data = sqlitePart5.randomPart5Subject(key,1);
            }

            bundle.putSerializable("data",data[index]);
            bundle.putInt("mode",mode);
            bundle.putInt("question",data[index].getId());
            bundle.putString("select","Not choose");
            bundle.putInt("submit",issubmit);
            if(key==0)flag_jump = 0;

            ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction().addToBackStack(null).remove(fragmentPart5).commit();
            fragmentPart5 = new FragmentPart5();
            fragmentPart5.addIResult(this);
            fragmentPart5.setArguments(bundle);
            ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction().addToBackStack(null).add(mFrameLayout.getId(), fragmentPart5).commit();
        }else if(mode==1){

            if(index==data.length-1)index=0;
            else index++;
            bundle.putSerializable("data",data[index]);
            bundle.putInt("mode",mode);
            bundle.putInt("question",data[index].getId());
            bundle.putString("select",arrayChoose.get(index));
            bundle.putInt("submit",issubmit);

            ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction().addToBackStack(null).remove(fragmentPart5).commit();
            fragmentPart5 = new FragmentPart5();
            fragmentPart5.addIResult(this);
            fragmentPart5.setArguments(bundle);
            ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction().addToBackStack(null).add(mFrameLayout.getId(), fragmentPart5).commit();

        }
    }

    @Override
    public void backQuestion() {
        Bundle bundle = new Bundle();
            if (mode == 1 ||(mode==0 && key!=-1)) {
            if (index == 0) index = data.length-1;
            else index--;
            bundle.putSerializable("data", data[index]);
            bundle.putInt("mode", mode);
            bundle.putInt("question", data[index].getId());
            bundle.putString("select", arrayChoose.get(index));
            ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction().addToBackStack(null).remove(fragmentPart5).commit();
            fragmentPart5 = new FragmentPart5();
            fragmentPart5.addIResult(this);
            fragmentPart5.setArguments(bundle);
            ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction().addToBackStack(null).add(mFrameLayout.getId(), fragmentPart5).commit();

        }

    }

    @Override
    public void setUpContent(int contentLayoutId) {
        setNavigation();
        getData();
        fragmentPart5 = new FragmentPart5();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",data[index]);
        bundle.putInt("mode",mode);
        bundle.putInt("question",data[index].getId());
        bundle.putString("select","Not choose");
        bundle.putInt("submit",issubmit);
        fragmentPart5.setArguments(bundle);
        fragmentPart5.addIResult(this);
        ((AppCompatActivity)getContext()).getSupportFragmentManager().beginTransaction().addToBackStack(null).add(contentLayoutId,fragmentPart5).commit();

    }

    private void setNavigation() {
        setItemOnClick(this);
        bottomNavi.setTitles(R.array.title_menu);
        bottomNavi.setIcons(R.array.icons);
    }

    @Override
    public void onOpen() {
        bottomNavi.setVisibility(View.VISIBLE);
        mBlurLayout.setVisibility(View.GONE);
        bottomNavi.onOpen();
    }

    @Override
    public void onClose() {
        mBlurLayout.setVisibility(View.GONE);

        bottomNavi.onClose();
    }


    @Override
    public void onTopItemClicked(int index) {

    }

    @Override
    public void onBottomItemClicked(int index) {
            if(index==0){
                if(mode==0 || mode==2)return;
                else if (mode==1){
                    if(arrayChoose.contains("Not choose")&& issubmit==0){
                        /*
                        chua lam xong khong cho submmit
                         */
                        return;
                    }else issubmit=1;
                }
            }
        if(index==1) {

            if (fragmentsubmit != null) {

                ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction().remove(fragmentsubmit).commit();
            }
            if (mBlurLayout.getVisibility() == View.GONE) {
                mBlurLayout.setVisibility(View.VISIBLE);
                fragmentsubmit = new FragmentSummary();
                fragmentsubmit.setItemClick(this);
                Bundle bundle = new Bundle();
                bundle.putIntegerArrayList("question", arryQuestion);
                bundle.putStringArrayList("choose", arrayChoose);
                bundle.putStringArrayList("result", arrayResult);
                bundle.putInt("submit", issubmit);
                if(mode==0)bundle.putInt("mode",1);
                else if(issubmit ==1){
                    bundle.putInt("mode",1);
                }else bundle.putInt("mode",0);

                fragmentsubmit.setArguments(bundle);
                ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.scale_submit, R.anim.scale_submit_out).add(mBlurLayout.getId(), fragmentsubmit).commit();
            }else{
                mBlurLayout.setVisibility(View.GONE);
                onClose();
            }
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void getData(){
        Bundle bundle = getArguments();
        if(bundle!=null){
            mode = bundle.getInt("mode");
            key = bundle.getInt("key");

            if(mode==0){
                if(key==0) {
                    arryQuestion = new ArrayList<>();
                    arrayChoose = new ArrayList<>();
                    arrayResult = new ArrayList<>();
                    flag_jump = 0;
                    SqlitePart5 sqlite = new SqlitePart5();
                    data = sqlite.randomPart5(1);
                }else if(key==1){
                    SqlitePart5 sqlitePart5 = new SqlitePart5();
                    data = sqlitePart5.searchPart5Check();
                    arryQuestion = new ArrayList<>();
                    for(int i =0;i<data.length;i++){
                        arrayChoose.add("Not Choose");
                        arryQuestion.add(data[i].getId());
                        arrayResult.add(data[i].getResult());
                    }
                    index = bundle.getInt("position");
                    flag_jump=1;
                }else if (key==2){
                    SqlitePart5 sqlitePart5 = new SqlitePart5();
                    // favorite
                    data = sqlitePart5.searchPart5Check();
                    arryQuestion = new ArrayList<>();
                    for(int i =0;i<data.length;i++){
                        arrayChoose.add("Not Choose");
                        arryQuestion.add(data[i].getId());
                    }
                    index = bundle.getInt("position");
                    flag_jump=1;
                }
            }else if(mode ==1){
                if(key==0) {
                    SqlitePart5 sqlite = new SqlitePart5();
                    data = sqlite.randomPart5(40);
                    for (int i = 0; i < 40; i++) {
                        arryQuestion.add(data[i].getId());
                        arrayChoose.add("Not choose");
                        arrayResult.add(data[i].getResult());
                    }
                    flag_jump = 1;
                }else if(key==1){
                    arrayChoose = bundle.getStringArrayList("choose");
                    ArrayList<String>quess = bundle.getStringArrayList("question");
                    for(int i=0;i<quess.size();i++){
                        arryQuestion.add(Integer.valueOf(quess.get(i)));

                    }
                    SqlitePart5 sqlite = new SqlitePart5();
                    data=new ModelPart5[arryQuestion.size()];;
                    for(int i =0;i<arryQuestion.size();i++){
                        ModelPart5 modelPart5[] = sqlite.searchPart5Id(arryQuestion.get(i));
                        data[i]=modelPart5[0];
                        arrayResult.add(modelPart5[0].getResult());
                    }

                    index= bundle.getInt("begin");

                }
            }else if(mode==2){
                mode=0;
                arryQuestion = new ArrayList<>();
                arrayChoose = new ArrayList<>();
                arrayResult = new ArrayList<>();
                flag_jump=0;
                SqlitePart5 sqlite = new SqlitePart5();
                int subject = bundle.getInt("subject");
                data =sqlite.randomPart5Subject(subject,1);

            }
        }

    }


    @Override
    public void funIteamClick(int question) {
        Bundle bundle = new Bundle();
        if(mode==0){
            SqlitePart5 sqlitePart5 = new SqlitePart5();
            data = sqlitePart5.searchPart5Id(question);
            bundle.putSerializable("data",data[0]);
            bundle.putInt("mode",-1);
            bundle.putInt("question",data[0].getId());
            bundle.putString("select",arrayChoose.get(arryQuestion.indexOf(question)));
            flag_jump = 1;
            onClose();
            ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction().addToBackStack(null).remove(fragmentPart5).commit();
            fragmentPart5 = new FragmentPart5();
            fragmentPart5.setFlagFigure(1);
            fragmentPart5.showExplan();
            fragmentPart5.addIResult(this);
            fragmentPart5.setArguments(bundle);
            ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction().addToBackStack(null).add(mFrameLayout.getId(), fragmentPart5).commit();
        }else if(mode==1){
            index= arryQuestion.indexOf(question);
            bundle.putSerializable("data",data[index]);
            bundle.putInt("mode",mode);
            bundle.putInt("question",data[index].getId());
            bundle.putString("select",arrayChoose.get(arryQuestion.indexOf(question)));
            onClose();
            ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction().addToBackStack(null).remove(fragmentPart5).commit();
            fragmentPart5 = new FragmentPart5();
            fragmentPart5.addIResult(this);
            fragmentPart5.setArguments(bundle);
            ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction().addToBackStack(null).add(mFrameLayout.getId(), fragmentPart5).commit();
        }
    }

    public class SendAsynTask extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... params) {
            try {
                URL url = new URL("Http://vntoeic.com/api/v1/part5/result/"+params[0]); //Enter URL here
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
                httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
                httpURLConnection.connect();

                JSONObject jsonObject = new JSONObject();


                jsonObject.put("chosen", params[1]);

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(jsonObject.toString());
                wr.flush();
                wr.close();
                if(httpURLConnection.getResponseCode()==200){
                    System.out.println("oke");

                }else{
                    System.out.println(httpURLConnection.getResponseMessage());
                    System.out.println(httpURLConnection.getResponseCode());
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }



}
