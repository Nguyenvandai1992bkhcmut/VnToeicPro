package com.vntoeic.bkteam.vntoeicpro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import model.Dictionary;
import sqlite.SqliteDictionary;

/**
 * Created by dainguyen on 5/29/17.
 */

public class FragmentAppBarMain extends Fragment {
    ImageView img_icon;
    ImageView img_menu;
    EditText editText;
    TextView text_bottom;
    RecyclerView recyclerView;
    ImageView img_search;
    private float tb_y = 0;
    private int flag = 0;
    private float height=0;
    private PopupWindow popupWindow;
    InputMethodManager imm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_bar_main, container, false);
        setUplayout(view);
        return view;
    }
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    public void setUplayout(View view) {
        final RelativeLayout relativeLayout = (RelativeLayout)view.findViewById(R.id.relative_top);
        img_icon = (ImageView) view.findViewById(R.id.img_icon);
        img_menu = (ImageView) view.findViewById(R.id.img_menu);
        final View view_result = LayoutInflater.from(getContext()).inflate(R.layout.popup_search, null);
        editText = (EditText) view.findViewById(R.id.edit_search);
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
                    showEdit();
                } else {
                    hideEdit();
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

        final int rootHeight = getContext().getResources().getDisplayMetrics().heightPixels;

        final int title = getStatusBarHeight();
        System.out.println(title);
        text_bottom.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(tb_y == 0f){
                    tb_y =  text_bottom.getY();
                    System.out.println(tb_y);
                }
                else if(tb_y-text_bottom.getY()>height){
                    height =  tb_y - text_bottom.getY();

                } else if(Math.abs(tb_y-text_bottom.getY())<50){
                    if(popupWindow!=null && popupWindow.isShowing()){
                        popupWindow.dismiss();
                        flag=0;
                    }
                }

                if(tb_y-text_bottom.getY()>=100 && flag ==0){
                    popupWindow.setHeight((int)(rootHeight- height- relativeLayout.getHeight()-title));
                    popupWindow.setAnimationStyle(R.style.PauseDialogAnimation);
                    popupWindow.showAsDropDown(relativeLayout, 0,0);
                    recyclerView = (RecyclerView) popupWindow.getContentView().findViewById(R.id.recycle_search);
                    flag=1;
                }
            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.relate_dictionary:
                        runDictionaryActivity();
                }


            }
        };

    }

    public void showEdit(){
        editText.setText("");
        editText.setHint("Enter your key");
        editText.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.scale_search);
        editText.startAnimation(animation);
        img_icon.setVisibility(View.INVISIBLE);
        imm.toggleSoftInputFromWindow(img_search.getWindowToken(), 1, 0);
    }

    public void hideEdit(){
        editText.setText("");
        editText.setVisibility(View.GONE);
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.scale_search_out);
        img_icon.setVisibility(View.VISIBLE);
        editText.startAnimation(animation);
        imm.toggleSoftInputFromWindow(img_search.getWindowToken(), 1, 0);
    }

    public void runDictionaryActivity(){
        Intent intent = new Intent();
    }

    public void setContentRecycle(RecyclerView recycle, String key){
        SqliteDictionary sqlite = new SqliteDictionary();
        Dictionary[] data = sqlite.searchSimilar(key);
        AdapterWordSearch adapter = new AdapterWordSearch(getContext(),data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recycle.setLayoutManager(linearLayoutManager);
        recycle.setAdapter(adapter);

    }


}
