package part5;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.AdapterWordSearch;
import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;

/**
 * Created by dainguyen on 6/3/17.
 */

public class FragmentSummary extends Fragment {
    private ArrayList<Integer>question ;
    private ArrayList<String>choose;
    private ArrayList<String>result;
    private IClickItemSubm iClickItemSubm;

    private int mode =0;
    private int issubmit=0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            question = bundle.getIntegerArrayList("question");
            choose = bundle.getStringArrayList("choose");
            result = bundle.getStringArrayList("result");
            mode =  bundle.getInt("mode");
            issubmit = bundle.getInt("issubmit");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submit,container,false);

        setUpLayout(view);
        return view ;
    }
    public void setUpLayout(View view){
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycle_submit);
        AdapterSubmit adapterSubmit = new AdapterSubmit();
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterSubmit);

    }

    class AdapterSubmit extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.cell_submit,parent,false);
            return new Myholder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Myholder myholder = (Myholder)holder;
            myholder.text_question.setText("Question "+ String.valueOf(position+1));
            myholder.text_choose.setText(choose.get(position));
            if(mode!=1){
                if(choose.get(position).equals("Not choose")){
                    myholder.text_result.setText("........");
                    myholder.text_result.setTextColor(Color.GRAY);
                }else{
                   showTextResult(myholder,position);
                }
            }else  if(mode==1) {
                if (issubmit==1) {
                    showTextResult(myholder,position);
                } else {
                    myholder.text_result.setText("........");
                    myholder.text_result.setTextColor(Color.GRAY);
                }
            }
            myholder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iClickItemSubm.funIteamClick(position);
                }
            });

        }

        public void showTextResult(Myholder myholder, int position){
            if (choose.get(position).equals(result.get(position))) {
                myholder.text_result.setText("True  ");
                myholder.text_result.setTextColor(getResources().getColor(R.color.text_example));
            } else {
                myholder.text_result.setText("Wrong("+result.get(position)+") ");
                myholder.text_result.setTextColor(Color.RED);
            }
        }

        @Override
        public int getItemCount() {

            return question.size();
        }

        class  Myholder extends RecyclerView.ViewHolder{
            public TextView text_question;
            public TextView text_choose;
            public TextView text_result;
            public View view;
            public Myholder(View itemView) {
                super(itemView);
                this.view = itemView;
                this.text_question= (TextView)itemView.findViewById(R.id.text_submit_question);
                this.text_choose= (TextView)itemView.findViewById(R.id.text_submit_choose);
                this.text_result= (TextView)itemView.findViewById(R.id.text_submit_result);
            }
        }
    }
    public void setItemClick(IClickItemSubm itemClick){
        this.iClickItemSubm = itemClick;
    }
    public interface  IClickItemSubm{
        public void funIteamClick(int question);
    }
}
