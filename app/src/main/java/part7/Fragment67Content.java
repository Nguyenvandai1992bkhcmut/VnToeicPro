package part7;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.vntoeic.bkteam.vntoeicpro.R;

import model.IDataPart;
import model.ModelPart7;
import supportview.IContentQuestion;

/**
 * Created by dainguyen on 7/6/17.
 */

public class Fragment67Content extends Fragment {

    private int mode=0;
    private int orgX=0;
    private int offsetX=0;
    private IContentQuestion iContentQuestion;
    private IDataPart data;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            data = (IDataPart) bundle.getSerializable("data");
            mode =bundle.getInt("mode");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part7_content,container,false);
        setUpLayout(view);
        return view;
    }

    public void setUpLayout(View view){

        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        orgX = (int) event.getX();
                        return true;
                    case MotionEvent.ACTION_UP:
                        offsetX = (int) (event.getX() - orgX);
                        if (offsetX < -150) iContentQuestion.nextContent();
                        else if (offsetX > 150) iContentQuestion.backContent();
                        return true;
                    case MotionEvent.ACTION_POINTER_DOWN:
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:

                }
                return false;
            }
        };
        View view_child= data.getViewContent(getContext());
        ScrollView scrollView =(ScrollView)view.findViewById(R.id.scrollView);
        scrollView.addView(view_child);
        view.setOnTouchListener(onTouchListener);
        view_child.setOnTouchListener(onTouchListener);
    }
    public void setiContentQuestion(IContentQuestion iPartContent){
        this.iContentQuestion = iPartContent;
    }

}
