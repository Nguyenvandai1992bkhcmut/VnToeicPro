package dictionary;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.AdapterWordSearch;
import com.vntoeic.bkteam.vntoeicpro.FragmentAppBarMain;
import com.vntoeic.bkteam.vntoeicpro.MainActivity;
import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.Timer;
import java.util.TimerTask;

import model.Dictionary;
import model.Example;
import sqlite.SqliteDictionary;

/**
 * Created by dainguyen on 5/30/17.
 */

public class DictionaryActivity extends AppCompatActivity implements AdapterWordSearch.onClickItem {
    private Dictionary dictionary;
    private ImageView img_search;
    private EditText editText;
    private TextView img_icon;
    private ImageView img_back;
    private FrameLayout frameLayout;
    InputMethodManager imm;
    private RecyclerView recyclerView;
    private RelativeLayout relativeLayout;
    private TextView text_bottom;
    int flag =0;
    float tb_y=0;
    float height=0;
    int rootHeight;
    private PopupWindow popupWindow;
    private Timer timer = new Timer();
    private FragmentDictionary fragmentDictionary;
    private FragMentHistory fragMentHistory;
    int is_dic=1;


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
        DictionaryActivity.this.overridePendingTransition(R.anim.right_left_in , R.anim.right_left_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        getData();
        setUpLayout();
    }
    public void getData(){
        Bundle bundle=getIntent().getExtras();
        if(bundle!= null){
            dictionary = (Dictionary) bundle.getSerializable("word");
            SqliteDictionary sqliteDictionary = new SqliteDictionary();
            sqliteDictionary.updateHistory(dictionary.getId(),1);
        }
    }



    public void setUpLayout(){
        rootHeight = getApplicationContext().getResources().getDisplayMetrics().heightPixels;
        imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        img_back = (ImageView)findViewById(R.id.img_back);
        relativeLayout = (RelativeLayout)findViewById(R.id.relative_top);
        frameLayout = (FrameLayout)findViewById(R.id.frame_dic);
        text_bottom = (TextView)findViewById(R.id.text_bottom);
        img_search = (ImageView)findViewById(R.id.img_search);
        editText=(EditText)findViewById(R.id.edit_search);
        img_icon = (TextView)findViewById(R.id.img_icon);
        fragMentHistory = new FragMentHistory();
        fragmentDictionary = new FragmentDictionary();
        Bundle bundle = new Bundle();
        bundle.putSerializable("word",dictionary);
        fragmentDictionary.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame_dic,fragmentDictionary).commit();

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

                System.out.println(s.toString());
                if(s.toString().length()==0)return;
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setContentRecycle(recyclerView,s.toString());
                            }
                        });
                    }
                },DELAY);
            }
        };
        editText.addTextChangedListener(watcher);

        ((TextView)findViewById(R.id.text_dictionary)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(is_dic==0){
                    if(frameLayout.getChildCount()==1){
                        getSupportFragmentManager().beginTransaction().remove(fragMentHistory).commit();
                        ( (TextView)findViewById(R.id.text_dictionary)).setAlpha(1f);
                        ( (TextView)findViewById(R.id.text_dictionary)).setTextColor(getResources().getColor(R.color.text_bar));
                        ( (TextView)findViewById(R.id.text_history)).setAlpha(0.6f);
                        ( (TextView)findViewById(R.id.text_history)).setTextColor(Color.WHITE);
                    }
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.right_left_in,R.anim.right_left_out).add(R.id.frame_dic,fragmentDictionary).commit();
                    is_dic=1;
                }
            }
        });
        ((TextView)findViewById(R.id.text_history)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(is_dic==1){
                    if(frameLayout.getChildCount()==1){
                        getSupportFragmentManager().beginTransaction().remove(fragmentDictionary).commit();
                        ( (TextView)findViewById(R.id.text_dictionary)).setAlpha(0.6f);
                        ( (TextView)findViewById(R.id.text_dictionary)).setTextColor(Color.WHITE);
                        ( (TextView)findViewById(R.id.text_history)).setAlpha(1f);
                        ( (TextView)findViewById(R.id.text_history)).setTextColor(getResources().getColor(R.color.text_bar));
                    }
                    getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.left_to_right_in,R.anim.left_to_right).add(R.id.frame_dic,fragMentHistory).commit();
                    is_dic=0;

                }
            }
        });
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

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        final View view_result = LayoutInflater.from(getApplicationContext()).inflate(R.layout.popup_search, null);
        popupWindow = new PopupWindow(view_result, ViewGroup.LayoutParams.MATCH_PARENT, (int) 0);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);
        MyTimeTask timeTask = new MyTimeTask();
        timer.schedule(timeTask,0,100);

    }
    public void showEdit(){
        editText.setText("");
        editText.setHint("Enter your key");
        editText.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.scale_search);
        editText.startAnimation(animation);
        img_icon.setVisibility(View.INVISIBLE);
        recyclerView =  (RecyclerView)FragmentAppBarMain.popupWindow.getContentView().findViewById(R.id.recycle_search);

    }

    public void hideEdit(){
        editText.setText("");
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.scale_search_out);
        img_icon.setVisibility(View.VISIBLE);
        editText.startAnimation(animation);
        editText.setVisibility(View.GONE);

    }

    public void setContentRecycle(RecyclerView recycle, String key){
        SqliteDictionary sqlite = new SqliteDictionary();
        Dictionary[] data = sqlite.searchSimilar(key);
        AdapterWordSearch adapter = new AdapterWordSearch(this,data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recycle.setLayoutManager(linearLayoutManager);
        recycle.setAdapter(adapter);

    }

    @Override
    public void funonClickItem(Dictionary dictionary) {
        imm.toggleSoftInputFromWindow(img_search.getWindowToken(), 1, 0);
        try {
            getSupportFragmentManager().beginTransaction().remove(fragmentDictionary).commit();
            getSupportFragmentManager().beginTransaction().remove(fragMentHistory).commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            getSupportFragmentManager().beginTransaction().remove(fragMentHistory).commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        fragmentDictionary = new FragmentDictionary();
        Bundle bundle = new Bundle();
        bundle.putSerializable("word",dictionary);
        fragmentDictionary.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame_dic,fragmentDictionary).commit();
        is_dic=1;
        SqliteDictionary sqliteDictionary = new SqliteDictionary();
        sqliteDictionary.updateHistory(dictionary.getId(),1);
        ( (TextView)findViewById(R.id.text_dictionary)).setAlpha(1f);
        ( (TextView)findViewById(R.id.text_history)).setAlpha(0.6f);
    }


    @Override
    public void funClickRemove(Dictionary dictionary, int position) {
        SqliteDictionary sqliteDictionary = new SqliteDictionary();
        sqliteDictionary.updateHistory(dictionary.getId(),0);
        fragMentHistory.removeItemView(position);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    class MyTimeTask extends TimerTask{

        @Override
        public void run() {
            if(tb_y == 0f){
                tb_y =  text_bottom.getY();
            }
            else if(tb_y-text_bottom.getY()>height){
                height =  tb_y - text_bottom.getY();
            }

            if(tb_y-text_bottom.getY()<50 && popupWindow.isShowing()){
                DictionaryActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        popupWindow.dismiss();
                        if(flag==1)hideEdit();
                        flag=0;

                    }
                });

            }else if(tb_y-text_bottom.getY()>100 && !popupWindow.isShowing()){
                DictionaryActivity.this.runOnUiThread(new Runnable() {
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
