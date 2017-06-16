package model;

import java.util.ArrayList;

/**
 * Created by dainguyen on 6/14/17.
 */

public class ModelPart3 {
    private int id;
    private String token;
    private String script;
    private ArrayList<Part3Question>arrQuestion;
    private int level;
    private int time;

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

    public class Part3Question{
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


