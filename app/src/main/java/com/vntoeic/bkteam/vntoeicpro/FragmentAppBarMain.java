package com.vntoeic.bkteam.vntoeicpro;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.Typeface;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import Dictionary.*;
import model.Content;
import model.Dictionary;
import part5.Part5Activity;
import sqlite.SqliteDictionary;

/**
 * Created by dainguyen on 5/29/17.
 */

public class FragmentAppBarMain extends Fragment{
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
                    case R.id.relate_part5:
                        runPart5Dictionary();
                }


            }
        };
        ((RelativeLayout)view.findViewById(R.id.relate_part5)).setOnClickListener(onClickListener);


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


}
