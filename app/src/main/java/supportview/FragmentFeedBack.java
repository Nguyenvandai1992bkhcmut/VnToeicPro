package supportview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;

/**
 * Created by dainguyen on 7/8/17.
 */

public class FragmentFeedBack extends Fragment {
    private RecyclerView recyclerView;
    private IFeedBack iFeedBack;
    String []title;
    String []content;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getResources().getStringArray(R.array.feedback_part5);
        content = getResources().getStringArray(R.array.feedback_part5_default_content);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submit,container,false);
        setUpLayout(view);
        return view;
    }
    public void setUpLayout(View view){
        recyclerView = (RecyclerView)view.findViewById(R.id.recycle_submit);
        AdapterFeedBack adapterFeedBack = new AdapterFeedBack();
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterFeedBack);
    }

    public void setiFeedBack(IFeedBack iFeedBack){
        this.iFeedBack = iFeedBack;
    }
    public static Drawable drawable  = new Drawable() {
        @Override
        public void draw(Canvas canvas) {
            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(1);

            canvas.drawLine(0,1,2000,1,paint);

        }

        @Override
        public void setAlpha(int alpha) {

        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return 0;
        }
    };

    public class AdapterFeedBack extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.cell_feedback,parent,false);
            Holder holder=  new Holder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder,  int position) {
            final Holder myhoder = (Holder)holder;
            myhoder.text_content.setText(content[position]);
            myhoder.text_title.setText(title[position]);
            final  int p = position;
            myhoder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iFeedBack.showFormFeedback(title[p],content[p]);

                }
            });
        }

        @Override
        public int getItemCount() {
            return title.length;
        }

        public class Holder extends RecyclerView.ViewHolder{
            public TextView text_title;
            public TextView text_content;
            public ImageView imageView;

            public Holder(View itemView) {
                super(itemView);
                text_title = (TextView)itemView.findViewById(R.id.text_title);
                text_content = (TextView)itemView.findViewById(R.id.text_content);
                imageView= (ImageView)itemView.findViewById(R.id.img_line_bot);

                imageView.setImageDrawable(drawable);
            }
        }

    }

    public interface  IFeedBack{
        public void showFormFeedback(String title , String content);
    }
}
