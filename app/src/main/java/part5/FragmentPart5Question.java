package part5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.MainActivity;
import com.vntoeic.bkteam.vntoeicpro.R;

import model.ModelPart5;

/**
 * Created by dainguyen on 6/2/17.
 */

public class FragmentPart5Question extends Fragment {
    private ModelPart5 data;
    private TextView text_question;
    private TextView text_a;
    private TextView text_b;
    private TextView text_c;
    private TextView text_d;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle =getArguments();
        data = (ModelPart5) bundle.getSerializable("data");
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
        text_a = (TextView)view.findViewById(R.id.text_a);
        text_a.setTypeface(MainActivity.typeface);
        text_b = (TextView)view.findViewById(R.id.text_b);
        text_b.setTypeface(MainActivity.typeface);
        text_c = (TextView)view.findViewById(R.id.text_c);
        text_c.setTypeface(MainActivity.typeface);
        text_d = (TextView)view.findViewById(R.id.text_d);
        text_d.setTypeface(MainActivity.typeface);

        text_question.setText(data.getQuestion());
        text_a.setText("A. "+data.getA());
        text_b.setText("B. "+data.getB());
        text_c.setText("C. "+data.getC());
        text_d.setText("D ."+data.getD());

    }
}
