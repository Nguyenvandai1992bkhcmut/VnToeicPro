package model;

import java.io.Serializable;
import java.util.ArrayList;

import sqlite.SqlitePart6;

/**
 * Created by dainguyen on 6/16/17.
 */

public class ModelPart6   implements Serializable,IDataPart {
    private int id;
    private String content;
    private ArrayList<Part6Question>arrQuestion;
    private String explan;
    private int level;
    private int time;

    public ModelPart6(int id , String content ,
                       String a1 , String b1 , String c1 , String d1 , String sol1,
                       String a2 , String b2 , String c2 , String d2 , String sol2,
                       String a3 , String b3 , String c3 , String d3 , String sol3,
                      String explan,int level, int time){
        this.id = id ;
        this.content = content;
        arrQuestion = new ArrayList<>();
        arrQuestion.add(new Part6Question(a1,b1,c1,d1,sol1));
        arrQuestion.add(new Part6Question(a2,b2,c2,d2,sol2));
        arrQuestion.add(new Part6Question(a3,b3,c3,d3,sol3));
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
    public int getId() {
        return id;
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

    public class Part6Question implements Serializable{

        public String a;
        public String b;
        public String c;
        public String d;
        public String sol;

        public Part6Question( String a, String b, String c, String d, String sol) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.sol = sol;
        }
    }
}
