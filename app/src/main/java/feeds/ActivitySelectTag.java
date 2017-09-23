package feeds;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import model.PostTag;
import model.User;
import sqlite.ManagerPart;
import supportview.FlowLayout;

/**
 * Created by dainguyen on 9/7/17.
 */

public class ActivitySelectTag extends AppCompatActivity implements AdapterTags.ITag{
    private ArrayList<PostTag>tag_selected;
    private ArrayList<PostTag>tag_used=new ArrayList<>();
    private ArrayList<PostTag>tag_normal=new ArrayList<>();
    private final String LINKTAG="http://vntoeic.xyz/api/v1/tags";

    private EditText edit_search;
    private FlowLayout flowLayout;
    private RecyclerView recycle_use;
    private TextView text_done;
    private ImageView img_plus;
    AdapterTags adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_tags);
        setUpData();
        setUpLayout();
    }

    public void setUpLayout(){
        edit_search =(EditText)findViewById(R.id.edit_search);
        flowLayout =(FlowLayout)findViewById(R.id.flow_tag);
        text_done =(TextView)findViewById(R.id.text_add);
        recycle_use =(RecyclerView)findViewById(R.id.recycle_tag_use);
        img_plus =(ImageView)findViewById(R.id.img_plus);
        img_plus.setVisibility(View.GONE);
        setFlowTag();
        setViewTags(tag_used,tag_normal);

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
                if(s.toString().length()==0){
                    img_plus.setVisibility(View.GONE);
                    setViewTags(tag_used,tag_normal);
                    return;
                }
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               if(!checkHasTag(key)){
                                   img_plus.setVisibility(View.GONE);

                                   ArrayList<PostTag>use = new ArrayList<PostTag>();
                                   ArrayList<PostTag>normal = new ArrayList<PostTag>();
                                   for(int i=0;i<tag_used.size();i++){
                                       if(tag_used.get(i).getTag_title().contains(key))use.add(tag_used.get(i));
                                   }
                                   for(int i=0;i<tag_normal.size();i++){
                                       if(tag_normal.get(i).getTag_title().contains(key))normal.add(tag_normal.get(i));
                                   }
                                   setViewTags(use,normal);
                               }else {
                                   img_plus.setVisibility(View.VISIBLE);
                                   setViewTags(new ArrayList<PostTag>(),new ArrayList<PostTag>());
                               }
                            }
                        });
                    }
                },DELAY);
            }
        };
        edit_search.addTextChangedListener(watcher);
        img_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_search.getText().length()==0)return;
                addTag(new PostTag(-1,edit_search.getText().toString(),0));
            }
        });
        text_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                Bundle result = new Bundle();

                result.putSerializable("selected",tag_selected);
                returnIntent.putExtra("result",result);

                setResult(Activity.RESULT_OK,returnIntent);
                finish();
              //  overridePendingTransition(R.anim.nochange,R.anim.bot_top_out);
            }
        });
    }

    public boolean checkHasTag(String key){

        for(int i=0;i<tag_used.size();i++){
            if(tag_used.get(i).getTag_title().contains(key)){
                return false;
            }
        }
        for(int i=0;i<tag_normal.size();i++){
            if(tag_normal.get(i).getTag_title().contains(key)){
                return false;
            }
        }
        return true;
    }

    public void setViewTags(ArrayList<PostTag>used , ArrayList<PostTag>normal){
        if(adapter==null) {
            adapter = new AdapterTags(getApplicationContext(), used, normal);
            adapter.setITag(this);
            LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            recycle_use.setLayoutManager(manager);
            recycle_use.setAdapter(adapter);
        }else{
            adapter = new AdapterTags(getApplicationContext(), used, normal);
            recycle_use.swapAdapter(adapter,false);
            adapter.notifyDataSetChanged();
        }


    }

    public void setFlowTag(){
        flowLayout.removeAllViews();
        for(int i =0;i<tag_selected.size();i++){
            View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.cell_tag_edit,null,false);
            final TextView text= (TextView) v.findViewById(R.id.text);
            text.setText(tag_selected.get(i).getTag_title());
            flowLayout.addView(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i=0;i<tag_selected.size();i++){
                        if(tag_selected.get(i).getTag_title().equals(text.getText().toString())){
                            tag_selected.remove(i);
                            break;
                        }
                    }
                    setFlowTag();
                }
            });
        }
        if(flowLayout.getChildCount()==0){
            TextView text = new TextView(getApplicationContext());
            text.setText("Select at least 1 tag,max 5 tag");
            text.setTextColor(Color.GRAY);
            flowLayout.addView(text);
        }
    }

    public void setUpData(){
        Bundle bundle = getIntent().getExtras();
        if(bundle!= null){
            tag_selected = (ArrayList<PostTag>) bundle.getSerializable("selected");
            tag_used = (ArrayList<PostTag>) bundle.getSerializable("used");
            tag_normal = (ArrayList<PostTag>) bundle.getSerializable("normal");
        }

    }
    @Override
    public void addTag(PostTag postTag) {
        int f =0;
        for(int i=0;i<tag_selected.size();i++){
            if(tag_selected.get(i).getTag_title().equals(postTag.getTag_title())){
                f=1;
                break;
            }
        }
        if(f==0){
            tag_selected.add(postTag);
            setFlowTag();
        }
    }

}
