package part5;

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

import model.ModelPart5;
import model.ModelPartCheck;
import sqlite.SqlitePart5;

/**
 * Created by dainguyen on 6/11/17.
 */

public class Part5ReviewActivity extends AppCompatActivity {

    private ModelPart5 data[];
    private ModelPartCheck modelPartCheck[];
    private int mode =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part5);
        getData();
        setUpLayout();
    }

    public void getData(){
        SqlitePart5 sqlitePart5 = new SqlitePart5();
        data = sqlitePart5.searchPart5Check();
        modelPartCheck = sqlitePart5.searchAllCheckedPart(5);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            mode =bundle.getInt("mode");
        }

    }

    public void setUpLayout(){
            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycle);
        AdapterReview adapterReview = new AdapterReview();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterReview);
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
            holder1.textView.setText(data[position].getQuestion());
            if(modelPartCheck[position].getResult()==0){
                holder1.imageView.setBackgroundResource(R.mipmap.icon_false);
            }else holder1.imageView.setBackgroundResource(R.mipmap.icon_true);

            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Part5ReviewActivity.this ,Part5PractiseAcitvity.class);
                    Bundle bundle = new Bundle();

                    bundle.putInt("mode",0);
                    if(mode==0){
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
            if(modelPartCheck!=null){
                return modelPartCheck.length;
            }
            return 0;
        }

        public class Holder extends RecyclerView.ViewHolder{
            public TextView textView;
            public ImageView imageView;
            public Holder(View itemView) {
                super(itemView);
                textView = (TextView)itemView.findViewById(R.id.text_ques);
                imageView = (ImageView)itemView.findViewById(R.id.img_check);
            }
        }
    }
}
