package part5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


import model.ModelPartFavorite;
import sqlite.SqlitePart5;
import supportview.ISummaryPart;

/**
 * Created by dainguyen on 6/29/17.
 */

public class Fragment5Control extends Fragment implements ISummaryPart{
    ImageView img_check;
    LinearLayout line_submit;
    LinearLayout line_summary;
    LinearLayout line_back;
    LinearLayout line_feedback;
    FrameLayout frameLayout;
    TableLayout relate_bottom;

    private int idQuestion=0;
    private ArrayList<Integer>question;
    private ArrayList<String>choose;
    private ArrayList<String>result;
    private int mode =0;
    private int issumit=0;

    private IPart5Control iPart5Control;
    FragmentSummary fragmentSummary;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            idQuestion = bundle.getInt("id");
            question = bundle.getIntegerArrayList("question");
            choose = bundle.getStringArrayList("choose");
            result = bundle.getStringArrayList("result");
            mode = bundle.getInt("mode");
            issumit = bundle.getInt("submit");

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_part5_control,container,false);
        setUpLayout(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.scale_popup_part5);
        relate_bottom.startAnimation(animation);
    }


    public void setUpLayout(View view){
        img_check = (ImageView)view.findViewById(R.id.img_check);
        line_submit =(LinearLayout)view.findViewById(R.id.line_submit);
        line_summary= (LinearLayout)view.findViewById(R.id.line_summary);
        line_back =(LinearLayout)view.findViewById(R.id.line_back);
        line_feedback= (LinearLayout)view.findViewById(R.id.line_feedback);
        frameLayout = (FrameLayout)view.findViewById(R.id.frame_summary);
        relate_bottom = (TableLayout) view.findViewById(R.id.relate_bottom);

        if(issumit==1){
            line_submit.setEnabled(false);
            line_submit.setAlpha(0.5f);
        }

        final SqlitePart5 sqlitePart5= new SqlitePart5();
        if(!sqlitePart5.checkPartFavorite(5,idQuestion))img_check.setImageResource(R.mipmap.icon_heart_while);
        else img_check.setImageResource(R.mipmap.icon_heart);

        img_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sqlitePart5.checkPartFavorite(5,idQuestion)){
                    DateFormat df = new SimpleDateFormat("EEE d MMM yyyy, HH:mm:ss");
                    String date = df.format(Calendar.getInstance().getTime());
                    sqlitePart5.insertPartFavorite(new ModelPartFavorite(5,idQuestion,date));
                    img_check.setImageResource(R.mipmap.icon_heart);
                }else{
                    sqlitePart5.deletePartFavorite(5,idQuestion);
                    img_check.setImageResource(R.mipmap.icon_heart_while);
                }
            }
        });
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(frameLayout.getChildCount()==0){
                    Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.scale_popup_part5_out);
                    relate_bottom.startAnimation(animation);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            iPart5Control.removeFragmentControl();

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            }
        });

        line_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(frameLayout.getChildCount()==0){
                    fragmentSummary= new FragmentSummary();
                    Bundle bundle = new Bundle();
                    bundle.putIntegerArrayList("question", question);
                    bundle.putStringArrayList("choose", choose);
                    bundle.putStringArrayList("result", result);
                    bundle.putInt("mode",mode);
                    bundle.putInt("issubmit",issumit);
                    fragmentSummary.setArguments(bundle);
                    fragmentSummary.setItemClick(Fragment5Control.this);
                    getChildFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.scale_submit,R.anim.scale_submit_out).add(frameLayout.getId(),fragmentSummary).commit();
                }else {
                    getChildFragmentManager().beginTransaction().addToBackStack(null).remove(fragmentSummary).commit();

                }
            }
        });

        line_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPart5Control.notfySubmit();
            }
        });

        line_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPart5Control.back();
            }
        });

        line_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPart5Control.showFeedback();
            }
        });

    }

    public void setIPart5Control(IPart5Control iPart5Control){
        this.iPart5Control = iPart5Control;
    }

    @Override
    public void funItemClick(int passage ,int question) {
        iPart5Control.changeQuestion(question);
    }


    public interface IPart5Control{
        public void removeFragmentControl();
        public void changeQuestion(int index);
        public void notfySubmit();
        public void back();
        public void showFeedback();

    }
}
