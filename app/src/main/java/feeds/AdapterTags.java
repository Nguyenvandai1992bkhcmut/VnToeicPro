package feeds;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;

import model.PostTag;

/**
 * Created by dainguyen on 9/7/17.
 */

public class AdapterTags extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<PostTag>datas;
    private ArrayList<PostTag>data_normal;
    private Context context;
    private ITag iTag;
    public AdapterTags(Context context , ArrayList<PostTag>datas,ArrayList<PostTag>arr){
            this.context = context;
        this.datas = datas;
        this.data_normal = arr;
    }

    public void setITag(ITag iTag){
        this.iTag = iTag;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(viewType==0)view=LayoutInflater.from(context).inflate(R.layout.cell_tag_header,parent,false);
        else view=LayoutInflater.from(context).inflate(R.layout.cell_tag,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Holder myholder =(Holder)holder;
        if(holder.getItemViewType()==0){
            if(datas.size()==0){
                    myholder.textView.setText("Nornal tags");
            }else{
                myholder.textView.setText("Used tags");
            }
        }else{
            int p =0;
            PostTag postTag= null;
            if(datas.size()==0){
                p=position-1;
                postTag=data_normal.get(p);
            }else{
                if(position<=datas.size()){
                    p=position-1;
                    postTag=datas.get(p);
                }else{
                    p=position-2-datas.size();
                    postTag=data_normal.get(p);
                }
            }

            myholder.textView.setText(postTag.getTag_title());
            final PostTag finalPostTag = postTag;
            myholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iTag.addTag(finalPostTag);
                }
            });
        }

    }

    public class Holder extends RecyclerView.ViewHolder{
        public TextView textView;
        public Holder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(datas.size()==0){
            if(position==0)return 0;
            else return 1;
        }else {
            if(position==0||position==(datas.size()+1))return 0;
            else return 1;
        }

    }

    @Override
    public int getItemCount() {
        if(datas.size()==0)return data_normal.size()+1;
        else return datas.size()+data_normal.size()+2;
    }

    public interface ITag{
        public void addTag(PostTag postTag);
    }
}
