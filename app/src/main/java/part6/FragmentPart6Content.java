package part6;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.print.PrintHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import model.ModelPart6;
import supportview.ConvertTagView;

/**
 * Created by dainguyen on 7/2/17.
 */

public class FragmentPart6Content extends Fragment {
    private TextView text_content;
    private ModelPart6 data;
    private int question =0;
    private int orgX;
    private int offsetX;
    private IPart6Manager iPart6Manager;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            data = (ModelPart6) bundle.getSerializable("data");
            question = bundle.getInt("question")+1;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part6_content,container,false);
        setUpLayout(view);
        return view;
    }
    public void setUpLayout(View view){
        text_content = (TextView)view.findViewById(R.id.text_content);
        ConvertTagView convertTagView = new ConvertTagView(getContext(),"Passage " +String.valueOf(question)+data.getContent());
        text_content.setText(convertTagView.getSpannableString());

        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        orgX = (int) event.getX();
                        return true;
                    case MotionEvent.ACTION_UP:
                        offsetX = (int) (event.getX() - orgX);
                        if (offsetX < -150) iPart6Manager.nextQues();
                        else if (offsetX > 150) iPart6Manager.backQues();
                        return true;
                    case MotionEvent.ACTION_POINTER_DOWN:
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:

                }
                return false;
            }
        };
        text_content.setOnTouchListener(onTouchListener);
    }
    public void setIPart6Manager(IPart6Manager iPart6Manager){
        this.iPart6Manager = iPart6Manager;
    }
    public interface IPart6Manager{
        public void nextQues();
        public void backQues();
    }
}
