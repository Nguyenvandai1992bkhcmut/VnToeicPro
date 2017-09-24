package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dainguyen on 6/14/17.
 */

public class ModelPart4 implements Serializable,IListenPart{
    private int id;
    private String token;
    private String script;
    private ArrayList<Part4Question>arrQuestion;
    private int level;
    private int time;
<<<<<<< HEAD
    private final String LINKAUDIO="http://vntoeic.com/api/v1/part4/sound/";
=======
    private final String LINKBASIC ="http://vntoeic.xyz/api/v1/part4s/";
    private final String LINKSRC="/data/user/0/com.vntoeic.bkteam.vntoeicpro/part4/";
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce


    public ModelPart4(int id , String token , String script ,
                      String q1 , String a1 , String b1 , String c1 , String d1 , String sol1,
                      String q2 , String a2 , String b2 , String c2 , String d2 , String sol2,
                      String q3 , String a3 , String b3 , String c3 , String d3 , String sol3,
                      int level, int time){
        this.id = id ;
        this.token = token;
        this.script = script;
        arrQuestion = new ArrayList<>();
        arrQuestion.add(new Part4Question(q1,a1,b1,c1,d1,sol1));
        arrQuestion.add(new Part4Question(q2,a2,b2,c2,d2,sol2));
        arrQuestion.add(new Part4Question(q3,a3,b3,c3,d3,sol3));
        this.level = level;
        this.time = time;
    }

    public ArrayList<Part4Question> getArrQuestion() {
        return arrQuestion;
    }

    public void setArrQuestion(ArrayList<Part4Question> arrQuestion) {
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

    public int getId() {
        return id;
    }

    @Override
    public String getSrcFile() {
<<<<<<< HEAD
        return "/data/user/0/com.vntoeic.bkteam.vntoeicpro/part4/audio/"+token+".mp3";
=======
        return LINKSRC+token+".mp3";
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce

    }

    @Override
    public String getLinkDowload() {
<<<<<<< HEAD
        return LINKAUDIO+token;
=======
        return LINKBASIC+String.valueOf(id)+"/sound";
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce

    }

    @Override
    public String getLinkDowloadImage() {
        return null;
    }

    @Override
    public String getLinkFigure(int numberQuestion) {
<<<<<<< HEAD
        return "Http://vntoeic.com/api/v1/part4/result/"+String.valueOf(getId())+"/"+String.valueOf(numberQuestion);
=======
        return LINKBASIC+String.valueOf(id)+"/results/"+String.valueOf(numberQuestion);
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce
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

    public class Part4Question implements Serializable{
        public String question;
        public String a;
        public String b;
        public String c;
        public String d;
        public String sol;

        public Part4Question(String question, String a, String b, String c, String d, String sol) {
            this.question = question;
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.sol = sol;
        }
    }
}


