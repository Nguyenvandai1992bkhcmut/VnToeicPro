package com.vntoeic.bkteam.vntoeicpro;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import dictionary.*;
import model.Content;
import model.Dictionary;
import model.ModelPart7;
import part3.ListeningActivity;
import part5.Part5Activity;
import part7.Part67Activity;
import sqlite.SqliteDictionary;
import sqlite.SqlitePart7;
import supportview.DowloadTask;
import supportview.PartPractiseAcitvity;

/**
 * Created by dainguyen on 5/29/17.
 */

public class FragmentAppBarMain extends Fragment implements DowloadTask.IDowload{
    static Context context;
    static TextView img_icon;
    static ImageView img_menu;
    static EditText editText;
    public static TextView text_bottom;
    RecyclerView recyclerView;
    ImageView img_search;
    private static float tb_y = 0;
    private static int flag = 0;
    private static float height=0;
    private static int rootHeight;
    public static PopupWindow popupWindow;
    public static  RelativeLayout relativeLayout;
    InputMethodManager imm;
    public static Timer timer = new Timer();


    private int flag_test_read = 0;
    private Bundle bundle= null;

    private  Dialog dialog;
    WindowManager.LayoutParams params;


    private Dialog dialog_dowload;
    private ArrayList<Boolean>arrdowload;
    private int clickPart7=0;

