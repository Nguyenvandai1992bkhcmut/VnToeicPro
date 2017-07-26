package testreading;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;

import model.ModelPartFavorite;
import part5.Fragment5Control;
import part5.FragmentSummary;

import part7.FragmentPagerQuestion;
import part7.FragmentPartQuestion;
import sqlite.ManagerPart;
import supportview.FragmentControl;
import supportview.FragmentFeedBack;
import supportview.FragmentFormFeedback;
import supportview.FragmentSummaryPart;
import supportview.IPartControl;
import supportview.ISummaryPart;

/**
 * Created by dainguyen on 7/10/17.
 */

public class FragmentControlTestReading extends Fragment implements FragmentAdapterSummary.IReadTestSummary, ISummaryPart,FragmentFormFeedback.IFormFeedBack,FragmentFeedBack.IFeedBack{
    ImageView img_check;
    LinearLayout line_submit;
    LinearLayout line_summary;
    LinearLayout line_feedback;
    LinearLayout line_back;
    FrameLayout frameLayout;
    TableLayout relate_bottom;
    TableLayout relate_top;

    ImageView img_part5;
    ImageView img_part6;
    ImageView img_part7;


    private ArrayList<Integer> arrQuestionPart5;
    private ArrayList<Integer>arrQuestionPart6;
    private ArrayList<Integer>arrQuestionPart7;

    private ArrayList<String>arrChoosePart5;
    private ArrayList<String>arrChoosePart6;
    private ArrayList<String>arrChoosePart7;

    private ArrayList<String>arrResultPart5;
    private ArrayList<String>arrResultPart6;
    private ArrayList<String>arrResultPart7;

    private int idQuestion=0;
    private int mode=0;
    private int part=0;
    private int issumit;


    private boolean isShowSummary=false;
    private int summary=5;


    private IPartControl iPartControl;


    FragmentAdapterSummary fragmentAdapterSummary;

    FragmentFeedBack fragmentFeedBack;
    FragmentFormFeedback fragmentFormFeedback;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            idQuestion = bundle.getInt("id");
            arrQuestionPart5 = bundle.getIntegerArrayList("questionpart5");
            arrQuestionPart6 = bundle.getIntegerArrayList("questionpart6");
            arrQuestionPart7 = bundle.getIntegerArrayList("questionpart7");

            arrChoosePart5 = bundle.getStringArrayList("choosepart5");
            arrChoosePart6 = bundle.getStringArrayList("choosepart6");
            arrChoosePart7 = bundle.getStringArrayList("choosepart7");

