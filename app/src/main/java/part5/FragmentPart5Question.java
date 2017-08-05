package part5;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.hanks.htextview.animatetext.EvaporateText;
import com.hanks.htextview.animatetext.HText;
import com.vntoeic.bkteam.vntoeicpro.AdapterWordSearch;
import com.vntoeic.bkteam.vntoeicpro.MainActivity;
import com.vntoeic.bkteam.vntoeicpro.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import model.ModelPart5;
import supportview.IContentQuestion;

import static com.vntoeic.bkteam.vntoeicpro.R.id.icon;
import static com.vntoeic.bkteam.vntoeicpro.R.id.textView;
import static java.lang.Float.valueOf;

/**
 * Created by dainguyen on 6/2/17.
 */

/*
mode = 0 select Dap an
    => show Solution
    => Gui ve Class Manager
    => Khong thay doi dc dap an da~ chon
mode =1 select 1 Dap an
    => gui ve Class Manager
 */
public class FragmentPart5Question extends Fragment {
    private ModelPart5 data;

    private String select = "Not choose";
    private int question;
    private IContentQuestion iContentQuestion;
    private int issubmit = 0;
    private RecyclerView recyclerView;
    private  AdapterQuestion adapter;
    private int mode;
    private int isfigure =0;

    public ArrayList<AdapterQuestion.Myhoder> arrHolder = new ArrayList<>();
    int orgX;
    int offsetX;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        data = (ModelPart5) bundle.getSerializable("data");
        mode = bundle.getInt("mode");
        question = bundle.getInt("question");
        select = bundle.getString("select");
        issubmit = bundle.getInt("submit");
        isfigure = bundle.getInt("figure");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_part5, container, false);
        setUpLayout(view);
        return view;
    }

    public void setUpLayout(View view) {
        TextView text_question = (TextView) view.findViewById(R.id.text_question);
        text_question.setText(String.valueOf(question+1)+". "+data.getQuestion());
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_ques);
        adapter = new AdapterQuestion();
        GridLayoutManager gridLayoutManager;
        if (checkSize()) {
            gridLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        } else
            gridLayoutManager = new GridLayoutManager(getContext(), 1, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

/*
on
 */
        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        orgX = (int) event.getX();
                        return true;
                    case MotionEvent.ACTION_UP:
                        v.setScaleX(1);
                        offsetX = (int) (event.getX() - orgX);
                        if (offsetX < -150) iContentQuestion.nextContent();
                        else if (offsetX > 150) iContentQuestion.backContent();
                        return true;
                    case MotionEvent.ACTION_POINTER_DOWN:
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:

                }
                return false;
            }
        };
        view.setOnTouchListener(onTouchListener);
        // recyclerView.setOnTouchListener(onTouchListener);
        //text_question.setOnTouchListener(onTouchListener);

        if(isfigure==1)showFigure();

    }

    boolean checkSize() {
        if (data.getA().length() > 15) return false;
        if (data.getB().length() > 15) return false;

        if (data.getC().length() > 15) return false;

        if (data.getD().length() > 15) return false;
        return true;
    }



    public void setiContentQuestion(IContentQuestion iContentQuestion) {
        this.iContentQuestion = iContentQuestion;
    }

    public class AdapterQuestion extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.cell_question_part5, parent, false);
            Myhoder myhoder = new Myhoder(view);
            arrHolder.add(myhoder);
            return myhoder;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            Myhoder myhoder = (Myhoder) holder;
            if( issubmit==1 && position==3){
                myhoder.text_ques.setText(data.getD());
                showResultQuestion();
            }else {
                if (position == 0) {
                    myhoder.text_ques.setText(data.getA());
                    if (select.equals("a"))
                        myhoder.img_check.setBackgroundResource(R.mipmap.icon_checked);
                } else if (position == 1) {
                    myhoder.text_ques.setText(data.getB());
                    if (select.equals("b"))
                        myhoder.img_check.setBackgroundResource(R.mipmap.icon_checked);
                } else if (position == 2) {
                    myhoder.text_ques.setText(data.getC());
                    if (select.equals("c"))
                        myhoder.img_check.setBackgroundResource(R.mipmap.icon_checked);
                } else {
                    myhoder.text_ques.setText(data.getD());
                    if (select.equals("d"))
                        myhoder.img_check.setBackgroundResource(R.mipmap.icon_checked);
                }
            }
            myhoder.itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            orgX = (int) event.getX();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            offsetX = (int) (event.getX() - orgX);
                            if (offsetX < -200) iContentQuestion.nextContent();
                            else if (offsetX > 200) iContentQuestion.backContent();
                            return true;
                        case MotionEvent.ACTION_UP:

                            if (Math.abs(offsetX) < 100){
                                if (issubmit == 0) {
                                    for (int i = 0; i < arrHolder.size(); i++) {
                                        if (i != position)
                                            arrHolder.get(i).img_check.setBackgroundResource(R.mipmap.icon_notchecked);
                                        else
                                            arrHolder.get(i).img_check.setBackgroundResource(R.mipmap.icon_checked);

                                    }
                                    if (position == 0) select = "a";
                                    else if (position == 1) select = "b";
                                    else if (position == 2) select = "c";
                                    else select = "d";

                                    if (mode != 1) {
                                        showResultQuestion();
                                        issubmit = 1;
                                        iContentQuestion.addChoose(0,select);
                                    } else {
                                        iContentQuestion.addChoose(0,select);
                                    }
                                }
                            }
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

        @Override
        public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
