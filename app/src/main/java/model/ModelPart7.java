package model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import sqlite.SqlitePart7;
import supportview.ConvertTagView;

/**
 * Created by dainguyen on 7/5/17.
 */

public class ModelPart7 implements Serializable,IDataPart,IListenPart {


    private int id ;
    private String explan;
    private int level;
    private int time;
    private ArrayList<Passage>passages;
    private ArrayList<Question>questions;
    private int index ;
    public final static String LINKIMAGE="http://vntoeic.com/api/v1/part7/image/";
    public final static  String LINKSRC="/data/user/0/com.vntoeic.bkteam.vntoeicpro/part7/image/";

    public ModelPart7(int id , String explan , int level  , int time){
        this.id = id ;
        this.explan = explan;
        this.level = level;
        this.time = time;
        this.passages = new ArrayList<>();
        questions = new ArrayList<>();
        index = 1;

    }

    public void addPassage(int id , int istext , String token , String content){
        Passage passage = new Passage(id,istext,token,content);
        this.passages.add(passage);
    }
    public void addQuestion(int id,  String question , String a , String b , String c, String d , String sol){
        Question question1 = new Question(index,id,question,a,b,c,d,sol);
        index++;
        this.questions.add(question1);
    }

    public String getExplan() {
        return explan;
    }

    @Override
    public ModelWord[] getListWord() {
        SqlitePart7 sqlitePart7 = new SqlitePart7();
        return sqlitePart7.searchWordPart(7,this.id);
    }

    @Override
    public String getContent() {
        return null;
    }


    public void setExplan(String explan) {
        this.explan = explan;
    }

    @Override
    public String getQuestion(int index) {
        return questions.get(index).getQuestion();
    }

    @Override
    public int getCountQuestion() {
        return questions.size();
    }

    @Override
    public String getA(int index) {
        return questions.get(index).getA();
    }

    @Override
    public String getB(int index) {
        return questions.get(index).getB();
    }

    @Override
    public String getC(int index) {
        return questions.get(index).getC();
    }

    @Override
    public String getD(int index) {
        return questions.get(index).getD();
    }

    @Override
    public String getSol(int index) {
        return questions.get(index).getSol();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getSrcFile() {
        return "";
    }

    @Override
    public String getLinkDowload() {
        return "";
    }

    @Override
    public String getLinkDowloadImage() {
        return LINKIMAGE;
    }

    @Override
    public String getLinkFigure(int numberQuestion) {
        return "Http://vntoeic.com/api/v1/part7/result/"+String.valueOf(id)+"/"+String.valueOf(numberQuestion);
    }

    @Override
    public String getSrcFileImage() {
        return LINKSRC;
    }

    @Override
    public int getCountAnswer() {
        return 4;
    }

    public void setUpContentPart7(Context context ,LinearLayout linearLayout) {
        for(int i =0;i<this.getPassages().size();i++) {
            if (this.getPassages().get(i).getIstext() == 1) {
                ConvertTagView convertTagView = new ConvertTagView(context, this.getPassages().get(i).getContent());
                TextView textView = new TextView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                textView.setLayoutParams(params);
                textView.setTextColor(Color.WHITE);

                textView.setText(convertTagView.getSpannableString());
                linearLayout.addView(textView, i);
            } else {
                ImageView imageView = new ImageView(context);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.CENTER;
                imageView.setLayoutParams(params);
                imageView.setBackgroundColor(Color.GREEN);
                imageView.setScaleType(ImageView.ScaleType.CENTER);

                linearLayout.addView(imageView, i);
                File file = new File(LINKSRC+this.getPassages().get(i).getToken()+".png");
                imageView.setImageBitmap(getBitmap(context,file));

            }
        }
    }

    @Override
    public View getViewContent(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        ViewGroup.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(params);
        setUpContentPart7(context,linearLayout);
        return linearLayout;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<Passage> getPassages() {
        return passages;
    }

    public void setPassages(ArrayList<Passage> passages) {
        this.passages = passages;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public class Passage implements Serializable{
        private int id ;
        private int istext;
        private String token ;
        private String content;


        public Passage(int id, int istext, String token, String content) {
            this.id = id;
            this.istext = istext;
            this.token = token;
            this.content = content;
        }

        public int getIstext() {
            return istext;
        }

        public void setIstext(int istext) {
            this.istext = istext;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public class Question implements Serializable,IDataQuestion{
        private int index;
        private int id;
        private String question;
        private String a;
        private String b;
        private String c;
        private String d;
        private String sol;

        public Question(int index,int id, String question, String a, String b, String c, String d, String sol) {
            this.id = id;
            this.question = question;
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.sol = sol;
            this.index = index;
        }

        @Override
        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        @Override
        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        @Override
        public String getC() {
            return c;
        }

        public void setC(String c) {
            this.c = c;
        }

        @Override
        public String getD() {
            return d;
        }

        @Override
        public int getColorTextQuestion() {
            return Color.YELLOW;
        }

        @Override
        public String getLinkFigure() {
            return  "Http://vntoeic.com/api/v1/part7/result/"+String.valueOf(ModelPart7.this.getId())+"/"+String.valueOf(index);
        }

        public void setD(String d) {
            this.d = d;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        @Override
        public String getSol() {
            return sol;
        }

        public void setSol(String sol) {
            this.sol = sol;
        }
    }

    public Bitmap getBitmap(Context context,File data){
        Bitmap bitmap =null;
        Bitmap bitmap1= null;
        try {
            InputStream inputStream =new FileInputStream(data);
            bitmap = BitmapFactory.decodeStream(inputStream);
            if(bitmap!=null) {
                float W = context.getResources().getDisplayMetrics().widthPixels * 0.95f;
                float heso = W / (float) bitmap.getWidth();
                float h = (float) (heso * bitmap.getHeight());
                bitmap1 = Bitmap.createScaledBitmap(bitmap, (int) W, (int) h, false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap1;
    }



}