            arrResultPart5= bundle.getStringArrayList("resultpart5");
            arrResultPart6= bundle.getStringArrayList("resultpart6");
            arrResultPart7= bundle.getStringArrayList("resultpart7");
            mode = bundle.getInt("mode");
            issumit = bundle.getInt("submit");
            part=  bundle.getInt("part");
            summary=part;


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
        Animation animation1 = AnimationUtils.loadAnimation(getContext(),R.anim.scale_question_in);
        relate_bottom.startAnimation(animation);
        relate_top.startAnimation(animation1);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                showSummary();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public void setUpLayout(View view) {
        img_check = (ImageView) view.findViewById(R.id.img_check);
        line_submit = (LinearLayout) view.findViewById(R.id.line_submit);
        line_summary = (LinearLayout) view.findViewById(R.id.line_summary);
        line_feedback = (LinearLayout) view.findViewById(R.id.line_feedback);
        line_back = (LinearLayout) view.findViewById(R.id.line_back);
        frameLayout = (FrameLayout) view.findViewById(R.id.frame_summary);
        relate_bottom = (TableLayout) view.findViewById(R.id.relate_bottom);
        relate_top = (TableLayout) view.findViewById(R.id.relate_top);
        img_part5=(ImageView)view.findViewById(R.id.part5);
        img_part6=(ImageView)view.findViewById(R.id.part6);
        img_part7=(ImageView)view.findViewById(R.id.part7);
        relate_top.setVisibility(View.VISIBLE);

        if(part==5){
            img_part5.setImageResource(R.mipmap.icon_part5_attach);
        }else if (part==6){
            img_part6.setImageResource(R.mipmap.icon_part6_attach);
        }else {
            img_part7.setImageResource(R.mipmap.icon_part7_attach);
        }


        final ManagerPart managerPart= new ManagerPart();
        if(!managerPart.checkPartFavorite(part,idQuestion))img_check.setImageResource(R.mipmap.icon_heart_while);
        else img_check.setImageResource(R.mipmap.icon_heart);

        img_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!managerPart.checkPartFavorite(part,idQuestion)){
                    managerPart.insertPartFavorite(new ModelPartFavorite(part,idQuestion,"time"));
                    img_check.setImageResource(R.mipmap.icon_heart);
                }else{
                    managerPart.deletePartFavorite(part,idQuestion);
                    img_check.setImageResource(R.mipmap.icon_heart_while);
                }
            }
        });
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(frameLayout.getChildCount()==0){
                    removeFragment();
                }
            }
        });

        line_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFragment();
            }
        });


        line_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPartControl.notfySubmit();
            }
        });

        line_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShowSummary) {
                    getChildFragmentManager().beginTransaction().addToBackStack(null).remove(fragmentAdapterSummary).commit();
                }else {
                    showSummary();
                }
            }
        });

        line_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(frameLayout.getChildCount()==0){
                    showFeedBack();
                    getChildFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.right_left_in,R.anim.right_left_out).add(frameLayout.getId(),fragmentFeedBack).commit();
                }else{
                    if(isShowSummary==false){
                        getChildFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.right_left_in,R.anim.right_left_out).remove(fragmentFeedBack).commit();
                    }else {
                        showFeedBack();
                        getChildFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.right_left_in,R.anim.right_left_out).replace(frameLayout.getId(),fragmentFeedBack).commit();
                    }
                }
                isShowSummary=false;
            }
        });

        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.part5:
                        if(part!=5){
                            part=5;
                            iPartControl.changePart(5);
                            img_part5.setImageResource(R.mipmap.icon_part5_attach);
                            img_part6.setImageResource(R.mipmap.icon_part6_detach);
                            img_part7.setImageResource(R.mipmap.icon_part7_detach);
                            if(isShowSummary){
                                fragmentAdapterSummary.changePager(0);
                            }
                        }
                        break;
                    case R.id.part6:
                        if(part!=6){
                            part=6;
                            iPartControl.changePart(6);
                            img_part5.setImageResource(R.mipmap.icon_part5_detach);
                            img_part6.setImageResource(R.mipmap.icon_part6_attach);
                            img_part7.setImageResource(R.mipmap.icon_part7_detach);
                            if(isShowSummary){
                                fragmentAdapterSummary.changePager(1);
                            }
                        }
                        break;
                    case R.id.part7:
                        if(part!=7){
                            part=7;
                            iPartControl.changePart(7);
                            img_part5.setImageResource(R.mipmap.icon_part5_detach);
                            img_part6.setImageResource(R.mipmap.icon_part6_detach);
                            img_part7.setImageResource(R.mipmap.icon_part7_attach);
                            if(isShowSummary){
                                fragmentAdapterSummary.changePager(2);
                            }
                        }
                        break;
                }

            }
        };
        img_part5.setOnClickListener(onClickListener);
        img_part6.setOnClickListener(onClickListener);
        img_part7.setOnClickListener(onClickListener);

    }

    public void showFeedBack(){
        fragmentFeedBack = new FragmentFeedBack();
        fragmentFeedBack.setiFeedBack(FragmentControlTestReading.this);
    }

    public void setiPartControl(IPartControl iPartControl){
        this.iPartControl = iPartControl;
    }

    public void showSummary(){
        if(fragmentAdapterSummary==null) {
            fragmentAdapterSummary = new FragmentAdapterSummary();
            fragmentAdapterSummary.setiReadTestSummary(this);
        }
        if(frameLayout.getChildCount()==0){
            getChildFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.right_left_in,R.anim.right_left_out).add(frameLayout.getId(),fragmentAdapterSummary).commit();
        }else{
            getChildFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.right_left_in,R.anim.right_left_out).replace(frameLayout.getId(),fragmentAdapterSummary).commit();
        }
        isShowSummary=true;
    }


    public Bundle setUpSummaryPart5(){

        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("question",arrQuestionPart5);
        bundle.putStringArrayList("choose", arrChoosePart5);
        bundle.putStringArrayList("result", arrResultPart5);
        bundle.putInt("mode",mode);
        bundle.putInt("issubmit",issumit);
        return bundle;
    }

    public Bundle setUpSummaryPart67(int part) {

            Bundle bundle = new Bundle();
            if (part == 6) {
                bundle.putIntegerArrayList("question", arrQuestionPart6);
                bundle.putStringArrayList("choose", arrChoosePart6);
                bundle.putStringArrayList("result", arrResultPart6);
            } else {
                bundle.putIntegerArrayList("question", arrQuestionPart7);
                bundle.putStringArrayList("choose", arrChoosePart7);
                bundle.putStringArrayList("result", arrResultPart7);
            }


            bundle.putInt("submit", issumit);
            bundle.putInt("mode", mode);
            bundle.putInt("part",part);
            bundle.putInt("fulltest",1);
        return bundle;

    }

    @Override
    public void funItemClick(int passage, int position) {

        removeFragment();
        iPartControl.changeQuestion(passage,position);

    }

    @Override
    public void showFormFeedback(String title, String content) {
        fragmentFormFeedback = new FragmentFormFeedback();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        bundle.putString("content",content);
        fragmentFormFeedback.setArguments(bundle);
        fragmentFormFeedback.setiFormFeedBack(FragmentControlTestReading.this);
        Animation animation2 = AnimationUtils.loadAnimation(getContext(),R.anim.scale_popup_part5_out);
        relate_bottom.startAnimation(animation2);
        Animation animation3 = AnimationUtils.loadAnimation(getContext(),R.anim.scale_question_out);
        relate_top.startAnimation(animation3);
        animation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                relate_bottom.setVisibility(View.GONE);
                relate_top.setVisibility(View.GONE);
                getChildFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.right_left_in,R.anim.right_left_out).replace(frameLayout.getId(),fragmentFormFeedback).commit();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @Override
    public void notyHideForm() {
        relate_bottom.setVisibility(View.VISIBLE);
        relate_top.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.scale_popup_part5);
        relate_bottom.startAnimation(animation);
        getChildFragmentManager().beginTransaction().addToBackStack(null).setCustomAnimations(R.anim.right_left_in,R.anim.right_left_out).remove(fragmentFormFeedback).commit();
    }

    public void removeFragment(){
        Animation animation1 = AnimationUtils.loadAnimation(getContext(),R.anim.right_left_out);
        frameLayout.startAnimation(animation1);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Animation animation2 = AnimationUtils.loadAnimation(getContext(),R.anim.scale_popup_part5_out);
                relate_bottom.startAnimation(animation2);
                Animation animation3 = AnimationUtils.loadAnimation(getContext(),R.anim.scale_question_out);
                relate_top.startAnimation(animation3);
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


    @Override
    public Bundle getBunldeFragment(int part) {
        if(part==5)return setUpSummaryPart5();
        else return setUpSummaryPart67(part);

    }

    @Override
    public int getSummary() {
        return summary;
    }

    @Override
    public void changePager(int part) {
        iPartControl.changePart(part);
        img_part5.setImageResource(R.mipmap.icon_part5_detach);
        img_part6.setImageResource(R.mipmap.icon_part6_detach);
        img_part7.setImageResource(R.mipmap.icon_part7_detach);
        if(part==5){

            img_part5.setImageResource(R.mipmap.icon_part5_attach);
        }else if(part==6){
            img_part6.setImageResource(R.mipmap.icon_part6_attach);
        }else{
            img_part7.setImageResource(R.mipmap.icon_part7_attach);
        }
    }
}
