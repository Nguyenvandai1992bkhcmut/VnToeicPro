package supportview;

import android.content.Context;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ViewUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

/**
 * Created by dainguyen on 7/9/17.
 */

public class FragmentFormFeedback extends Fragment {
    private String name =" Nguyen van Dai";
    private String title;
    private String content ;
    private TextView text_name;
    private TextView text_title;
    private EditText edt_content;
    private Button bt_cancel;
    private Button bt_send;
    private IFormFeedBack iFormFeedBack;
    private InputMethodManager imm;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            title = bundle.getString("title");
            content =bundle.getString("content");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_feedback,container,false);

         imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        setUpLayout(view);
        return view;
    }

    public void setUpLayout(final View view){
        imm.showSoftInputFromInputMethod(view.getWindowToken(),0);
        text_name = (TextView)view.findViewById(R.id.text_name);
        text_title =(TextView)view.findViewById(R.id.text_title);
        edt_content= (EditText)view.findViewById(R.id.text_content);
        bt_cancel = (Button)view.findViewById(R.id.bt_cancel);
        bt_send = (Button)view.findViewById(R.id.bt_send);
        text_name.setText(name);
        text_title.setText(title);
        edt_content.setText(content);
        View.OnClickListener onClickListener= new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                iFormFeedBack.notyHideForm();
            }
        };

        bt_cancel.setOnClickListener(onClickListener);

        bt_send.setOnClickListener(onClickListener);
    }
    public void setiFormFeedBack(IFormFeedBack iFormFeedBack){
        this.iFormFeedBack = iFormFeedBack;
    }
    public interface  IFormFeedBack{
        public void notyHideForm();
    }


}