//            int position = holder.getLayoutPosition();
//            if (position == 0 || position == 2) {
//                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.right_left_in);
//                holder.itemView.startAnimation(animation);
//            } else {
//                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.left_to_right_in);
//                holder.itemView.startAnimation(animation);
//            }
            Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.scale_question_in);
            animation.setStartOffset(holder.getLayoutPosition()*150);
            holder.itemView.startAnimation(animation);
            super.onViewAttachedToWindow(holder);
        }

        public void showResultQuestion() {
            String sol = data.getResult();
            if (!select.equals("Not choose")) {
                if (select.equals("a")) {
                    arrHolder.get(0).img_check.setBackgroundResource(R.mipmap.icon_false);
                    arrHolder.get(0).idImage = R.mipmap.icon_false;
                } else if (select.equals("b")) {
                    arrHolder.get(1).img_check.setBackgroundResource(R.mipmap.icon_false);
                    arrHolder.get(1).idImage = R.mipmap.icon_false;
                } else if (select.equals("c")) {
                    arrHolder.get(2).img_check.setBackgroundResource(R.mipmap.icon_false);
                    arrHolder.get(2).idImage = R.mipmap.icon_false;
                } else if (select.equals("d")) {
                    arrHolder.get(3).img_check.setBackgroundResource(R.mipmap.icon_false);
                    arrHolder.get(3).idImage = R.mipmap.icon_false;
                }
            }else{
                for(int i=0;i<arrHolder.size();i++){
                    arrHolder.get(i).img_check.setBackgroundResource(R.mipmap.icon_false);
                    arrHolder.get(i).idImage = R.mipmap.icon_false;
                }
            }


            if (sol.equals("a")) {
                arrHolder.get(0).img_check.setBackgroundResource(R.mipmap.icon_true);
                arrHolder.get(0).idImage = R.mipmap.icon_true;
            }
            else if (sol.equals("b")) {
                arrHolder.get(1).img_check.setBackgroundResource(R.mipmap.icon_true);
                arrHolder.get(1).idImage = R.mipmap.icon_true;
            }
            else if (sol.equals("c")) {
                arrHolder.get(2).img_check.setBackgroundResource(R.mipmap.icon_true);
                arrHolder.get(2).idImage = R.mipmap.icon_true;
            }
            else if (sol.equals("d")) {
                arrHolder.get(3).img_check.setBackgroundResource(R.mipmap.icon_true);
                arrHolder.get(3).idImage = R.mipmap.icon_true;
            }


        }


        public class Myhoder extends RecyclerView.ViewHolder {
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

    public void showFigure() {
        String url = "Http://vntoeic.com/api/v1/part5/result/"+String.valueOf(data.getId());
        MyAsynTask myAsynTask = new MyAsynTask();
        myAsynTask.execute(url);
    }

    public class MyAsynTask extends AsyncTask<String , Void ,ArrayList<String>>{
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

            for(int i =0;i<arrHolder.size();i++){
                float value = Float.valueOf(str.get(i))/count *100;
                arrHolder.get(i).text_pro.setText(String.format("%.2f", value)+"%");
                arrHolder.get(i).text_pro.setVisibility(View.VISIBLE);
                arrHolder.get(i).img.setVisibility(View.VISIBLE);
                arrHolder.get(i).line.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)(700*value/100),30);
                params.gravity =Gravity.CENTER;
                arrHolder.get(i).img.setLayoutParams(params);
                ValueAnimator valueAnimator = new ValueAnimator().ofFloat(700*value/100);

                final int finalI = i;

                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(final ValueAnimator animation) {
                        Drawable draw = new Drawable() {
                            @Override
                            public void draw(Canvas canvas) {
                                Paint paint = new Paint();
                                paint.setStrokeWidth(60);

                                if(arrHolder.get(finalI).idImage == R.mipmap.icon_true){
                                    paint.setColor(Color.GREEN);
                                }
                                else if(arrHolder.get(finalI).idImage == R.mipmap.icon_false){
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
                        arrHolder.get(finalI).img.setImageDrawable(draw);

                    }
                });
                valueAnimator.setDuration(500);
                valueAnimator.start();

            }
            ((GridLayoutManager)recyclerView.getLayoutManager()).setSpanCount(1);


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}