package Dictionary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.AdapterWordSearch;
import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;

import model.Example;
import model.Meanings;

/**
 * Created by dainguyen on 5/30/17.
 */

public class AdapterDiction extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private int flag =0;
    private ArrayList<Meanings>data;
    private ArrayList<Example> examples;

    public AdapterDiction(Context context ,int flag ,ArrayList<Meanings>data){
        this.flag = 1;
        this.data = data;
    }

    public AdapterDiction(Context context ,ArrayList<Example>examples){
        this.flag = 0;
        this.examples = examples;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(flag==0){
            view= LayoutInflater.from(context).inflate(R.layout.cell_meaning_dictionary,parent,false);
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.cell_meaning_dictionary,parent,false);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==0){
            setLayoutMeaning(holder,position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return flag;
    }

    @Override
    public int getItemCount() {

       if(flag==0)return data.size();
        else return examples.size();

    }
    public void setLayoutMeaning(RecyclerView.ViewHolder holder,int position){
        MyHoder myHoder = (MyHoder)holder;
    }

    class MyHoder extends RecyclerView.ViewHolder{
        public View view;
        public ImageView img_explan;
        public TextView text_mean;
        public MyHoder(View itemView) {
            super(itemView);
            this.view = itemView;
            img_explan = (ImageView)itemView.findViewById(R.id.img_mean);
            text_mean = (TextView)itemView.findViewById(R.id.text_mean);



        }
    }
}
