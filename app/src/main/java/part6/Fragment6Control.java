package part6;

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

import model.ModelPartFavorite;
import part5.Fragment5Control;
import part5.FragmentSummary;
import sqlite.SqlitePart5;
import sqlite.SqlitePart6;

/**
 * Created by dainguyen on 7/2/17.
 */

public class Fragment6Control extends Fragment implements FragmentSummaryPart6.ItemClick{
    ImageView img_check;
    LinearLayout line_submit;
    LinearLayout line_summary;
    FrameLayout frameLayout;
    RelativeLayout relate_bottom;

    private int idQuestion=0;
    private ArrayList<Integer> question;
    private ArrayList<String>choose;
    private ArrayList<String>result;
    private int mode =0;
    private int issumit=0;

    private Fragment6Control.IPart6Control iPart6Control;
    FragmentSummaryPart6 fragmentSummary;
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

        final SqlitePart6 sqlitePart6= new SqlitePart6();
        if(!sqlitePart6.checkPartFavorite(6,idQuestion))img_check.setBackgroundResource(R.mipmap.icon_heart_while);
        else img_check.setBackgroundResource(R.mipmap.icon_heart);

        img_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sqlitePart6.checkPartFavorite(6,idQuestion)){
                    sqlitePart6.insertPartFavorite(new ModelPartFavorite(6,idQuestion,"time"));
                    img_check.setBackgroundResource(R.mipmap.icon_heart);
                }else{
                    sqlitePart6.deletePartFavorite(6,idQuestion);
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
                            iPart6Control.removeFragmentControl();
                        }
                    },300);

                }
            }
        });

        line_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(frameLayout.getChildCount()==0){
                    fragmentSummary= new FragmentSummaryPart6();
                    Bundle bundle = new Bundle();
                    bundle.putIntegerArrayList("question", question);
                    bundle.putStringArrayList("choose", choose);
                    bundle.putStringArrayList("result", result);
                    bundle.putInt("submit",issumit);
                    bundle.putInt("mode",mode);
                    fragmentSummary.setArguments(bundle);
                    fragmentSummary.setItemClick(Fragment6Control.this);
                    getChildFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.scale_submit,R.anim.scale_submit_out).add(frameLayout.getId(),fragmentSummary).commit();
                }else {
                    getChildFragmentManager().beginTransaction().addToBackStack(null).remove(fragmentSummary).commit();

                }
            }
        });

        line_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPart6Control.notfySubmit();
            }
        });

    }

    public void setIPart6Control(Fragment6Control.IPart6Control iPart6Control){
        this.iPart6Control = iPart6Control;
    }

    @Override
    public void clickItem(int passage ,int position) {
        iPart6Control.changeQuestion(passage,position);
    }

    public interface IPart6Control{
        public void removeFragmentControl();
        public void changeQuestion(int passage,int index);
        public void notfySubmit();

    }

}
