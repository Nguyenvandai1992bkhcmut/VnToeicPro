package model;

import java.io.Serializable;

/**
 * Created by dainguyen on 2/22/17.
 */

public class ModelPart5 implements Serializable {


    private int id;
    private String question;
    private String a ;
    private String b;
    private String c;
    private String d;
    private String result;
    private String explain;
    private int level;
    private int time;

    public ModelPart5(int id, String question, String a, String b, String c, String d,  String result, String explain, int level, int time) {

        this.id = id;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.result = result;
        this.level = level;
        this.explain = explain;
        this.question = question;
        this.time = time;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
