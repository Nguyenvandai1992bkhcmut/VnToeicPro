package model;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.io.Serializable;
import java.util.ArrayList;

import sqlite.SqlitePart2;

/**
 * Created by dainguyen on 6/14/17.
 */

public class ModelPart2 implements Serializable,IListenPart,IDataPart {
    private int id;
    private String token;
    private String script;
    private String sol;
    private  int level;
    private int time;
    private String question="";
    private String a;
    private String b;
    private String c;

    private final String LINKBASIC ="http://vntoeic.xyz/api/v1/part2s/";
    private final String LINKSRC="/data/user/0/com.vntoeic.bkteam.vntoeicpro/part2/";


    public ModelPart2(int id, String token, String script,String a, String b, String c, String sol, int level, int time) {
        this.id = id;
        this.token = token;
        this.script = script;
        this.sol = sol;
        this.level = level;
        this.time = time;
        this.a = a;
        this.b = b;
        this.c = c;


    }


    @Override
    public String getQuestion(int index) {
        return question;
    }

    @Override
    public int getCountQuestion() {
        return 1;
    }

    @Override
    public String getA(int index) {
        return a;
    }

    @Override
    public String getB(int index) {
        return b;

    }

    @Override
    public String getC(int index) {
        return c;

    }

    @Override
    public String getD(int index) {
        return c;

    }

    @Override
    public String getSol(int index) {
        return sol;
    }

    @Override
    public String getExplan() {
        return "Question :"+script+"\n"+ "Result :"+ sol;
    }

    @Override
    public ModelWord[] getListWord() {
        SqlitePart2 sqlitePart2 = new SqlitePart2();
        return  sqlitePart2.searchWordPart(2,id);

    }

    @Override
    public String getContent() {
        return null;
    }


    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getPart() {
        return 2;
    }


    @Override
    public View getViewContent(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setId(R.id.img_play);
        imageView.setImageResource(R.mipmap.icon_part1_play);
        return imageView;
    }

    @Override
    public String getSrcFile() {
        return LINKSRC+token+".mp3";

    }

    @Override
    public String getLinkDowload() {
        return LINKBASIC+String.valueOf(id)+"/sound";
    }

    @Override
    public String getLinkDowloadImage() {
        return "";
    }

    @Override
    public String getLinkFigure(int numberQuestion) {
        return LINKBASIC+String.valueOf(id)+"/result";
    }

    @Override
    public String getSrcFileImage() {
        return "";
    }

    @Override
    public int getCountAnswer() {
        return 3;
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

    public String getSol() {
        return sol;
    }

    public void setSol(String sol) {
        this.sol = sol;
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

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
