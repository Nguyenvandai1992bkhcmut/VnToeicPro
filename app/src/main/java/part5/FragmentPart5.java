package part5;

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

import com.vntoeic.bkteam.vntoeicpro.R;

import model.ModelPart5;
import supportview.BaseFragment;

/**
 * Created by dainguyen on 6/2/17.
 */

public class FragmentPart5 extends Fragment {
    FrameLayout frame_question;
    FrameLayout frame_explan;
    private int mode =0;
    FragmentPart5Question frag_question ;
    FragmentPart5Explan frag_explan ;

    private ImageView img_figure;

    private int flag_figure =0;
    private int question = 1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle =getArguments();
        if(bundle != null){
            mode = bundle.getInt("mode");
            question = bundle.getInt("question");
        }else mode=0;
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

       setDataFrame();

       img_figure = (ImageView)view.findViewById(R.id.img_figure);
       img_figure.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                                                            // part6/10/[1-3]
                                                            //part7/10/[1-3]
               if(flag_figure==1) {
                   frag_question.showFigure("Http://vntoeic.com/api/v1/part5/result/"+String.valueOf(question));
                   flag_figure=0;
               }
           }
       });

   }
    public void setFlagFigure(int flag){
        this.flag_figure = flag;
    }

    private void setDataFrame() {
        frag_question.setArguments(getArguments());
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame_question,frag_question).commit();
         frag_explan = new FragmentPart5Explan();
        frag_explan.setArguments(getArguments());
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame_explan,frag_explan).commit();

    }

    public void addIResult(FragmentPart5Question.IAddResult iAddResult){
        frag_question = new FragmentPart5Question();
        frag_question.setiAddResult(iAddResult);
    }

    public void showExplan(){
        frag_explan.setFlag(1);
    }


}
