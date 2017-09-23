package part3;

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

import model.IListenPart;
import supportview.IPartControlListen;

/**
 * Created by dainguyen on 7/11/17.
 */

public class FragmentPart34Question extends Fragment {
    private IListenPart data;
    private int mode;
    private int isSubmit;
    private int index;
    private ArrayList<String>choose;
    private RecyclerView recyclerView;
    private int indexQuesiton =0;
    private int isFigure=0;



    private IPartControlListen iContentListen ;
    AdapterQuestion adapterQuestion;

    public void setIPartControl(IPartControlListen iContentQuestion){
        this.iContentListen = iContentQuestion;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    public void getData(){
        Bundle bundle=iContentListen.getBunldeQuestion();
        if(bundle!=null){
            data= (IListenPart) bundle.getSerializable("data");
            choose = bundle.getStringArrayList("choose");
            isSubmit = bundle.getInt("submit");
            mode = bundle.getInt("mode");
            index = bundle.getInt("index");
            indexQuesiton= bundle.getInt("indexQuesiton");
            isFigure = bundle.getInt("figure");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_submit,container,false);
        setUpLayout(view);
        return view;
    }

    public void reloadContent(){
        getData();
        adapterQuestion= new AdapterQuestion();
        recyclerView.swapAdapter(adapterQuestion,false);
        adapterQuestion.notifyDataSetChanged();
    }
    public void setUpLayout(View view){
        recyclerView = (RecyclerView)view.findViewById(R.id.recycle_submit);
         adapterQuestion = new AdapterQuestion();
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterQuestion);
    }

    public void showResult(){
        isSubmit=1;
        adapterQuestion.showResult();
    }

    public void showFigure(){
        adapterQuestion.showFigure();
    }

    public void hideFigure(){
        adapterQuestion.hideFigure();
    }

