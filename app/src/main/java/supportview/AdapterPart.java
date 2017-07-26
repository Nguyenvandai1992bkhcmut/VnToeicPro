package supportview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.MainActivity;
import com.vntoeic.bkteam.vntoeicpro.R;

import model.ModelPartResult;

/**
 * Created by dainguyen on 6/2/17.
 */

public class AdapterPart extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private AppCompatActivity context;
    private ModelPartResult[]data;
    private ICallActivity iCallActivity;

    public AdapterPart(AppCompatActivity context , ModelPartResult[]data){
        this.context = context;
        iCallActivity = (ICallActivity) context;
        this.data = data;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =null;
        if(viewType ==0){
            view = LayoutInflater.from(context).inflate(R.layout.cell_part_select,parent,false);
            return new Myhoder1(view);
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.cell_part_subject,parent,false);
            return new Myhoder2(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(position<4){
           Myhoder1 myhoder1 = (Myhoder1)holder;
            if(position==0){
                myhoder1.imageView.setBackgroundResource(R.mipmap.icon_test_part);
                myhoder1.textView.setText("Test");
            }
            else if(position==1){
                myhoder1.imageView.setBackgroundResource(R.mipmap.icon_practise_part);
                myhoder1.textView.setText("Unlimited Question");
            }
            else if(position==2){
                myhoder1.imageView.setBackgroundResource(R.mipmap.icon_favorite_part);
                myhoder1.textView.setText("Favorite Question");
            }
            else if(position==3){
                myhoder1.imageView.setBackgroundResource(R.mipmap.icon_history_part);
                myhoder1.textView.setText("History");
            }
            myhoder1.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position==0)iCallActivity.funCallActivity(position-4,"Test");
                    else if(position==1)iCallActivity.funCallActivity(position-4,"UnLimited Question");
                    else if(position==2)iCallActivity.funCallActivity(position-4,"Favorite");
                    else if(position==3)iCallActivity.funCallActivity(position-4,"History");
                }
            });

        }else{
            Myhoder2 myhoder2 = (Myhoder2)holder;
            myhoder2.textView1.setText(data[position-4].getTitle());
            myhoder2.textView2.setText("Correct" + data[position-4].getCorrect()+ "/" + data[position-4].getCount());
            myhoder2.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iCallActivity.funCallActivity(data[position-4].getIdsubject(),data[position-4].getTitle());
                }
            });
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
        public View view;
        public TextView textView;
        public ImageView imageView;
        public Myhoder1(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            imageView = (ImageView)itemView.findViewById(R.id.img);
            textView.setTypeface(MainActivity.typeface);
            this.view = itemView;
        }
    }

    public class Myhoder2 extends RecyclerView.ViewHolder{
        public View view;
        public TextView textView1;
        public TextView textView2;
        public Myhoder2(View itemView) {
            super(itemView);
            this.view = itemView;
            textView1 = (TextView)itemView.findViewById(R.id.text1);
            textView2 = (TextView)itemView.findViewById(R.id.text2);
            textView1.setTypeface(MainActivity.typeface);
            textView2.setTypeface(MainActivity.typeface);
        }
    }

    public interface ICallActivity{
        public void funCallActivity(int idsubject, String title);
    }
}
