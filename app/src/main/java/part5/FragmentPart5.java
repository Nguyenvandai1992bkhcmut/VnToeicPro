package part5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.vntoeic.bkteam.vntoeicpro.R;

import model.ModelPart5;
import supportview.BaseFragment;

/**
 * Created by dainguyen on 6/2/17.
 */

public class FragmentPart5 extends Fragment {
    FrameLayout frame_question;
    FrameLayout frame_explan;
    private int flag =0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part5,container,false);
        setUpLayout(view);
        return view;
    }

   public void setUpLayout(View view){
       frame_question = (FrameLayout)view.findViewById(R.id.frame_question);
       frame_explan= (FrameLayout)view.findViewById(R.id.frame_explan);
       int h = getContext().getResources().getDisplayMetrics().heightPixels;
       RelativeLayout.LayoutParams params ;
       if(flag==0) {
           params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h / 2);
       }else  params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

       frame_question.setLayoutParams(params);
       setDataFrame();

   }

    private void setDataFrame() {
        FragmentPart5Question frag_question = new FragmentPart5Question();
        frag_question.setArguments(getArguments());
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame_question,frag_question).commit();

        FragmentPart5Explan frag_explan = new FragmentPart5Explan();
        frag_explan.setArguments(getArguments());
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame_explan,frag_explan).commit();

    }

    public void setMode(int flag){
        this.flag= flag;
    }
}