    private Boolean checkInternet;
    @Override
    public void onAttach(Context context1) {
        context = context1;
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_bar_main, container, false);
        setUplayout(view);
        return view;
    }
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    public void setUplayout(View view) {

        relativeLayout = (RelativeLayout)view.findViewById(R.id.relative_top);
        img_icon = (TextView) view.findViewById(R.id.img_icon);
        img_icon.setTypeface(MainActivity.typeface);
        img_menu = (ImageView) view.findViewById(R.id.img_menu);
        final View view_result = LayoutInflater.from(getContext()).inflate(R.layout.popup_search, null);
        editText = (EditText) view.findViewById(R.id.edit_search);
        editText.setTypeface(MainActivity.typeface);
        img_search = (ImageView)view.findViewById(R.id.img_search);

        popupWindow = new PopupWindow(view_result, ViewGroup.LayoutParams.MATCH_PARENT, (int) 0);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);

        imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setVisibility(View.VISIBLE);
                editText.requestFocus();
                if (flag == 0) {
                    flag=1;
                    showEdit();
                    imm.toggleSoftInputFromWindow(img_search.getWindowToken(), 1, 0);
                } else {
                    flag=0;
                    hideEdit();
                    imm.toggleSoftInputFromWindow(img_search.getWindowToken(), 1, 0);

                }

            }
        });

        TextWatcher watcher = new TextWatcher() {
            final  int DELAY =200;
            Timer timer;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println(s.toString());
                if(timer!=null){
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(final Editable s) {
                timer = new Timer();
                final String key = s.toString();
                System.out.println(s.toString());
                if(s.toString().length()==0)return;
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setContentRecycle(recyclerView,key);

                            }
                        });
                    }
                },DELAY);
            }
        };
        editText.addTextChangedListener(watcher);



        text_bottom = (TextView)view.findViewById(R.id.text_bottom);

        rootHeight = getContext().getResources().getDisplayMetrics().heightPixels;


        tb_y = text_bottom.getY();
        MyTimeTask myTimeTask = new MyTimeTask();
        timer.schedule(myTimeTask,0,100);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.relate_dictionary:
                        runDictionaryActivity();
                        break;
                    case R.id.relate_part3:
                        runPartListenning(3);
                        break;
                    case R.id.relate_part1:
                        runPartListenning(1);
                        break;
                    case R.id.relate_part2:
                        runPartListenning(2);
                        break;
                    case R.id.relate_part4:
                        runPartListenning(4);
                        break;
                    case R.id.relate_part5:
                        runPart5Dictionary();
                        break;
                    case R.id.relate_part6:
                        runPart67Dictionary(6);
                        break;
                    case R.id.relate_part7:
                        clickPart7=1;
                        if(prepareDataPart7()){
                            runPart67Dictionary(7);
                        }
                        break;
                    case R.id.relate_readingtest:
                        clickPart7=0;
                        if(prepareDataPart7()){
                            runReadingTestAcitity();
                        }
                        break;

                }


            }
        };
        ((RelativeLayout)view.findViewById(R.id.relate_part5)).setOnClickListener(onClickListener);
        ((RelativeLayout)view.findViewById(R.id.relate_part6)).setOnClickListener(onClickListener);
        ((RelativeLayout)view.findViewById(R.id.relate_part7)).setOnClickListener(onClickListener);
        ((RelativeLayout)view.findViewById(R.id.relate_readingtest)).setOnClickListener(onClickListener);
        ((RelativeLayout)view.findViewById(R.id.relate_part3)).setOnClickListener(onClickListener);
        ((RelativeLayout)view.findViewById(R.id.relate_part4)).setOnClickListener(onClickListener);
        ((RelativeLayout)view.findViewById(R.id.relate_part1)).setOnClickListener(onClickListener);
        ((RelativeLayout)view.findViewById(R.id.relate_part2)).setOnClickListener(onClickListener);


    }

    public Boolean prepareDataPart7() {
        arrdowload = new ArrayList<>();
        SqlitePart7 sqlitePart7 = new SqlitePart7();
        String datapart7[] = sqlitePart7.searchAllImagePart7();
        for (int i = 0; i < datapart7.length; i++) {
            File file = new File(ModelPart7.LINKSRC + datapart7[i] + ".png");
            if (!file.exists()) {
                    arrdowload.add(false);
                    DowloadTask dowloadTask = new DowloadTask();
                    dowloadTask.setiDowload(this);
                    dowloadTask.execute(String.valueOf(i), ModelPart7.LINKIMAGE + datapart7[i], ModelPart7.LINKSRC + datapart7[i] + ".png");

            }else arrdowload.add(true);
        }
        if(arrdowload.contains(false)){
            showDialogDowload();
            return false;
        }else return true;

    }

    @Override
    public void notifySuccess(int numbertask, boolean b, String url, String file) {
        if(!b){
            Toast.makeText(getContext(),"Dowload failure. Please check your internet!",Toast.LENGTH_LONG).show();
        }else {
            arrdowload.set(numbertask,b);
            if (!arrdowload.contains(false)) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog_dowload.dismiss();
                        if(clickPart7==1) runPart67Dictionary(7);
                        else  runReadingTestAcitity();
                        return;
                    }
                });
            }
        }
    }

    public void showDialogDowload(){
        dialog_dowload = new Dialog(getContext(),R.style.ActivityDialog);
        WindowManager.LayoutParams params = dialog_dowload.getWindow().getAttributes();
        int width = (int) (getContext().getResources().getDisplayMetrics().widthPixels*0.8);
        params.width=width;
        params.height=WindowManager.LayoutParams.WRAP_CONTENT;
        dialog_dowload.setCanceledOnTouchOutside(false);
        dialog_dowload.setContentView(R.layout.dialog_dowload);
        dialog_dowload.getWindow().setWindowAnimations(R.style.PauseDialogAnimation);
        dialog_dowload.show();;
        dialog_dowload.getWindow().setAttributes(params);
    }

    public class AsynTaskCheckInternet extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            if(isInternetAvailable()){
                checkInternet = true;
            }else{
                checkInternet = false;
            }
            return null;
        }

        public boolean isInternetAvailable() {
            Runtime runtime = Runtime.getRuntime();
            try {
                Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
                int     exitValue = ipProcess.waitFor();
                return (exitValue == 0);
            }
            catch (IOException e)          { e.printStackTrace(); }
            catch (InterruptedException e) { e.printStackTrace(); }

            return false;
        }
    }



    public void showEdit(){
        editText.setText("");
        editText.setHint("Enter your key");
        editText.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.scale_search);
        editText.startAnimation(animation);
        img_icon.setVisibility(View.INVISIBLE);
    }

    public void hideEdit(){
        editText.setText("");
        editText.setVisibility(View.GONE);
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.scale_search_out);
        img_icon.setVisibility(View.VISIBLE);
        editText.startAnimation(animation);
    }



    public void runDictionaryActivity(){
        Intent intent = new Intent();
    }

    public void runPart5Dictionary(){
        Intent intent = new Intent(getContext(), Part5Activity.class);
        startActivity(intent);
        ((MainActivity)getContext()).overridePendingTransition(R.anim.left_to_right_in , R.anim.left_to_right);


    }
    public void runPartListenning(int part){
        Intent intent = new Intent(getContext(), ListeningActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("part",part);
        intent.putExtras(bundle);
        startActivity(intent);
        ((MainActivity)getContext()).overridePendingTransition(R.anim.left_to_right_in , R.anim.left_to_right);


    }


    public void runReadingTestAcitity() {
        final Intent intent = new Intent(getContext(), PartPractiseAcitvity.class);
        bundle = new Bundle();
        bundle.putInt("mode", 1);
        bundle.putInt("part", 8);
        bundle.putString("title", "Test Reading");
        if(dialog==null) {
            dialog = new Dialog(getContext(), R.style.ActivityDialog);
             params = dialog.getWindow().getAttributes();
            int width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.7);
            params.width = width;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.setContentView(R.layout.dialog_time_part5);
           // dialog.getWindow().setWindowAnimations(R.style.PauseDialogAnimation);
        }
        if (!dialog.isShowing()) {
            dialog.show();
            dialog.getWindow().setAttributes(params);
            CheckReference();
            final SeekBar seekBar = (SeekBar) dialog.findViewById(R.id.seekbar_time);
            seekBar.setMax(20);
            seekBar.setProgress(20);
            final TextView text_continue = (TextView) dialog.findViewById(R.id.text_continue);
            if (flag_test_read == 1) {
                text_continue.setVisibility(View.VISIBLE);
                text_continue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bundle.putInt("key", 1);
                        intent.putExtras(bundle);
                        dialog.dismiss();
                        clearReference();
                        startActivityForResult(intent, 99);
                    }
                });
            }
            final TextView textTime = (TextView) dialog.findViewById(R.id.text_time);
            TextView textStart = (TextView) dialog.findViewById(R.id.text_start);
            textTime.setText("Time: " + String.valueOf(40) + " minutes");
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    textTime.setText("Time: " + String.valueOf(20 + progress) + " minutes");

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            textStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    bundle.putInt("time", 20 + seekBar.getProgress());
                    intent.putExtras(bundle);
                    clearReference();
                    startActivityForResult(intent, 99);
                }
            });
        }
    }
    public void runPart67Dictionary(int part){
        Intent intent = new Intent(getContext(), Part67Activity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("part",part);
        intent.putExtras(bundle);
        startActivity(intent);
        ((MainActivity)getContext()).overridePendingTransition(R.anim.left_to_right_in , R.anim.left_to_right);
    }

    public void setContentRecycle(RecyclerView recycle, String key){
        SqliteDictionary sqlite = new SqliteDictionary();
        Dictionary[] data = sqlite.searchSimilar(key);
        AdapterWordSearch adapter = new AdapterWordSearch((MainActivity)getContext(),data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recycle.setLayoutManager(linearLayoutManager);
        recycle.setAdapter(adapter);

    }


    class MyTimeTask extends TimerTask{

        @Override
        public void run() {
            if(tb_y == 0f){
                tb_y =  text_bottom.getY();
            }
            else if(tb_y-text_bottom.getY()>height){
                height =  tb_y - text_bottom.getY();
            }else if(tb_y-text_bottom.getY()<50 &&  popupWindow.isShowing()){
                ((MainActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        popupWindow.dismiss();
                        if(flag==1)hideEdit();
                        flag=0;
                    }
                });

            }else if(tb_y-text_bottom.getY()>300 && !popupWindow.isShowing() ){
                ((MainActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        popupWindow.setHeight((int)(rootHeight- height- relativeLayout.getHeight()-getStatusBarHeight()));
                        popupWindow.setAnimationStyle(R.style.PauseDialogAnimation);
                        popupWindow.showAsDropDown(relativeLayout, 0,0);
                        recyclerView = (RecyclerView) popupWindow.getContentView().findViewById(R.id.recycle_search);

                    }
                });

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==99 && data!=null)
        {
            clearReference();
            Bundle bundle = data.getExtras();
            if(bundle!=null){
                saveTest(bundle);
            }

        }
    }

    public void clearReference(){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("flagtestread",0);
        editor.commit();

    }

    public void CheckReference(){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        flag_test_read = sharedPref.getInt("flagtestread",0);

        if(flag_test_read==1){
            bundle.putInt("time",sharedPref.getInt("timetestread",1));
            String Squestion = sharedPref.getString("questionpart5","");
            String Schoose = sharedPref.getString("choosepart5","");
            String arr[] = Squestion.split("!");
            String arr1[]= Schoose.split("!");
            ArrayList<Integer>question = new ArrayList<>();
            ArrayList<String>choosen = new ArrayList<>();
            for(int i =0;i<arr.length;i++){
                if(arr[i].length()>0 ){
                    question.add(Integer.valueOf(arr[i]));
                    choosen.add(arr1[i]);
                }
            }
            bundle.putIntegerArrayList("questionpart5",question);
            bundle.putStringArrayList("choosepart5",choosen);

            Squestion = sharedPref.getString("questionpart6","");

            arr = Squestion.split("!");
            question = new ArrayList<>();

            for(int i =0;i<arr.length;i++){
                if(arr[i].length()>0 ){
                    question.add(Integer.valueOf(arr[i]));
                }
            }
            bundle.putIntegerArrayList("questionpart6",question);

            Squestion = sharedPref.getString("questionpart7","");
            arr = Squestion.split("!");
            question = new ArrayList<>();

            for(int i =0;i<arr.length;i++){
                if(arr[i].length()>0 ){
                    question.add(Integer.valueOf(arr[i]));
                }
            }


            bundle.putIntegerArrayList("questionpart7",question);

            bundle.putString("choosepart7",sharedPref.getString("choosepart7",""));
            bundle.putString("choosepart6",sharedPref.getString("choosepart6",""));


            bundle.putInt("indexpart5",sharedPref.getInt("indexpart5",0));
            bundle.putInt("indexpart6",sharedPref.getInt("indexpart6",0));
            bundle.putInt("indexpart7",sharedPref.getInt("indexpart7",0));

            bundle.putInt("parttestread",sharedPref.getInt("parttestread",0));
        }
    }
    public  void saveTest(Bundle bundle){

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("flagtestread",1);
        editor.putInt("timetestread",bundle.getInt("time"));
        editor.putInt("indexpart5",bundle.getInt("indexpart5"));
        editor.putInt("indexpart6",bundle.getInt("indexpart6"));
        editor.putInt("indexpart7",bundle.getInt("indexpart7"));
        editor.putInt("part",bundle.getInt("part"));
        String Squestion ="";
        ArrayList<String>arrchoosePart5= bundle.getStringArrayList("choosepart5");
        ArrayList<Integer>arrQ5= bundle.getIntegerArrayList("questionpart5");
        String Schoose= "";
        for(int i=0;i<arrQ5.size();i++){
            if(i==0){
                Squestion +=String.valueOf(arrQ5.get(i));
                Schoose += arrchoosePart5.get(i);

            }
            else {
                Squestion +="!"+ String.valueOf(arrQ5.get(i)) ;
                Schoose +="!"+ arrchoosePart5.get(i);

            }
        }
        editor.putString("questionpart5",Squestion);

        Squestion ="";
        arrQ5= bundle.getIntegerArrayList("questionpart6");
        for(int i=0;i<arrQ5.size();i++){
            if(i==0){
                Squestion +=String.valueOf(arrQ5.get(i));

            }
            else {
                Squestion +="!"+ String.valueOf(arrQ5.get(i)) ;

            }
        }
        editor.putString("questionpart6",Squestion);

        Squestion ="";
        arrQ5= bundle.getIntegerArrayList("questionpart7");
        for(int i=0;i<arrQ5.size();i++){
            if(i==0){
                Squestion +=String.valueOf(arrQ5.get(i));

            }
            else {
                Squestion +="!"+ String.valueOf(arrQ5.get(i)) ;

            }
        }
        editor.putString("questionpart7",Squestion);

        editor.putString("choosepart7",bundle.getString("choosepart7"));
        editor.putString("choosepart6",bundle.getString("choosepart6"));
        editor.putString("choosepart5",Schoose);
        editor.putInt("parttestread",bundle.getInt("part"));
        editor.commit();

    }


}
