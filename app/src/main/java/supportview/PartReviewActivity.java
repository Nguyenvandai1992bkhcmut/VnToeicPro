package supportview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import model.IDataPart;
import model.ModelPart5;
import model.ModelPart6;
import model.ModelPartCheck;
import sqlite.SqlitePart5;
import sqlite.SqlitePart6;

/**
 * Created by dainguyen on 6/11/17.
 */

public class PartReviewActivity extends AppCompatActivity {

    private IDataPart data[];
    private int mode =0;
    private int part =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part5);
        getData();
        setUpLayout();
    }

    public void getData(){


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            part = bundle.getInt("part");
            mode = bundle.getInt("mode");
            if(part==5) {
                SqlitePart5 sqlitePart5 = new SqlitePart5();
                if (mode == 4) {
                    data = sqlitePart5.searchPart5Check();
                } else {
                    data = sqlitePart5.searchPart5Favorite();
                }
            }else if(part==6){
                SqlitePart6 sqlitePart6 = new SqlitePart6();
                if (mode == 4) {
                    data = sqlitePart6.searchPart6Check();
                } else {
                    data = sqlitePart6.searchPart6Favorite();
                }
            }
        }

    }

    public void setUpLayout(){
        TextView textView = (TextView)findViewById(R.id.text_part);
        if(part==5){
            textView.setText("Part 5");
        }else if(part==6){
            textView.setText("Part 6");
        }

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycle);
        AdapterReview adapterReview = new AdapterReview();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterReview);

        ImageView img_back = (ImageView)findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public class AdapterReview  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.cell_part5_review,parent,false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Holder holder1 = (Holder)holder;
            if(part==5) holder1.textView.setText(data[position].getContent());
            else if(part==6){
                ConvertTagView convertTagView  = new ConvertTagView(getApplicationContext(),data[position].getContent());
                holder1.textView.setText(convertTagView.getSpannableString());
            }
            ModelPartCheck modelPartCheck[] = null;
            if(part==5) {
                SqlitePart5 sqlitePart5 = new SqlitePart5();
                modelPartCheck = sqlitePart5.searchAllCheckPartId(5, data[position].getId());
            }else if(part==6){
                SqlitePart6 sqlitePart6 = new SqlitePart6();
                modelPartCheck = sqlitePart6.searchAllCheckPartId(6, data[position].getId());
            }
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
            AdapterCheck adapterCheck = new AdapterCheck(modelPartCheck);
            holder1.recyclerView.setLayoutManager(linearLayoutManager);
            holder1.recyclerView.setAdapter(adapterCheck);

            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(PartReviewActivity.this , PartPractiseAcitvity.class);

                    Bundle bundle = new Bundle();
                    bundle.putInt("mode",mode);
                    bundle.putInt("part",part);
                    if(mode==3){
                        bundle.putString("title","Favorite");
                        bundle.putInt("key",1);
                        bundle.putInt("position",position);
                    }
                    else{
                        bundle.putString("title","History");
                        bundle.putInt("key",2);
                        bundle.putInt("position",position);
                    }
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.length;

        }

        public class Holder extends RecyclerView.ViewHolder{
            public TextView textView;
            public RecyclerView recyclerView;
            public Holder(View itemView) {
                super(itemView);
                textView = (TextView)itemView.findViewById(R.id.text_ques);
                recyclerView = (RecyclerView) itemView.findViewById(R.id.recycle_check);
            }
        }

        public class  AdapterCheck extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
            ModelPartCheck[]data;
            public AdapterCheck(ModelPartCheck[]data){
                this.data = data;
            }
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                ImageView imageView = new ImageView(getApplicationContext());
                RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(30,0,0,0);
                imageView.setLayoutParams(params);

                return new Holder(imageView);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                if(data[position].getResult()==1){
                    ((Holder) holder).imageView.setBackgroundResource(R.mipmap.icon_true);
                }else{
                    ((Holder) holder).imageView.setBackgroundResource(R.mipmap.icon_false);
                }
            }

            @Override
            public int getItemCount() {
                return data.length;
            }

            public class Holder extends RecyclerView.ViewHolder{
                public ImageView imageView;
                public Holder(View itemView) {
                    super(itemView);
                    this.imageView = (ImageView) itemView;
                }
            }
        }
    }
}
