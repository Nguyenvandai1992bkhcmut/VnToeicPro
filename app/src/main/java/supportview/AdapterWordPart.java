package supportview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import model.ModelFavoriteWord;
import model.ModelWord;
import sqlite.SqliteVocabulary;

/**
 * Created by dainguyen on 6/2/17.
 */

public class AdapterWordPart extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ModelWord>dataWord;
    private int flag;
    private String[]type;
    private String[]mean;
    private Myhoder parent;

    public  AdapterWordPart(Context context, List<ModelWord>dataAware){
        this.context = context;
        this.flag = 0;
        this.dataWord = dataAware;

    }

    public AdapterWordPart(Context  context,Myhoder parent ,String[]type , String[]mean){
        this.context = context;
        this.flag =1;
        this.type = type;
        this.mean = mean;
        this.parent = parent;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (flag == 1) {
            View view = LayoutInflater.from(context).inflate(R.layout.cell_meaning_word_part, parent, false);
            return new Myhoder1(view);
        } else{
            View view = LayoutInflater.from(context).inflate(R.layout.cell_word_part, parent, false);
            return new Myhoder(view);
     }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       if(flag==0){
           setLayoutHoder(holder,position);
       }else {
           setUpLayoutHoder2(holder , position);
       }
    }

    private void setUpLayoutHoder2(RecyclerView.ViewHolder holder, int position) {
        Myhoder1 myhoder = (Myhoder1)holder;
        myhoder.text_mean.setText(mean[position]);
        myhoder.img_type.setBackgroundResource(R.mipmap.noun);
        myhoder.view.setOnClickListener(parent.onclick);

    }

    public void setLayoutHoder(RecyclerView.ViewHolder holder, final int position){
        final Myhoder myhoder =(Myhoder)holder;
        myhoder.text_word.setText(dataWord.get(position).getmWord());
        final SqliteVocabulary sqlite = new SqliteVocabulary();
        boolean b  =sqlite.checkFavoriteWord(dataWord.get(position).getmId())==1;
        if(b){
            myhoder.img_check.setBackgroundResource(R.mipmap.icon_heart);
            myhoder.flag=1;
        }else {
            myhoder.img_check.setBackgroundResource(R.mipmap.icon_heart_while);
            myhoder.flag=0;
        }

        LinearLayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        AdapterWordPart adapter= new AdapterWordPart(context,myhoder,dataWord.get(position).getmTypes(),dataWord.get(position).getmMeanings());
        myhoder.recycle.setLayoutManager(manager);
        myhoder.recycle.setAdapter(adapter);
        myhoder.view.setBackgroundResource(R.color.none);
        myhoder.onclick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myhoder.flag==0){
                    myhoder.flag=1;
                    myhoder.img_check.setBackgroundResource(R.mipmap.icon_heart);
                    myhoder.view.setBackgroundResource(R.color.header_bar);
                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                    String date = df.format(Calendar.getInstance().getTime());
                    //sqlite.insertFavoriteWord(new ModelFavoriteWord(dataWord.get(position).getmId(),date));
                }else{
                    myhoder.flag=0;
                    myhoder.img_check.setBackgroundResource(R.mipmap.icon_heart_while);
                    myhoder.view.setBackgroundResource(R.color.none);
                   // sqlite.deleteWordFavorite(dataWord.get(position).getmId());

                }
            }
        };
        myhoder.view.setOnClickListener(myhoder.onclick);


    }

    @Override
    public int getItemViewType(int position) {
        return flag;
    }

    @Override
    public int getItemCount() {
        if(flag==0)return dataWord.size();
        else return mean.length;
    }

    public class Myhoder extends RecyclerView.ViewHolder{
        public View view;
        public TextView text_word;
        public ImageView img_check;
        public RecyclerView recycle;
        View.OnClickListener onclick;
        public int flag =0;
        public Myhoder(View itemView) {
            super(itemView);
            this.view = itemView;
            text_word = (TextView) itemView.findViewById(R.id.text_word);
            img_check = (ImageView)itemView.findViewById(R.id.img_check);
            recycle = (RecyclerView)itemView.findViewById(R.id.recycle_mean);

        }

    }

    public class Myhoder1 extends RecyclerView.ViewHolder{
        public TextView text_mean;
        public ImageView img_type;
        public View view ;
        public Myhoder1(View itemView) {
            super(itemView);
            this.view = itemView;
            text_mean = (TextView)itemView.findViewById(R.id.text_mean);
            img_type = (ImageView)itemView.findViewById(R.id.img_type);
        }


    }
}
