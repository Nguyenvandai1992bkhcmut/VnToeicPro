package supportview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;

import model.IDataPart;
import model.ModelPart1;
import model.ModelPart5;
import model.ModelPart6;
import model.ModelPart7;
import model.ModelPartCheck;
import part3.FragmentContent;

import sqlite.ManagerPart;
import sqlite.SqlitePart1;
import sqlite.SqlitePart2;
import sqlite.SqlitePart3;
import sqlite.SqlitePart4;
import sqlite.SqlitePart5;
import sqlite.SqlitePart6;
import sqlite.SqlitePart7;

import static android.R.attr.data;
import static android.R.attr.translateX;

/**
 * Created by dainguyen on 6/11/17.
 */

public class PartReviewActivity extends AppCompatActivity {

    private int isSortId =0;
    private int part;
    private int mode;
    private ImageView img_setting,img_back;
    private PopupWindow popupWindow;
    private FrameLayout frameLayout;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        showReview();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        getData();
        setUpLayout();
    }

    public void getData(){

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            part = bundle.getInt("part");
            mode = bundle.getInt("mode");

        }

    }

    public void setUpLayout(){

        TextView textView = (TextView)findViewById(R.id.text_part);
        textView.setText("Part "+ String.valueOf(part));
        frameLayout =(FrameLayout)findViewById(R.id.frame_content);
        img_setting =(ImageView)findViewById(R.id.img_setting);
        img_back=(ImageView)findViewById(R.id.img_back);
        img_setting.setVisibility(View.VISIBLE);
        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        popupWindow = new PopupWindow(getApplicationContext());
        popupWindow.setAnimationStyle(R.style.PopUpSetting);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.popup_setting, null);
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(true);

        img_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!popupWindow.isShowing()) {
                    popupWindow.showAsDropDown(img_setting);
                    final ImageView image_check_id =(ImageView)popupWindow.getContentView().findViewById(R.id.img_check_id);
                    final ImageView img_check_time =(ImageView)popupWindow.getContentView().findViewById(R.id.img_check_time);
                    if(isSortId==1){
                        image_check_id.setBackgroundResource(R.mipmap.icon_checked);
                        img_check_time.setBackgroundResource(R.mipmap.icon_notchecked);
                    }else{
                        image_check_id.setBackgroundResource(R.mipmap.icon_notchecked);
                        img_check_time.setBackgroundResource(R.mipmap.icon_checked);
                    }

                    View.OnClickListener onClick  = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (v.getId()){
                                case R.id.img_check_id:
                                case R.id.text_check_id:
                                    if(isSortId==0){
                                        isSortId=1;
                                      showReview();
                                        popupWindow.dismiss();
                                    }
                                    break;
                                case R.id.img_check_time:
                                case R.id.text_check_time:
                                    if(isSortId==1){
                                        isSortId=0;
                                        showReview();
                                        popupWindow.dismiss();

                                    }
                                    break;
                            }
                        }
                    };
                    image_check_id.setOnClickListener(onClick);
                    img_check_time.setOnClickListener(onClick);
                    ((TextView)popupWindow.getContentView().findViewById(R.id.text_check_id)).setOnClickListener(onClick);
                    ((TextView)popupWindow.getContentView().findViewById(R.id.text_check_time)).setOnClickListener(onClick);

                }else{
                    popupWindow.dismiss();
                }
            }
        });
        showReview();

    }
    public void showReview(){
        FragmentReview fragmentReview = new FragmentReview();
        Bundle bundle = new Bundle();
        bundle.putInt("part",part);
        bundle.putInt("mode",mode);
        bundle.putInt("sort",isSortId);
        fragmentReview.setArguments(bundle);
        if(frameLayout.getChildCount()==0){
            getSupportFragmentManager().beginTransaction().addToBackStack(null).add(frameLayout.getId(),fragmentReview).commit();
        }else{
            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(frameLayout.getId(),fragmentReview).commit();
        }
    }
}
