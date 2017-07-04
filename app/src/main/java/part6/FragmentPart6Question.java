package part6;

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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.vntoeic.bkteam.vntoeicpro.R;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import model.ModelPart6;
import part5.FragmentPart5Question;
import supportview.ConvertTagView;

/**
 * Created by dainguyen on 6/17/17.
 */

public class FragmentPart6Question extends Fragment {
    private ModelPart6.Part6Question dataquestion;
    private String select = "Not choose";
    private int mode =0;
    private int issubmit=0;
    private int position = 0;
    private ArrayList<AdapterQuesionPart6.Myhoder>arrholder;
    public AdapterQuesionPart6 adapter;
    public IQuestionPart6 iQuestionPart6;
    private int idquestion=0;
    private int orgX;
    private int offsetX;
    private int isfigure=0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        arrholder= new ArrayList<>();
        if(bundle!=null){
            dataquestion= (ModelPart6.Part6Question) bundle.getSerializable("dataquestion");
            select = bundle.getString("select");
            position = bundle.getInt("position");
            idquestion = bundle.getInt("idquestion");
            isfigure = bundle.getInt("figure");
            mode = bundle.getInt("mode");
            issubmit= bundle.getInt("issubmit");

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_part6,container,false);
        setUpLayout(view);
        return view;
    }

    public void setUpLayout(View view){
        TextView text_postion = (TextView) view.findViewById(R.id.text_position);

        text_postion.setText(String.valueOf(position+1)+" of 3");

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycle_ques_part6);
         adapter = new AdapterQuesionPart6();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        orgX = (int) event.getX();
                        return true;
                    case MotionEvent.ACTION_UP:
                        offsetX = (int) (event.getX() - orgX);
                        if (offsetX < -150) iQuestionPart6.next();
                        else if (offsetX > 150) iQuestionPart6.back();
                        return true;
                    case MotionEvent.ACTION_POINTER_DOWN:
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:

                }
                return false;
            }
        });



    }
    public void setIssubmit(int issubmit){
        this.issubmit = issubmit;
    }
    public void setiQuestionPart6(IQuestionPart6 iQuestionPart6){
        this.iQuestionPart6= iQuestionPart6;
    }

    public interface IQuestionPart6{
        public void addChoose(String choose);
        public void next();
        public void back();
    }

    public void showResultQuestion() {
        String sol = dataquestion.sol;
        if (!select.equals("Not choose")) {
            if (select.equals("a")) {
                arrholder.get(0).img_check.setBackgroundResource(R.mipmap.icon_false);
                arrholder.get(0).idImage = R.mipmap.icon_false;
            } else if (select.equals("b")) {
                arrholder.get(1).img_check.setBackgroundResource(R.mipmap.icon_false);
                arrholder.get(1).idImage = R.mipmap.icon_false;
            } else if (select.equals("c")) {
                arrholder.get(2).img_check.setBackgroundResource(R.mipmap.icon_false);
                arrholder.get(2).idImage = R.mipmap.icon_false;
            } else if (select.equals("d")) {
                arrholder.get(3).img_check.setBackgroundResource(R.mipmap.icon_false);
                arrholder.get(3).idImage = R.mipmap.icon_false;
            }
        }else{
            for(int i =0;i<arrholder.size();i++){
                arrholder.get(i).img_check.setBackgroundResource(R.mipmap.icon_false);
                arrholder.get(i).idImage = R.mipmap.icon_false;
            }
        }


                if (sol.equals("a")) {
                    arrholder.get(0).img_check.setBackgroundResource(R.mipmap.icon_true);
                    arrholder.get(0).idImage = R.mipmap.icon_true;
                }
                else if (sol.equals("b")) {
                    arrholder.get(1).img_check.setBackgroundResource(R.mipmap.icon_true);
                    arrholder.get(1).idImage = R.mipmap.icon_true;
                }
                else if (sol.equals("c")) {
                    arrholder.get(2).img_check.setBackgroundResource(R.mipmap.icon_true);
                    arrholder.get(2).idImage = R.mipmap.icon_true;
                }
                else if (sol.equals("d")) {
                    arrholder.get(3).img_check.setBackgroundResource(R.mipmap.icon_true);
                    arrholder.get(3).idImage = R.mipmap.icon_true;
                }


    }

    public void showFiugre(){
        String url = "Http://vntoeic.com/api/v1/part6/result/"+ String.valueOf(idquestion)+"/"+String.valueOf(position+1);
        FragmentPart6Question.MyAsynTask myAsynTask = new FragmentPart6Question.MyAsynTask();
        myAsynTask.execute(url);
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


                String jsonString = new String();

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

            for(int i =0;i<arrholder.size();i++){
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

    public class AdapterQuesionPart6 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.cell_question_part6,parent,false);
            Myhoder myhoder = new Myhoder(view);
            arrholder.add(myhoder);
            return myhoder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Myhoder myhoder = (Myhoder)holder;

            if(issubmit==1 && position==3){
                if(isfigure==1)showFiugre();
                myhoder.text_ques.setText(dataquestion.d);
                showResultQuestion();
            } else if (position == 0) {
                myhoder.text_ques.setText(dataquestion.a);
                if (select.equals("a"))
                    myhoder.img_check.setBackgroundResource(R.mipmap.icon_checked);
            } else if (position == 1) {
                myhoder.text_ques.setText(dataquestion.b);
                if (select.equals("b"))
                    myhoder.img_check.setBackgroundResource(R.mipmap.icon_checked);
            } else if (position == 2) {
                myhoder.text_ques.setText(dataquestion.c);
                if (select.equals("c"))
                    myhoder.img_check.setBackgroundResource(R.mipmap.icon_checked);
            } else {
                myhoder.text_ques.setText(dataquestion.d);
                if (select.equals("d"))
                    myhoder.img_check.setBackgroundResource(R.mipmap.icon_checked);
            }

                myhoder.itemView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                if (mode != 1 && issubmit == 0) {
                                    for (int i = 0; i < arrholder.size(); i++) {
                                        if (i != position)
                                            arrholder.get(i).img_check.setBackgroundResource(R.mipmap.icon_notchecked);
                                        else
                                            arrholder.get(i).img_check.setBackgroundResource(R.mipmap.icon_checked);

                                    }
                                    if (position == 0) select = "a";
                                    else if (position == 1) select = "b";
                                    else if (position == 2) select = "c";
                                    else select = "d";
                                }
                                iQuestionPart6.addChoose(select);
                                break;
                        }
                        return false;
                    }
                });



        }

        @Override
        public int getItemCount() {
            return 4;
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
    }



}
