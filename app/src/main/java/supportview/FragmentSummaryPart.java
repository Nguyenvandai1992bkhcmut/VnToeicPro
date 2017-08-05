package supportview;

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

public class FragmentSummaryPart extends Fragment {
    ArrayList<Integer>arrsubmit;
    ArrayList<ArrayList<String>>choose = new ArrayList<>();
    ArrayList<ArrayList<String>>result = new ArrayList<>();
    int mode=0;
    ISummaryPart itemClick;


    int part;
    int fulltest=0;
    int indexQ=0;
    int isSubmit =0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            arrsubmit = bundle.getIntegerArrayList("arrsubmit");
            ArrayList<String>ch = bundle.getStringArrayList("choose");
            ArrayList<String>re = bundle.getStringArrayList("result");

            part = bundle.getInt("part");
            fulltest = bundle.getInt("fulltest");
            if(fulltest==1){
                if(part==6)indexQ=41;
                else if(part==7)indexQ=53;
            }else indexQ=1;

            //fail
            for(int i =0;i<ch.size();i++){
                String arr[] = ch.get(i).split("!");
                String arr1[] = re.get(i).split("!");
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
            isSubmit = bundle.getInt("submit");
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
    public void setItemClick(ISummaryPart itemClick){
        this.itemClick = itemClick;
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
            return choose.size();
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
            private int count=0;
            public AdapterQuestion(int index){

                this.index = index;
                count=0;
                for(int i=0;i<index;i++){
                    count += choose.get(i).size();
                }
            }
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.cell_submit,parent,false);
                return new HolderQuestion(view);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                HolderQuestion holderQuestion = (HolderQuestion) holder;


                if(fulltest==1)holderQuestion.text_question.setText("Question " + String.valueOf(indexQ+ count +position ));
                else holderQuestion.text_question.setText("Question " + String.valueOf(position+indexQ));


                holderQuestion.text_choose.setText(choose.get(index).get(position));
                if(fulltest==1){
                    if(isSubmit==0){
                        holderQuestion.text_result.setText(".....");
                    } else {
                        setTextResult(holderQuestion, index, position);
                    }
                }else {
                    if (arrsubmit.get(index) == 0) {
                        holderQuestion.text_result.setText(".....");
                    } else {
                        setTextResult(holderQuestion, index, position);
                    }
                }

                holderQuestion.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClick.funItemClick(index,position);
                    }
                });
            }

            public void setTextResult(HolderQuestion holderQuestion,int index, int position){
                holderQuestion.text_result.setText(result.get(index).get(position)); if(result.get(index).get(position).equals(choose.get(index).get(position))) {
                    holderQuestion.text_result.setText(" True ");
                    holderQuestion.text_result.setTextColor(Color.GREEN);
                }else {
                    holderQuestion.text_result.setText("Wrong ");
                    holderQuestion.text_result.setTextColor(Color.RED);
                }
            }

            @Override
            public int getItemCount() {
                return choose.get(index).size();
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
