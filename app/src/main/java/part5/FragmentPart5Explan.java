package part5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.MainActivity;
import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.Dictionary;
import model.ModelPart5;
import model.ModelWord;
import sqlite.AdapterWordPart;
import sqlite.SqlitePart5;

/**
 * Created by dainguyen on 6/2/17.
 */

public class FragmentPart5Explan extends Fragment {
    private LinearLayout line_explan;
    private LinearLayout line_aware;
    private LinearLayout line_unaware;
    private TextView text_explan;
    private RecyclerView recycle_aware;
    private RecyclerView recycle_unawre;
    private ModelPart5 data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle= getArguments();
        if(bundle!=null){
            data = (ModelPart5) bundle.getSerializable("data");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part5_explan,container,false);
        setUpLayout(view);
        return view;
    }

    public void setUpLayout(View view){
        line_explan = (LinearLayout)view.findViewById(R.id.img_explan);
        line_aware = (LinearLayout)view.findViewById(R.id.img_aware);

        text_explan = (TextView)view.findViewById(R.id.text_explan);
        recycle_aware = (RecyclerView)view.findViewById(R.id.recycle_aware_word);



        line_explan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text_explan.getVisibility()==View.GONE){
                    text_explan.setVisibility(View.VISIBLE);
                    text_explan.setTypeface(MainActivity.typeface);
                    text_explan.setText(data.getExplain());

                }else{
                    text_explan.setVisibility(View.GONE);
                }
            }
        });

        line_aware.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recycle_aware.getVisibility() == View.GONE){
                    recycle_aware.setVisibility(View.VISIBLE);
                    SqlitePart5 sqlitePart5 = new SqlitePart5();
                    ModelWord dataword[]=  sqlitePart5.searchWordPart(5,data.getId());
                   List<ModelWord> data1 = new ArrayList<ModelWord>(Arrays.asList(dataword));
                    AdapterWordPart adapter = new AdapterWordPart(getContext(),data1);
                    LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                    recycle_aware.setLayoutManager(manager);
                    recycle_aware.setAdapter(adapter);
                }else{
                    recycle_aware.setVisibility(View.GONE);
                }
            }
        });



    }
}
