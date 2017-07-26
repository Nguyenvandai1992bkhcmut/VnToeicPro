package part7;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import model.IDataQuestion;
import supportview.IContentQuestion;

/**
 * Created by dainguyen on 6/17/17.
 */

public class FragmentPartQuestion extends Fragment {
    private IDataQuestion dataquestion;
    private String select = "Not choose";
    private int issubmit=0;
    private int positionQues = 0;

    public IContentQuestion iContentQuestion;

    private int isfigure=0;

    private int isreadtest=0;
    private int numberQuestion=0;
    private int part=0;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null) {
            positionQues = bundle.getInt("position");
        }
        bundle = iContentQuestion.getBunldeQuestion(positionQues);
        if(bundle!=null) {
            dataquestion = (IDataQuestion) bundle.getSerializable("data");
            select = bundle.getString("select");

            isfigure = bundle.getInt("figure");
            issubmit = bundle.getInt("issubmit");
            isreadtest = bundle.getInt("fulltest");
            numberQuestion = bundle.getInt("numberQuestion");
            part = bundle.getInt("part");
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_part6,container,false);
        setUpLayout(view);
        return view;
    }

    public void setUpLayout(final View view){
        view.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView text_postion = (TextView) view.findViewById(R.id.text_position);

        if(isreadtest==0) {
            text_postion.setText(String.valueOf(positionQues+1)+". "+dataquestion.getQuestion());
            text_postion.setTextColor(dataquestion.getColorTextQuestion());
        }else {
            if(part==7){
                text_postion.setText(String.valueOf(numberQuestion+positionQues)+". "+dataquestion.getQuestion());
            }else text_postion.setText(String.valueOf(numberQuestion+positionQues)+". ");
            text_postion.setTextColor(dataquestion.getColorTextQuestion());
        }

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycle_ques_part6);
        AdapterQuesionPart adapter = new AdapterQuesionPart();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


    }
    public void setIssubmit(int issubmit){
        this.issubmit = issubmit;
    }

    public void setiContentQuestion(IContentQuestion iContentQuestion){
        this.iContentQuestion= iContentQuestion;
    }


    public class AdapterQuesionPart extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private ArrayList<Myhoder>arrholder= new ArrayList<>();
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.cell_question_part6,parent,false);
            Myhoder myhoder = new Myhoder(view);
            arrholder.add(myhoder);
            return myhoder;
        }

        public void showResultQuestion() {
            issubmit=1;
            String sol = dataquestion.getSol();
            if (!select.equals("Not choose")) {
                for(int i=0;i<arrholder.size();i++){
                    if (select.equals(String.valueOf((char)('a'+i)))) {
                        arrholder.get(i).img_check.setBackgroundResource(R.mipmap.icon_false);
                        arrholder.get(i).idImage = R.mipmap.icon_false;
                    }
                }
            }else{
                for(int i =0;i<arrholder.size();i++){
                    arrholder.get(i).img_check.setBackgroundResource(R.mipmap.icon_false);
                    arrholder.get(i).idImage = R.mipmap.icon_false;
                }
            }

            for(int i=0;i<arrholder.size();i++){
                if (sol.equals(String.valueOf((char)('a'+i)))) {
                    arrholder.get(i).img_check.setBackgroundResource(R.mipmap.icon_true);
                    arrholder.get(i).idImage = R.mipmap.icon_true;
                }
            }

        }

        public void showFiugre(){
            isfigure=1;
            String url = dataquestion.getLinkFigure();
            MyAsynTask myAsynTask = new MyAsynTask();
            myAsynTask.execute(url);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Myhoder myhoder = (Myhoder)holder;

            if(issubmit==1 && position==3){
                if(isfigure==1)showFiugre();
                myhoder.text_ques.setText(dataquestion.getD());
                showResultQuestion();
            } else if (position == 0) {
                myhoder.text_ques.setText(dataquestion.getA());
                if (select.equals("a"))
                    myhoder.img_check.setBackgroundResource(R.mipmap.icon_checked);
            } else if (position == 1) {
                myhoder.text_ques.setText(dataquestion.getB());
                if (select.equals("b"))
                    myhoder.img_check.setBackgroundResource(R.mipmap.icon_checked);
            } else if (position == 2) {
                myhoder.text_ques.setText(dataquestion.getC());
                if (select.equals("c"))
                    myhoder.img_check.setBackgroundResource(R.mipmap.icon_checked);
            } else {
                myhoder.text_ques.setText(dataquestion.getD());
                if (select.equals("d"))
                    myhoder.img_check.setBackgroundResource(R.mipmap.icon_checked);
            }
            myhoder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (issubmit == 0) {

                        for (int i = 0; i < arrholder.size(); i++) {
                            if (i != position)
                                arrholder.get(i).img_check.setBackgroundResource(R.mipmap.icon_notchecked);
                            else {
                                if(select.equals(String.valueOf((char)('a'+i)))){
                                    arrholder.get(i).img_check.setBackgroundResource(R.mipmap.icon_notchecked);
                                    select="Not choose";
                                }else {
                                    arrholder.get(i).img_check.setBackgroundResource(R.mipmap.icon_checked);
                                    select = String.valueOf((char)('a'+position));
                                }
                            }

                        }
                        iContentQuestion.addChoose(positionQues,select);
                    }

                }
            });


        }

        @Override
        public int getItemCount() {
            return 4;
        }

        @Override
        public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {

            super.onViewDetachedFromWindow(holder);

        }

        public class  Myhoder extends RecyclerView.ViewHolder{

            public ImageView img_check;
            public TextView text_ques;
            public TextView text_pro;
            public LinearLayout line;
            public ImageView img;
            public int idImage =0;
            public Myhoder(View itemView) {
                super(itemView);
                img_check = (ImageView) itemView.findViewById(R.id.checkbox);
                text_ques = (TextView) itemView.findViewById(R.id.text_ques);
                text_pro = (TextView)itemView.findViewById(R.id.text_pro);
                line = (LinearLayout)itemView.findViewById(R.id.line);
                img = (ImageView)itemView.findViewById(R.id.img);
            }
        }

        public class MyAsynTask extends AsyncTask<String , Void ,ArrayList<String>> {
            int count =0;
            @Override
            protected ArrayList<String> doInBackground(String... params) {
                URL url = null;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(params[0]);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));

                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    JSONObject ob = new JSONObject(sb.toString());

                    ArrayList<String> arr = new ArrayList<>();
                    arr.add(ob.getString("a"));
                    count+=Integer.valueOf(arr.get(0));
                    arr.add(ob.getString("b"));
                    count+=Integer.valueOf(arr.get(1));
                    arr.add(ob.getString("c"));
                    count+=Integer.valueOf(arr.get(2));
                    arr.add(ob.getString("d"));
                    count+=Integer.valueOf(arr.get(3));
                    if(count==0)count=1;

                    return arr;

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }
                return new ArrayList<>();
            }

            @Override
            protected void onPostExecute(ArrayList<String> str) {
                super.onPostExecute(str);
                if(str.size()==0)return;
                for(int i= 0;i<arrholder.size();i++){
                    arrholder.get(i).line.setVisibility(View.VISIBLE);
                    float value = Float.valueOf(str.get(i))/count *100;
                    arrholder.get(i).text_pro.setText(String.format("%.2f", value)+"%");
                    arrholder.get(i).text_pro.setVisibility(View.VISIBLE);
                    arrholder.get(i).img.setVisibility(View.VISIBLE);
                    arrholder.get(i).line.setGravity(Gravity.CENTER);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)(300*value/100),30);
                    params.gravity =Gravity.CENTER;
                    arrholder.get(i).img.setLayoutParams(params);
                    ValueAnimator valueAnimator = new ValueAnimator().ofFloat(300*value/100);

                    final int finalI = i;

                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(final ValueAnimator animation) {
                            Drawable draw = new Drawable() {
                                @Override
                                public void draw(Canvas canvas) {
                                    Paint paint = new Paint();
                                    paint.setStrokeWidth(60);

                                    if(arrholder.get(finalI).idImage == R.mipmap.icon_true){
                                        paint.setColor(Color.GREEN);
                                    }
                                    else if(arrholder.get(finalI).idImage == R.mipmap.icon_false){
                                        paint.setColor(Color.RED);
                                    }
                                    else {
                                        paint.setColor(Color.YELLOW);
                                    }

                                    canvas.drawLine(0,0, (Float) animation.getAnimatedValue(),0,paint);
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
                            arrholder.get(finalI).img.setImageDrawable(draw);

                        }
                    });
                    valueAnimator.setDuration(500);
                    valueAnimator.start();

                }


            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }
        }

    }



}
