package part5;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
    private static ModelPart5 data[];
    private static int index=0;
    private static int mode =0;
    private static int key=0;

    private static  int flag_jump=0;
    private static ArrayList<Integer>arryQuestion= new ArrayList<>();
    private static ArrayList<String>arrayChoose= new ArrayList<>();
    private static ArrayList<String>arrayResult = new ArrayList<>();

    public static int issubmit=0;

    @Override
    public  void addResult(int question,  String choose){
        if(mode==0 || mode==2){
            arryQuestion.add(question);
            arrayChoose.add(choose);
            boolean re = false;
            if(data[0].getResult().equals(choose))re= true;
            if(re)arrayResult.add("True  ");
            else arrayResult.add("False");
            fragmentPart5.showResult();
            Part5PractiseAcitvity.updateTextCorrect(re,arryQuestion.size());
            SqlitePart5 sqlitePart5 = new SqlitePart5();
            sqlitePart5.updateTimePart(5,data[0].getId());

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
    public void nextQuestion() {
        Bundle bundle = new Bundle();
        if ((mode == 0 || mode==2) && flag_jump==1 ) {
            if(mode==0) {
                SqlitePart5 sqlitePart5 = new SqlitePart5();
                data = sqlitePart5.randomPart5(1);
            }else {
                SqlitePart5 sqlitePart5 = new SqlitePart5();
                data = sqlitePart5.randomPart5Subject(key,1);
            }

            bundle.putSerializable("data",data[0]);
            bundle.putInt("mode",mode);
            bundle.putInt("question",data[0].getId());
            bundle.putString("select","Not choose");
            bundle.putInt("submit",issubmit);
            flag_jump = 0;

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
        if (mode == 1) {
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
        bundle.putSerializable("data",data[0]);
        bundle.putInt("mode",mode);
        bundle.putInt("question",data[0].getId());
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
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(),R.style.ActivityDialog);

                        // set title
                       // .setMessage("Question " + String.valueOf(arryQuestion.get(arrayChoose.indexOf("Not choose")+1)))

                        // set dialog message
                        alertDialogBuilder
                                .setView(R.layout.dialog_submit)
                                .setCancelable(false);


                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,300);
                        // show it
                        alertDialog.show();
                        return;
                    }else issubmit=1;
                }
            }
        if(index==1) {
            if (fragmentsubmit != null) {

                ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction().remove(fragmentsubmit).commit();
            }
            mBlurLayout.setVisibility(View.VISIBLE);
            fragmentsubmit = new FragmentSummary();
            fragmentsubmit.setItemClick(this);
            Bundle bundle = new Bundle();
            bundle.putIntegerArrayList("question", arryQuestion);
            bundle.putStringArrayList("choose", arrayChoose);
            bundle.putStringArrayList("result", arrayResult);
            bundle.putInt("submit", issubmit);

            fragmentsubmit.setArguments(bundle);
            ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.scale_submit, R.anim.scale_submit_out).add(mBlurLayout.getId(), fragmentsubmit).commit();
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
                arryQuestion = new ArrayList<>();
                arrayChoose = new ArrayList<>();
                arrayResult = new ArrayList<>();
                flag_jump=0;
                SqlitePart5 sqlite = new SqlitePart5();
                data =sqlite.randomPart5(1);
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
                    SqlitePart5 sqlite = new SqlitePart5();
                    data = sqlite.searchPart5Favorite();
                    for (int i = 0; i <data.length; i++) {
                        arryQuestion.add(data[i].getId());
                        arrayChoose.add("Not choose");
                        arrayResult.add(data[i].getResult());
                    }
                    flag_jump = 1;
                }else if(key==2){
                    /*
                    History
                     */
                }
            }else if(mode==2){
                mode=0;
                arryQuestion = new ArrayList<>();
                arrayChoose = new ArrayList<>();
                arrayResult = new ArrayList<>();
                flag_jump=0;
                SqlitePart5 sqlite = new SqlitePart5();
                data =sqlite.randomPart5Subject(key,1);

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
}
