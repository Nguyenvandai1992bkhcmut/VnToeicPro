package part6;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;

/**
 * Created by dainguyen on 6/26/17.
 */

public class FragmentSummaryPart6 extends Fragment {
    ArrayList<Integer>question;
    ArrayList<ArrayList<String>>choose = new ArrayList<>();
    ArrayList<ArrayList<String>>result = new ArrayList<>();
    int mode=0;
    ItemClick itemClick;
    int issubmit=0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            question = bundle.getIntegerArrayList("question");
            ArrayList<String>ch = bundle.getStringArrayList("choose");
            ArrayList<String>re = bundle.getStringArrayList("result");

            for(int i =0;i<ch.size();i++){
                String arr[] = ch.get(i).split("!",3);
                String arr1[] = re.get(i).split("!",3);
                ArrayList<String>arrch =new ArrayList<>();
                ArrayList<String>arrre = new ArrayList<>();
                for(int j=0;j<arr.length;j++){
                    arrch.add(arr[j]);
                    arrre.add(arr1[j]);
                }
                choose.add(arrch);
                result.add(arrre);
            }
            mode =  bundle.getInt("mode");
            issubmit = bundle.getInt("submit");
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        RecycleAdapter adapter = new RecycleAdapter();
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
    public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;

    }
    public interface  ItemClick{
        public void clickItem(int passage,int question);
    }
    public class RecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.cell_summary,parent,false);
            return new Myhoder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Myhoder myhoder = (Myhoder)holder;
            myhoder.text_header.setText("Passage " + String.valueOf(position+1));

            AdapterQuestion adapterQuestion = new AdapterQuestion(position);
            LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
            myhoder.recyclerView.setLayoutManager(manager);
            myhoder.recyclerView.setAdapter(adapterQuestion);
        }

        @Override
        public int getItemCount() {
            return question.size();
        }

        public class Myhoder extends RecyclerView.ViewHolder{
            public TextView text_header;
            public RecyclerView recyclerView;
            public View view;
            public Myhoder(View itemView) {
                super(itemView);
                this.view = itemView;
                this.text_header= (TextView)itemView.findViewById(R.id.text_header);
                this.recyclerView= (RecyclerView) itemView.findViewById(R.id.recycle_summary);
            }
        }

        public class AdapterQuestion extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
            private int index =0;
            public AdapterQuestion(int index){
                this.index = index;
            }
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.cell_submit,parent,false);
                return new HolderQuestion(view);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                HolderQuestion holderQuestion = (HolderQuestion)holder;
                holderQuestion.text_question.setText("Question "+ String.valueOf(position+1));
                holderQuestion.text_choose.setText(choose.get(index).get(position));
                if(mode==0 || mode==2){
                    if(issubmit==0 && index==question.size()-1){
                        holderQuestion.text_result.setText(".....");
                    }else {
                        if(result.get(index).get(position).equals(choose.get(index).get(position))) {
                            holderQuestion.text_result.setText("True");
                            holderQuestion.text_result.setTextColor(Color.GREEN);
                        }else {
                            holderQuestion.text_result.setText("Wrong"+"("+result.get(index).get(position)+")");
                            holderQuestion.text_result.setTextColor(Color.RED);
                        }
                    }
                }else if(mode==1) {
                    if (issubmit == 0) holderQuestion.text_result.setText(".....");
                    else {
                        holderQuestion.text_result.setText(result.get(index).get(position)); if(result.get(index).get(position).equals(choose.get(index).get(position))) {
                            holderQuestion.text_result.setText("True");
                            holderQuestion.text_result.setTextColor(Color.GREEN);
                        }else {
                            holderQuestion.text_result.setText("Wrong"+"("+result.get(index).get(position)+")");
                            holderQuestion.text_result.setTextColor(Color.RED);
                        }
                    }
                }
                holderQuestion.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClick.clickItem(question.get(index),position);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return 3;
            }

            public class HolderQuestion extends RecyclerView.ViewHolder{
                public TextView text_question;
                public TextView text_choose;
                public TextView text_result;
                public HolderQuestion(View itemView) {
                    super(itemView);
                    text_question = (TextView)itemView.findViewById(R.id.text_submit_question);
                    text_choose= (TextView)itemView.findViewById(R.id.text_submit_choose);
                    text_result = (TextView)itemView.findViewById(R.id.text_submit_result);
                }
            }
        }
    }
}
