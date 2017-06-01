package sqlite;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import model.ModelPartResult;

/**
 * Created by dainguyen on 6/2/17.
 */

public class AdapterPart extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ModelPartResult[]data;

    public AdapterPart(Context context , ModelPartResult[]data){
        this.context = context;
        this.data = data;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =null;
        if(viewType ==0){
            view = LayoutInflater.from(context).inflate(R.layout.cell_part_select,parent,false);
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.cell_part_subject,parent,false);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position<4){
           Myhoder1 myhoder1 = (Myhoder1)holder;
            if(position==0)myhoder1.imageView.setBackgroundResource(R.mipmap.essentialwordsicon);
            else if(position==0)myhoder1.imageView.setBackgroundResource(R.mipmap.essentialwordsicon);
            else if(position==1)myhoder1.imageView.setBackgroundResource(R.mipmap.essentialwordsicon);
            else if(position==2)myhoder1.imageView.setBackgroundResource(R.mipmap.essentialwordsicon);
            else if(position==3)myhoder1.imageView.setBackgroundResource(R.mipmap.essentialwordsicon);




        }else{
            Myhoder2 myhoder2 = (Myhoder2)holder;
            myhoder2.textView1.setText(data[position].getTitle());
            myhoder2.textView2.setText("Correct" + data[position].getCorrect()+ "/" + data[position].getCount());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position<4)return 0;
        else return 1;
    }


    @Override
    public int getItemCount() {
        return data.length+4;
    }

    public class Myhoder1 extends RecyclerView.ViewHolder{
        public TextView textView;
        public ImageView imageView;
        public Myhoder1(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            imageView = (ImageView)itemView.findViewById(R.id.img);
        }
    }

    public class Myhoder2 extends RecyclerView.ViewHolder{
        public TextView textView1;
        public TextView textView2;
        public Myhoder2(View itemView) {
            super(itemView);
            textView1 = (TextView)itemView.findViewById(R.id.text1);
            textView2 = (TextView)itemView.findViewById(R.id.text2);
        }
    }
}
