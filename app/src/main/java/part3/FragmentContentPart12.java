package part3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.vntoeic.bkteam.vntoeicpro.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import model.IListenPart;
import model.ModelPart1;
import supportview.IPartControlListen;

/**
 * Created by dainguyen on 7/13/17.
 */

public class FragmentContentPart12 extends Fragment {
    private IPartControlListen iPartControlListen;

    private IListenPart data;
    private int mode;
    private int isSubmit=0;

    private ImageView img_content;
    private ImageView img_a;
    private ImageView img_b;
    private ImageView img_c;
    private ImageView img_d;
    private ImageView img_line_bot;
    private LinearLayout line_d;
    private LinearLayout line_a;
    private LinearLayout line_b;
    private LinearLayout line_c;
    ArrayList<String> choose;

    private TableLayout tableLayout;


    private int part=0;



    public void setiPartControlListen(IPartControlListen iPartControlListen){
        this.iPartControlListen = iPartControlListen;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    public void getData(){
        Bundle bundle=iPartControlListen.getBunldeQuestion();
        if(bundle!=null){
            data= (IListenPart) bundle.getSerializable("data");
            mode = bundle.getInt("mode");
            isSubmit = bundle.getInt("submit");
            choose = bundle.getStringArrayList("choose");
            part= bundle.getInt("part");
        }
    }

    public void reloadData(){
        getData();
        setUpLayout();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_part1,container,false);
        img_content=(ImageView)view.findViewById(R.id.img_content);
        img_a =(ImageView) view.findViewById(R.id.img_a);
        img_b =(ImageView) view.findViewById(R.id.img_b);
        img_c =(ImageView) view.findViewById(R.id.img_c);
        img_d =(ImageView) view.findViewById(R.id.img_d);
        tableLayout =(TableLayout)view.findViewById(R.id.tab_top);
        img_line_bot =(ImageView)view.findViewById(R.id.img_line_bot);
        line_d = (LinearLayout) view.findViewById(R.id.line_d);
        line_a = (LinearLayout) view.findViewById(R.id.line_a);
        line_b = (LinearLayout) view.findViewById(R.id.line_b);
        line_c = (LinearLayout) view.findViewById(R.id.line_c);

        setUpLayout();
        return view;
    }

    public void setUpLayout(){
        if(part==2){
           line_d.setVisibility(View.GONE);
        }



        if(part==1){
            ((ModelPart1)data).getImage(getContext(),img_content);
        }else if(part==2){
            img_content.setVisibility(View.GONE);
            img_line_bot.setVisibility(View.GONE);

            RelativeLayout.LayoutParams params =new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
            params.addRule(RelativeLayout.CENTER_VERTICAL,RelativeLayout.TRUE);
            tableLayout.setLayoutParams(params);

        }

        setUpEventImageContent();

        if(isSubmit==1){
            showResult();
        }else{
            if(!choose.get(0).equals("Not choose")){
                if(choose.get(0).equals("a"))img_a.setImageResource(R.mipmap.icon_checked);
                else if(choose.get(0).equals("b"))img_b.setImageResource(R.mipmap.icon_checked);
                else if(choose.get(0).equals("c"))img_c.setImageResource(R.mipmap.icon_checked);
                else if(choose.get(0).equals("d"))img_d.setImageResource(R.mipmap.icon_checked);
            }else{
                img_a.setImageResource(R.mipmap.icon_notchecked);
                img_b.setImageResource(R.mipmap.icon_notchecked);
                img_c.setImageResource(R.mipmap.icon_notchecked);
                img_d.setImageResource(R.mipmap.icon_notchecked);

            }
        }
    }

    public void showResult(){
        isSubmit=1;
        img_a.setImageResource(R.mipmap.icon_notchecked);
        img_b.setImageResource(R.mipmap.icon_notchecked);
        img_c.setImageResource(R.mipmap.icon_notchecked);
        img_d.setImageResource(R.mipmap.icon_notchecked);

        if(choose.equals("Not choose")){
            img_a.setImageResource(R.mipmap.icon_false);
            img_b.setImageResource(R.mipmap.icon_false);
            img_c.setImageResource(R.mipmap.icon_false);
            img_d.setImageResource(R.mipmap.icon_false);
        }else{
            if(choose.get(0).equals("a"))img_a.setImageResource(R.mipmap.icon_false);
            else if(choose.get(0).equals("b"))img_b.setImageResource(R.mipmap.icon_false);
            else if(choose.get(0).equals("c"))img_c.setImageResource(R.mipmap.icon_false);
            else if(choose.get(0).equals("d"))img_d.setImageResource(R.mipmap.icon_false);
        }

        if(data.getSol(0).equals("a"))img_a.setImageResource(R.mipmap.icon_true);
        else if(data.getSol(0).equals("b"))img_b.setImageResource(R.mipmap.icon_true);
        else if(data.getSol(0).equals("c"))img_c.setImageResource(R.mipmap.icon_true);
        else if(data.getSol(0).equals("d"))img_d.setImageResource(R.mipmap.icon_true);



    }

    private void setUpEventImageContent() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSubmit==1)return;
                switch (v.getId()){
                    case R.id.line_a:
                        img_a.setImageResource(R.mipmap.icon_checked);
                        img_b.setImageResource(R.mipmap.icon_notchecked);
                        img_c.setImageResource(R.mipmap.icon_notchecked);
                        img_d.setImageResource(R.mipmap.icon_notchecked);
                        choose.set(0,"a");
                        break;
                    case R.id.line_b:
                        img_b.setImageResource(R.mipmap.icon_checked);
                        img_a.setImageResource(R.mipmap.icon_notchecked);
                        img_c.setImageResource(R.mipmap.icon_notchecked);
                        img_d.setImageResource(R.mipmap.icon_notchecked);
                        choose.set(0,"b");
                        break;
                    case R.id.line_c:
                        img_c.setImageResource(R.mipmap.icon_checked);
                        img_a.setImageResource(R.mipmap.icon_notchecked);
                        img_b.setImageResource(R.mipmap.icon_notchecked);
                        img_d.setImageResource(R.mipmap.icon_notchecked);
                        choose.set(0,"c");
                        break;
                    case R.id.line_d:
                        img_d.setImageResource(R.mipmap.icon_checked);
                        img_a.setImageResource(R.mipmap.icon_notchecked);
                        img_c.setImageResource(R.mipmap.icon_notchecked);
                        img_b.setImageResource(R.mipmap.icon_notchecked);
                        choose.set(0,"d");
                        break;
                }
                iPartControlListen.addResult(choose);
            }
        };


        line_a.setOnClickListener(onClickListener);
        line_c.setOnClickListener(onClickListener);
       line_b.setOnClickListener(onClickListener);
        if(part==1)line_d.setOnClickListener(onClickListener);;



    }



}
