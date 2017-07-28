package supportview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import model.ModelPartFavorite;
import sqlite.ManagerPart;
import sqlite.SqlitePart6;

/**
 * Created by dainguyen on 7/2/17.
 */

public class FragmentControl extends Fragment implements ISummaryPart, FragmentFeedBack.IFeedBack, FragmentFormFeedback.IFormFeedBack {
    ImageView img_check;
    LinearLayout line_submit;
    LinearLayout line_summary;
    LinearLayout line_feedback;
    LinearLayout line_back;
    FrameLayout frameLayout;
    TableLayout relate_bottom;

    private int idQuestion=0;
    private ArrayList<String>choose;
    private ArrayList<String>result;
    private ArrayList<Integer>arrsubmit;
    private int mode =0;
    private int issumit=0;
    private int part =0;

    private int is_summary=0;

    private IPartControl iPartControl;
    FragmentSummaryPart fragmentSummary;
    private FragmentFeedBack fragmentFeedBack;
    private FragmentFormFeedback fragmentFormFeedback;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            idQuestion = bundle.getInt("id");
            choose = bundle.getStringArrayList("choose");
            result = bundle.getStringArrayList("result");
            arrsubmit = bundle.getIntegerArrayList("arrsubmit");
            mode = bundle.getInt("mode");
            issumit = bundle.getInt("submit");
            part=  bundle.getInt("part");


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
        line_feedback =(LinearLayout)view.findViewById(R.id.line_feedback);
        line_back = (LinearLayout)view.findViewById(R.id.line_back);
        frameLayout = (FrameLayout)view.findViewById(R.id.frame_summary);
        relate_bottom = (TableLayout)view.findViewById(R.id.relate_bottom);

        if(issumit==1){
            line_submit.setEnabled(false);
            line_submit.setAlpha(0.5f);
        }

        final ManagerPart managerPart= new ManagerPart();
        if(!managerPart.checkPartFavorite(part,idQuestion)){
            img_check.setImageResource(R.mipmap.icon_heart_while);
        }
        else {
            img_check.setImageResource(R.mipmap.icon_heart);
        }

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
                            iPartControl.removeFragmentControl();

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                }
            }
        });


        img_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!managerPart.checkPartFavorite(part,idQuestion)){
                    DateFormat df = new SimpleDateFormat("EEE d MMM yyyy, HH:mm:ss");
                    String date = df.format(Calendar.getInstance().getTime());
                    managerPart.insertPartFavorite(new ModelPartFavorite(part,idQuestion,date));
                    img_check.setImageResource(R.mipmap.icon_heart);
                }else{
                    managerPart.deletePartFavorite(part,idQuestion);
                    img_check.setImageResource(R.mipmap.icon_heart_while);
                }
            }
        });


        line_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(frameLayout.getChildCount()==0){
                    is_summary=1;
                    showSummary();
                    getChildFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.scale_summary_in,R.anim.scale_summary_out).add(frameLayout.getId(),fragmentSummary).commit();
                }else {
                    if(is_summary==1){
                        is_summary=0;
                        getChildFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.scale_summary_in,R.anim.scale_summary_out).remove(fragmentSummary).commit();
                    }
                    else {
                        is_summary=1;
                        showSummary();
                        getChildFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.scale_summary_in,R.anim.scale_summary_out).replace(frameLayout.getId(),fragmentSummary).commit();

                    }

                }
            }
        });

        line_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPartControl.notfySubmit();
            }
        });

        line_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity)(getContext())).onBackPressed();
            }
        });

        line_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(frameLayout.getChildCount()==0){
                    showFeedBack();
                    getChildFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.scale_summary_in,R.anim.scale_summary_out).add(frameLayout.getId(),fragmentFeedBack).commit();
                }else{
                    if(is_summary==0){
                        getChildFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.scale_summary_in,R.anim.scale_summary_out).remove(fragmentFeedBack).commit();
                    }
                    else {
                        showFeedBack();
                        getChildFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.scale_summary_in,R.anim.scale_summary_out).replace(frameLayout.getId(),fragmentFeedBack).commit();
                    }
                }
            }
        });

    }
    public void showSummary(){
        fragmentSummary= new FragmentSummaryPart();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("choose", choose);
        bundle.putStringArrayList("result", result);
        bundle.putInt("submit",issumit);
        bundle.putIntegerArrayList("arrsubmit",arrsubmit);
        bundle.putInt("mode",mode);
        fragmentSummary.setArguments(bundle);
        fragmentSummary.setItemClick(FragmentControl.this);
    }

    public void showFeedBack(){
        is_summary=0;
        fragmentFeedBack = new FragmentFeedBack();
        fragmentFeedBack.setiFeedBack(FragmentControl.this);
    }

    public void setIPartControl(IPartControl iPart6Control){
        this.iPartControl = iPart6Control;
    }

    @Override
    public void funItemClick(int passage ,int position) {
        iPartControl.changeQuestion(passage,position);
    }

    @Override
    public void showFormFeedback(String title, String content) {
        fragmentFormFeedback = new FragmentFormFeedback();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        bundle.putString("content",content);
        fragmentFormFeedback.setArguments(bundle);
        fragmentFormFeedback.setiFormFeedBack(this);
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.scale_popup_part5_out);
        relate_bottom.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                relate_bottom.setVisibility(View.GONE);
                getChildFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.scale_summary_in,R.anim.scale_summary_out).replace(frameLayout.getId(),fragmentFormFeedback).commit();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void notyHideForm() {
        relate_bottom.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.scale_popup_part5);
        relate_bottom.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                getChildFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.scale_summary_in,R.anim.scale_summary_out).remove(fragmentFormFeedback).commit();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }



}
