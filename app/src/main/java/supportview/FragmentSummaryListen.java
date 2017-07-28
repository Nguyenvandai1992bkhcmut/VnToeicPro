package supportview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;

import model.IListenPart;
import part3.FragmentPart34Question;

/**
 * Created by dainguyen on 7/12/17.
 */

public class FragmentSummaryListen extends Fragment {
    private ArrayList<ArrayList<String>>arrChoose;
    private ArrayList<Integer>arrQuestion;
    private ArrayList<ArrayList<String>>arrResult;
    private ArrayList<Integer>arrSubmit ;

    private int isSubmit =0;
    private int mode=0;
    private int part=0;
    private int index=0;

    private  int countQuestion=0;


    private RecyclerView recyclerView;
    private IPartControlListen iPartControlListen;

    public void setiPartControlListen(IPartControlListen iPartControlListen){
        this.iPartControlListen= iPartControlListen;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    public void getData(){
        Bundle bundle=iPartControlListen.getBunldeSummary();
        if(bundle!=null){
            arrChoose = new ArrayList<>();
            arrResult= new ArrayList<>();
            ArrayList<String> choose = bundle.getStringArrayList("choose");
            ArrayList<String> result = bundle.getStringArrayList("result");
            arrQuestion = bundle.getIntegerArrayList("question");
            arrSubmit = bundle.getIntegerArrayList("arrsubmit");
            isSubmit = bundle.getInt("submit");
            mode = bundle.getInt("mode");
            part = bundle.getInt("part");
            index = bundle.getInt("index");

            for(int i=0;i<choose.size();i++){
                ArrayList<String >ch =new ArrayList<>();
                ArrayList<String>re = new ArrayList<>();
                String arr[] = choose.get(i).split("!");
                String arr1[]= result.get(i).split("!");
                for(int j=0;j<arr.length;j++){
                    ch.add(arr[j]);
                    re.add(arr1[j]);
                }
                arrChoose.add(ch);
                arrResult.add(re);
            }
            countQuestion = arrQuestion.size();
            if(mode==0|| mode==2) {
                countQuestion--;
            }
        }
    }

    public void reloadContent(){
        getData();
        AdapterSummary adapterSummary = new AdapterSummary();
        recyclerView.swapAdapter(adapterSummary,false);
        adapterSummary.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submit,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycle_submit);
        setUpLayout();

        return view;
    }
    public void setUpLayout(){
        AdapterSummary adapterSummary = new AdapterSummary();
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterSummary);

    }


    public class AdapterSummary extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.cell_summary_listening,parent,false);
            Holder holder = new Holder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Holder myholder =(Holder)holder;
            if(part==1 || part==2)myholder.text_question.setText("Question "+String.valueOf(position+1));
            else {
                int count = 0;
                for (int i = 0; i < position; i++) {
                    count += arrChoose.get(i).size();
                }
                myholder.text_question.setText("Question " + String.valueOf(count + 1) + " - " + String.valueOf(count + arrChoose.get(position).size()));
            }
            AdapterChild adapterChild = new AdapterChild(position);
            LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
            myholder.recycler_child.setLayoutManager(manager);
            myholder.recycler_child.setAdapter(adapterChild);
            myholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iPartControlListen.changeQuestion(position);
                }
            });
       
        }

        @Override
        public int getItemCount() {
            return countQuestion;
        }

        public class Holder extends RecyclerView.ViewHolder{
            public TextView text_question;
            public RecyclerView recycler_child;
            public Holder(View itemView) {
                super(itemView);
                text_question =(TextView)itemView.findViewById(R.id.text_ques);
                recycler_child =(RecyclerView)itemView.findViewById(R.id.recycle_child);
            }
        }

        public class AdapterChild extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
            private final int indexQ;
            public AdapterChild(int indexQ){
                this.indexQ = indexQ;
            }
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                ImageView imageView = new ImageView(getContext());
                RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                imageView.setLayoutParams(params);
                return new HoderChild(imageView);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                HoderChild hoderChild = (HoderChild) holder;
                if (arrSubmit.get(indexQ) == 0) {
                    if (arrChoose.get(indexQ).get(position).equals("Not choose")) {
                        ((ImageView) (hoderChild.itemView)).setImageResource(R.mipmap.icon_notchecked);
                    } else {
                        ((ImageView) (hoderChild.itemView)).setImageResource(R.mipmap.icon_checked);
                    }
                } else {
                    if (arrChoose.get(indexQ).get(position).equals(arrResult.get(indexQ).get(position))) {
                        ((ImageView) (hoderChild.itemView)).setImageResource(R.mipmap.icon_true);
                    } else {
                        ((ImageView) (hoderChild.itemView)).setImageResource(R.mipmap.icon_false);
                    }
                }
            }


            @Override
            public int getItemCount() {
                return arrChoose.get(indexQ).size();
            }

            public class HoderChild extends RecyclerView.ViewHolder{

                public HoderChild(View itemView) {
                    super(itemView);

                }
            }
        }
    }
}