    public class AdapterQuestion extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        public AdapterQuestion(){
            adapterChild = new ArrayList<>();
        }
        private ArrayList<AdapterChild> adapterChild ;
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.cell_question_part3,parent,false);
            return new Hoder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Hoder hoder  =(Hoder)holder;
            hoder.text_quetion.setText(String.valueOf(indexQuesiton+position+1) +". "+data.getQuestion(position));
             AdapterChild adapterChild1 = new AdapterChild(position);
            adapterChild.add(adapterChild1);
            LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
            hoder.recycle_child.setLayoutManager(manager);
            hoder.recycle_child.setAdapter(adapterChild1);
        }
        public void showFigure(){
            for(int i=0;i<adapterChild.size();i++) {
                adapterChild.get(i).showFiugre();
            }
        }

        public void hideFigure(){
            for(int i=0;i<adapterChild.size();i++) {
                adapterChild.get(i).hideFigure();
            }
        }

        public void showResult(){
            for(int i=0;i<adapterChild.size();i++) {
                adapterChild.get(i).showResultQuestion();
            }
        }

        @Override
        public int getItemCount() {
            return data.getCountQuestion();
        }

        public class Hoder extends RecyclerView.ViewHolder{
            public TextView text_quetion;
            public RecyclerView recycle_child;
            public Hoder(View itemView) {
                super(itemView);
                text_quetion = (TextView)itemView.findViewById(R.id.text_question);
                recycle_child= (RecyclerView)itemView.findViewById(R.id.recycle_child);
            }
        }

        public class AdapterChild extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
            private int indexQues;
            private ArrayList<HolderChild> arrayHoder;

            public AdapterChild(int indexQuesstion) {
                this.indexQues = indexQuesstion;
                this.arrayHoder = new ArrayList<>();
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.cell_question_part5, parent, false);
                HolderChild holderChild = new HolderChild(view);
                arrayHoder.add(holderChild);
                return holderChild;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                HolderChild myhoder = (HolderChild) holder;
                if (isSubmit == 1 && position == (data.getCountAnswer()-1)) {
                    if(isFigure==1)showFiugre();
                    myhoder.text_ques.setText(data.getD(indexQues));
                    showResultQuestion();
                } else {
                    if (position == 0) {
                        myhoder.text_ques.setText(data.getA(indexQues));
                        if (choose.get(indexQues).equals("a"))
                            myhoder.img_check.setBackgroundResource(R.mipmap.icon_checked);
                    } else if (position == 1) {
                        myhoder.text_ques.setText(data.getB(indexQues));
                        if (choose.get(indexQues).equals("b"))
                            myhoder.img_check.setBackgroundResource(R.mipmap.icon_checked);
                    } else if (position == 2) {
                        myhoder.text_ques.setText(data.getC(indexQues));
                        if (choose.get(indexQues).equals("c"))
                            myhoder.img_check.setBackgroundResource(R.mipmap.icon_checked);
                    } else {
                        myhoder.text_ques.setText(data.getD(indexQues));
                        if (choose.get(indexQues).equals("d"))
                            myhoder.img_check.setBackgroundResource(R.mipmap.icon_checked);
                    }
                }
                myhoder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isSubmit == 0) {
                            for (int i = 0; i < arrayHoder.size(); i++) {
                                if (i != position)
                                    arrayHoder.get(i).img_check.setBackgroundResource(R.mipmap.icon_notchecked);
                                else
                                    arrayHoder.get(i).img_check.setBackgroundResource(R.mipmap.icon_checked);

                            }
                            String select = String.valueOf((char) ('a' + position));
                            choose.set(indexQues, select);
                            iContentListen.addResult(choose);
                        }
                    }
                });

            }

            public void showFiugre(){
                MyAsynTask myAsynTask = new MyAsynTask();
                myAsynTask.execute(data.getLinkFigure(indexQues+1),data.getToken());
            }
            public void hideFigure(){
                for(int i=0;i<arrayHoder.size();i++){
                    arrayHoder.get(i).line.setVisibility(View.GONE);
                }
            }


            public void showResultQuestion() {
                String sol = data.getSol(indexQues);
                if (!choose.get(indexQues).equals("Not choose")) {
                    for(int i=0;i<arrayHoder.size();i++){
                        if (choose.get(indexQues).equals(String.valueOf((char)('a'+i)))) {
                            arrayHoder.get(i).img_check.setBackgroundResource(R.mipmap.icon_false);
                            arrayHoder.get(i).idImage = R.mipmap.icon_false;
                        }
                    }
                }else{
                    for(int i=0;i<arrayHoder.size();i++){
                        arrayHoder.get(i).img_check.setBackgroundResource(R.mipmap.icon_false);
                        arrayHoder.get(i).idImage = R.mipmap.icon_false;
                    }
                }

                for(int i=0;i<arrayHoder.size();i++){
                    if (sol.equals(String.valueOf((char)('a'+i)))) {
                        arrayHoder.get(i).img_check.setBackgroundResource(R.mipmap.icon_true);
                        arrayHoder.get(i).idImage = R.mipmap.icon_true;
                    }
                }

            }
            @Override
            public int getItemCount() {
                return data.getCountAnswer();
            }

            public class HolderChild extends RecyclerView.ViewHolder{
                public ImageView img_check;
                public TextView text_ques;
                public TextView text_pro;
                public LinearLayout line;
                public ImageView img;
                public int idImage =0;
                public HolderChild(View itemView) {
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
                        urlConnection.setRequestProperty("content-token",params[1]);


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

                        ArrayList<String>arr = new ArrayList<>();
                        arr.add(ob.getString("a"));
                        count+=Integer.valueOf(arr.get(0));
                        arr.add(ob.getString("b"));
                        count+=Integer.valueOf(arr.get(1));
                        arr.add(ob.getString("c"));
                        count+=Integer.valueOf(arr.get(2));
                        if(arrayHoder.size()==4) {
                            arr.add(ob.getString("d"));
                            count += Integer.valueOf(arr.get(3));
                        }
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
                    for(int i= 0;i<arrayHoder.size();i++){
                        float value = Float.valueOf(str.get(i))/count *100;
                        arrayHoder.get(i).text_pro.setText(String.format("%.2f", value)+"%");
                        arrayHoder.get(i).text_pro.setVisibility(View.VISIBLE);
                        arrayHoder.get(i).img.setVisibility(View.VISIBLE);
                        arrayHoder.get(i).line.setVisibility(View.VISIBLE);
                        arrayHoder.get(i).line.setGravity(Gravity.CENTER);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)(300*value/100),30);
                        params.gravity =Gravity.CENTER;
                        arrayHoder.get(i).img.setLayoutParams(params);
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

                                        if(arrayHoder.get(finalI).idImage == R.mipmap.icon_true){
                                            paint.setColor(Color.GREEN);
                                        }
                                        else if(arrayHoder.get(finalI).idImage == R.mipmap.icon_false){
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
                                arrayHoder.get(finalI).img.setImageDrawable(draw);

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
}
