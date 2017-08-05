package model;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import sqlite.ManagerPart;

/**
 * Created by dainguyen on 6/14/17.
 */
public class ModelPart3 implements Serializable,IListenPart,IDataPart {
    private int id;
    private String token;
    private String script;
    private ArrayList<Part3Question> arrQuestion;
    private int level;
    private int time;

    private final String LINKAUDIO="http://vntoeic.com/api/v1/part3/sound/";

    public ModelPart3(int id , String token , String script ,
                      String q1 , String a1 , String b1 , String c1 , String d1 ,String sol1,
                      String q2 , String a2 , String b2 , String c2 , String d2 ,String sol2,
                      String q3 , String a3 , String b3 , String c3 , String d3 ,String sol3,
                      int level, int time){
        this.id = id ;
        this.token = token;
        this.script = script;
        arrQuestion = new ArrayList<>();
        arrQuestion.add(new Part3Question(q1,a1,b1,c1,d1,sol1));
        arrQuestion.add(new Part3Question(q2,a2,b2,c2,d2,sol2));
        arrQuestion.add(new Part3Question(q3,a3,b3,c3,d3,sol3));
        this.level = level;
        this.time = time;
    }

    public ArrayList<Part3Question> getArrQuestion() {
        return arrQuestion;
    }

    public void setArrQuestion(ArrayList<Part3Question> arrQuestion) {
        this.arrQuestion = arrQuestion;
    }

    @Override
    public String getQuestion(int index) {
        return arrQuestion.get(index).question;
    }

    @Override
    public int getCountQuestion() {
        return arrQuestion.size();
    }

    @Override
    public String getA(int index) {
        return arrQuestion.get(index).a;
    }

    @Override
    public String getB(int index) {
        return arrQuestion.get(index).b;
    }

    @Override
    public String getC(int index) {
        return arrQuestion.get(index).c;
    }

    @Override
    public String getD(int index) {
        return arrQuestion.get(index).d;
    }

    @Override
    public String getSol(int index) {
        return arrQuestion.get(index).sol;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getExplan() {
        return this.script;
    }

    @Override
    public ModelWord[] getListWord() {
        ManagerPart managerPart = new ManagerPart();
        return  managerPart.searchWordPart(3,getId());

    }

    @Override
    public String getContent() {
        return null;
    }


    @Override
    public View getViewContent(Context context) {
        TextView textView = new TextView(context);
        textView.setText("AAAA");
        textView.setTextColor(Color.WHITE);
        return textView;
    }

    @Override
    public String getSrcFile() {
        return "/data/user/0/com.vntoeic.bkteam.vntoeicpro/part3/audio/"+token+".mp3";
    }

    @Override
    public String getLinkDowload() {
        return LINKAUDIO+token;
    }

    @Override
    public String getLinkDowloadImage() {
        return null;
    }

    @Override
    public String getLinkFigure(int numberQuetion) {
        return "Http://vntoeic.com/api/v1/part3/result/"+String.valueOf(getId())+"/"+String.valueOf(numberQuetion);
    }

    @Override
    public String getSrcFileImage() {
        return null;
    }

    @Override
    public int getCountAnswer() {
        return 4;
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

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public class Part3Question implements Serializable{

        public String question;
        public String a;
        public String b;
        public String c;
        public String d;
        public String sol;

        public Part3Question(String question, String a, String b, String c, String d, String sol) {
            this.question = question;
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.sol = sol;
        }
    }
}


