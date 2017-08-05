package supportview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.MainActivity;
import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.IDataPart;
import model.ModelPart5;
import model.ModelPart6;
import model.ModelWord;
import sqlite.SqlitePart5;
import sqlite.SqlitePart6;
import supportview.AdapterWordPart;

/**
 * Created by dainguyen on 6/18/17.
 */

public class FragmentPartExplan extends Fragment {

    private LinearLayout line_explan;
    private LinearLayout line_aware;
    private TextView text_explan;
    private RecyclerView recycle_aware;
    private IDataPart data;

    private TextView textNofy;
    private int issubmit =0;
    private int part =0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle= getArguments();
        if(bundle!=null){
            data = (IDataPart) bundle.getSerializable("data");
            issubmit = bundle.getInt("submit");
            part= bundle.getInt("part");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part5_explan,container,false);
        setUpLayout(view);
        return view;
    }

    public void setUpLayout(final View view){
        final int maxheight = getContext().getResources().getDisplayMetrics().heightPixels;

//        if(part==6||part==7){
//           view.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, maxheight/2));
//        }else if(part==3 || part==4 ||){
            view.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
//        }
        line_explan = (LinearLayout)view.findViewById(R.id.img_explan);
        line_aware = (LinearLayout)view.findViewById(R.id.img_aware);
        text_explan = (TextView)view.findViewById(R.id.text_explan);
        recycle_aware = (RecyclerView)view.findViewById(R.id.recycle_aware_word);
        textNofy =(TextView)view.findViewById(R.id.text_notify);
        text_explan.setText(data.getExplan());
        final ModelWord dataword[] = data.getListWord();

        line_aware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (recycle_aware.getVisibility() == View.GONE) {

                    recycle_aware.setVisibility(View.VISIBLE);
                    List<ModelWord> data1 = new ArrayList<ModelWord>(Arrays.asList(dataword));
                    AdapterWordPart adapter = new AdapterWordPart(getContext(), data1);
                    LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    recycle_aware.setLayoutManager(manager);
                    recycle_aware.setAdapter(adapter);

                } else {
                    recycle_aware.setVisibility(View.GONE);
                }

            }
        });
        line_explan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text_explan.getVisibility() == View.GONE) {
                    text_explan.setVisibility(View.VISIBLE);
                    text_explan.setTypeface(MainActivity.typeface);
                    text_explan.setText(data.getExplan());

                } else {
                    text_explan.setVisibility(View.GONE);
                }

            }
        });
        int dk1 = 0;
        int dk2 = 0;
        if (data.getExplan().length() == 0) {
            line_explan.setVisibility(View.GONE);
            text_explan.setVisibility(View.GONE);
            dk1 = 1;
        }

        if (dataword.length == 0) {
            line_aware.setVisibility(View.GONE);
            dk2 = 1;
        }


            if (dk1 == 1 && dk2 == 1) {
                textNofy.setVisibility(View.VISIBLE);
            }else{
                if(issubmit==0) {
                    text_explan.setVisibility(View.GONE);
                    line_explan.setEnabled(false);
                    line_explan.setAlpha(0.5f);
                    line_aware.setEnabled(false);
                    line_aware.setAlpha(0.5f);
                }
        }

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.getArguments().clear();
    }

    public void show(){
        text_explan.setVisibility(View.VISIBLE);
        line_aware.setEnabled(true);
        line_aware.setAlpha(1f);
        line_explan.setEnabled(true);
        line_explan.setAlpha(1f);
    }


}
