package feeds;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import model.Convert;
import supportview.ConvertTagView;
import supportview.FlowLayout;

/**
 * Created by dainguyen on 9/14/17.
 */

public class Question implements Serializable{
    private  int question_id;
    private String question;
    private ArrayList<Answer>arr_answer;

    public Question(JSONObject object) {
        try {
            this.question_id = object.getInt("questionId");
            this.question = object.getString("question");
            JSONArray arr = object.getJSONArray("answers");
            arr_answer= new ArrayList<>();
            for(int i=0;i<arr.length();i++){
                JSONObject o =arr.getJSONObject(i);
                arr_answer.add(new Answer(o.getString("name"),o.getString("answer")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public View getViewQuestion(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.feed_question,null,false);
        TextView text_question = (TextView)view.findViewById(R.id.text_ques);
        ConvertTagView convertTagView = new ConvertTagView(context,question);
       text_question.setText(convertTagView.getSpannableString());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        int w = context.getResources().getDisplayMetrics().widthPixels;
        GridLayoutManager gridLayoutManager = null;
        if(checkSizeText(context,w)){
            gridLayoutManager = new GridLayoutManager(context,2, LinearLayoutManager.VERTICAL,false);
        }else{
            gridLayoutManager = new GridLayoutManager(context,1,LinearLayoutManager.VERTICAL,false);
        }
        Adapter adapter = new Adapter(context);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;

    }

    public boolean checkSizeText(Context context,int w){
        for(int i =0;i<arr_answer.size();i++ ){
            Paint paint= new Paint();
            float size = convertSpToPixels(18,context);
            paint.setTextSize(size);
            float width = paint.measureText(arr_answer.get(i).answer, 0, arr_answer.get(i).answer.length());
            if(width>(w/2)){
                return false;
            }
        }
        return true;

    }
    public static int convertSpToPixels(float sp, Context context) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
        return px;
    }

    public ArrayList<Answer> getArr_answer() {
        return arr_answer;
    }

    public void setArr_answer(ArrayList<Answer> arr_answer) {
        this.arr_answer = arr_answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }



    public class Answer implements Serializable{
        public String name;
        public String answer;

        public Answer(String name, String answer) {
            this.name = name;
            this.answer = answer;
        }
    }

    public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private Context context;
        public Adapter(Context context){
            this.context = context;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.cell_question_feed,null,false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Holder hol = (Holder) holder;
            hol.textView.setText(arr_answer.get(position).answer);
        }

        @Override
        public int getItemCount() {
            return arr_answer.size();
        }

        public class Holder extends RecyclerView.ViewHolder{
            public ImageView imageView;
            public TextView textView;
            public Holder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.img_check);
                textView = (TextView) itemView.findViewById(R.id.text_answer);
            }
        }
    }
}
