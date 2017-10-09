package model;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import sqlite.SqlitePart6;
import supportview.ConvertTagView;

/**
 * Created by dainguyen on 6/16/17.
 */

public class ModelPart6   implements Serializable,IDataPart,IListenPart {
    private int id;
    private String token;
    private String content;
    private ArrayList<Part6Question>arrQuestion;
    private String explan;
    private int level;
    private int time;
    private final String LINKFIGURE ="Http://vntoeic.com/api/v1/part6/result/";

    public ModelPart6(int id ,String token, String content ,
                       String a1 , String b1 , String c1 , String d1 , String sol1,
                       String a2 , String b2 , String c2 , String d2 , String sol2,
                       String a3 , String b3 , String c3 , String d3 , String sol3,
                      String explan,int level, int time){
        this.id = id ;
        this.token = token;
        this.content = content;
        arrQuestion = new ArrayList<>();
        arrQuestion.add(new Part6Question(1,a1,b1,c1,d1,sol1));
        arrQuestion.add(new Part6Question(2,a2,b2,c2,d2,sol2));
        arrQuestion.add(new Part6Question(3,a3,b3,c3,d3,sol3));
        this.explan = explan;
        this.level = level;
        this.time = time;
    }

    public ArrayList<Part6Question> getArrQuestion() {
        return arrQuestion;
    }

    public void setArrQuestion(ArrayList<Part6Question> arrQuestion) {
        this.arrQuestion = arrQuestion;
    }

    @Override
    public String getQuestion(int index) {
        return arrQuestion.get(index).getQuestion();
    }

    @Override
    public int getCountQuestion() {
        return arrQuestion.size();
    }

    @Override
    public String getA(int index) {
        return arrQuestion.get(index).getA();
    }

    @Override
    public String getB(int index) {
        return arrQuestion.get(index).getB();
    }

    @Override
    public String getC(int index) {
        return arrQuestion.get(index).getC();
    }

    @Override
    public String getD(int index) {
        return arrQuestion.get(index).getD();
    }

    @Override
    public String getSol(int index) {
        return arrQuestion.get(index).getSol();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getPart() {
        return 6;
    }

    @Override
    public String getSrcFile() {
        return null;
    }

    @Override
    public String getLinkDowload() {
        return null;
    }

    @Override
    public String getLinkDowloadImage() {
        return null;
    }

    @Override
    public String getLinkFigure(int numberQuestion) {
        return LINKFIGURE+String.valueOf(id)+"/"+String.valueOf(numberQuestion);
    }

    @Override
    public String getSrcFileImage() {
        return null;
    }

    @Override
    public int getCountAnswer() {
        return 4;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public View getViewContent(Context context) {
        TextView textView = new TextView(context);
        textView.setTextColor(Color.WHITE);
        ConvertTagView convertTagView = new ConvertTagView(context,content);
        textView.setText(convertTagView.getSpannableString());
        return textView;
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




    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getExplan() {
        return explan;
    }

    @Override
    public ModelWord[] getListWord() {
        SqlitePart6 sqlitePart6 = new SqlitePart6();
        return sqlitePart6.searchWordPart(6,this.id);
    }



    public void setExplan(String explan) {
        this.explan = explan;
    }

    public class Part6Question implements Serializable,IDataQuestion{
        public int index;
        public  String a;
        public String b;
        public String c;
        public String d;
        public String sol;

        public Part6Question( int index ,String a, String b, String c, String d, String sol) {
            this.index = index;
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.sol = sol;
        }

        @Override
        public String getSol() {
            return this.sol;
        }

        @Override
        public String getQuestion() {
            return "Question :" +String.valueOf(index)+"/3";
        }

        @Override
        public String getA() {
            return a;
        }

        @Override
        public String getB() {
            return b;
        }

        @Override
        public String getC() {
            return c;
        }

        @Override
        public String getD() {
            return d;
        }

        @Override
        public String getToken() {
            return ModelPart6.this.getToken();
        }

        @Override
        public int getColorTextQuestion() {
            return Color.YELLOW;
        }

        @Override
        public String getLinkFigure() {
            return ModelPart6.this.getLinkFigure(index);
        }
    }
}
