package part5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.hanks.htextview.animatetext.EvaporateText;
import com.hanks.htextview.animatetext.HText;
import com.vntoeic.bkteam.vntoeicpro.MainActivity;
import com.vntoeic.bkteam.vntoeicpro.R;

import model.ModelPart5;

/**
 * Created by dainguyen on 6/2/17.
 */

public class FragmentPart5Question extends Fragment {
    private ModelPart5 data;
    private TextView text_question;
    private HTextView text_a;
    private HTextView text_b;
    private HTextView text_c;
    private HTextView text_d;
    private ImageView image_a;
    private ImageView image_b;
    private ImageView image_c;
    private ImageView image_d;

    String select = "Not choose";

    int question;
    private IAddResult iAddResult;
    private int issubmit =0;

    private int mode;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle =getArguments();
        data = (ModelPart5) bundle.getSerializable("data");
        mode = bundle.getInt("mode");
        question = bundle.getInt("question");
        select = bundle.getString("select");
        issubmit = bundle.getInt("submit");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_part5,container,false);

        setUpLayout(view);
        return view;
    }



    public void setUpLayout(View view) {
        text_question = (TextView)view.findViewById(R.id.text_question);
        text_question.setTypeface(MainActivity.typeface);
        text_a = (HTextView) view.findViewById(R.id.text_a);
        text_a.setTypeface(MainActivity.typeface);
        text_b = (HTextView)view.findViewById(R.id.text_b);
        text_b.setTypeface(MainActivity.typeface);
        text_c = (HTextView)view.findViewById(R.id.text_c);
        text_c.setTypeface(MainActivity.typeface);
        text_d = (HTextView)view.findViewById(R.id.text_d);
        text_d.setTypeface(MainActivity.typeface);

        text_question.setText(data.getQuestion());

        text_a.setAnimateType(HTextViewType.TYPER);
        text_a.animateText(data.getA());

        text_b.setAnimateType(HTextViewType.SCALE);
        text_b.animateText(data.getB());

        text_c.setAnimateType(HTextViewType.SCALE);
        text_c.animateText(data.getC());

        text_d.setAnimateType(HTextViewType.SCALE);
        text_d.animateText(data.getD());

        image_a = (ImageView)view.findViewById(R.id.checkbox_a);
        image_b = (ImageView)view.findViewById(R.id.checkbox_b);
        image_c = (ImageView)view.findViewById(R.id.checkbox_c);
        image_d = (ImageView)view.findViewById(R.id.checkbox_d);


            if(select.equals("a")) image_a.setBackgroundResource(R.mipmap.icon_checked);
            else if(select.equals("b")) image_b.setBackgroundResource(R.mipmap.icon_checked);
            else if(select.equals("c")) image_c.setBackgroundResource(R.mipmap.icon_checked);
            else if(select.equals("d")) image_d.setBackgroundResource(R.mipmap.icon_checked);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode!=-1 && issubmit==0) {
                    switch (v.getId()) {
                        case R.id.checkbox_a:
                        case R.id.text_a:
                        case R.id.line_a:
                            select = "a";
                            image_a.setBackgroundResource(R.mipmap.icon_checked);
                            image_b.setBackgroundResource(R.mipmap.icon_notchecked);
                            image_c.setBackgroundResource(R.mipmap.icon_notchecked);
                            image_d.setBackgroundResource(R.mipmap.icon_notchecked);
                            break;
                        case R.id.line_b:
                        case R.id.text_b:
                        case R.id.checkbox_b:
                            select = "b";
                            image_b.setBackgroundResource(R.mipmap.icon_checked);
                            image_a.setBackgroundResource(R.mipmap.icon_notchecked);
                            image_c.setBackgroundResource(R.mipmap.icon_notchecked);
                            image_d.setBackgroundResource(R.mipmap.icon_notchecked);
                            break;
                        case R.id.line_c:
                        case R.id.text_c:
                        case R.id.checkbox_c:
                            select = "c";
                            image_c.setBackgroundResource(R.mipmap.icon_checked);
                            image_b.setBackgroundResource(R.mipmap.icon_notchecked);
                            image_a.setBackgroundResource(R.mipmap.icon_notchecked);
                            image_d.setBackgroundResource(R.mipmap.icon_notchecked);
                            break;
                        case R.id.line_d:
                        case R.id.text_d:
                        case R.id.checkbox_d:
                            select = "d";
                            image_d.setBackgroundResource(R.mipmap.icon_checked);
                            image_b.setBackgroundResource(R.mipmap.icon_notchecked);
                            image_c.setBackgroundResource(R.mipmap.icon_notchecked);
                            image_a.setBackgroundResource(R.mipmap.icon_notchecked);
                            break;
                    }
                    if(mode ==0) {
                        showResultQuestion();
                        mode = -1;
                        iAddResult.addResult(question, select);
                    }else if(mode==1){
                        iAddResult.addResult(question,select);
                    }
                }
            }
        };

        ((LinearLayout)view.findViewById(R.id.line_a)).setOnClickListener(onClickListener);
        ((LinearLayout)view.findViewById(R.id.line_b)).setOnClickListener(onClickListener);
        ((LinearLayout)view.findViewById(R.id.line_c)).setOnClickListener(onClickListener);
        ((LinearLayout)view.findViewById(R.id.line_d)).setOnClickListener(onClickListener);

    }

    public void showResultQuestion(){
        String sol = data.getResult();
        if(!select.equals("Unchoose")) {
            if (select.equals("a")) image_a.setBackgroundResource(R.mipmap.icon_false);
            else if (select.equals("b")) image_b.setBackgroundResource(R.mipmap.icon_false);
            else if (select.equals("c")) image_c.setBackgroundResource(R.mipmap.icon_false);
            else if (select.equals("d")) image_d.setBackgroundResource(R.mipmap.icon_false);

            if (sol.equals("a")) image_a.setBackgroundResource(R.mipmap.icon_true);
            else if (sol.equals("b")) image_b.setBackgroundResource(R.mipmap.icon_true);
            else if (sol.equals("c")) image_c.setBackgroundResource(R.mipmap.icon_true);
            else if (sol.equals("d")) image_d.setBackgroundResource(R.mipmap.icon_true);
        }

    }

    public interface IAddResult{
        public void addResult(int question, String select);
    }

    public void setiAddResult(IAddResult iAddResult){
        this.iAddResult = iAddResult;
    }

}
