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

import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;
import java.util.logging.Handler;

import model.ModelPartFavorite;
import sqlite.SqlitePart5;

/**
 * Created by dainguyen on 6/29/17.
 */

public class Fragment5Control extends Fragment implements FragmentSummary.IClickItemSubm{
    ImageView img_check;
    LinearLayout line_submit;
    LinearLayout line_summary;
    FrameLayout frameLayout;
    RelativeLayout relate_bottom;

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
        frameLayout = (FrameLayout)view.findViewById(R.id.frame_summary);
        relate_bottom = (RelativeLayout)view.findViewById(R.id.relate_bottom);

        if(issumit==1){
            line_submit.setEnabled(false);
            line_submit.setAlpha(0.5f);
        }

        final SqlitePart5 sqlitePart5= new SqlitePart5();
        if(!sqlitePart5.checkPartFavorite(5,idQuestion))img_check.setBackgroundResource(R.mipmap.icon_heart_while);
        else img_check.setBackgroundResource(R.mipmap.icon_heart);

        img_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sqlitePart5.checkPartFavorite(5,idQuestion)){
                    sqlitePart5.insertPartFavorite(new ModelPartFavorite(5,idQuestion,"time"));
                    img_check.setBackgroundResource(R.mipmap.icon_heart);
                }else{
                    sqlitePart5.deletePartFavorite(5,idQuestion);
                    img_check.setBackgroundResource(R.mipmap.icon_heart_while);
                }
            }
        });
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(frameLayout.getChildCount()==0){
                    Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.scale_popup_part5_out);
                    relate_bottom.startAnimation(animation);
                    android.os.Handler handler = new android.os.Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            iPart5Control.removeFragmentControl();
                        }
                    },300);

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

    }

    public void setIPart5Control(IPart5Control iPart5Control){
        this.iPart5Control = iPart5Control;
    }

    @Override
    public void funIteamClick(int question) {
        iPart5Control.changeQuestion(question);
    }

    public interface IPart5Control{
        public void removeFragmentControl();
        public void changeQuestion(int index);
        public void notfySubmit();

    }
}
