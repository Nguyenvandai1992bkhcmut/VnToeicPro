package model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.vntoeic.bkteam.vntoeicpro.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

import sqlite.SqlitePart1;

/**
 * Created by dainguyen on 5/27/17.
 */

public class ModelPart1 implements Serializable,IListenPart,IDataPart {
    private  int id;
    private String token;
    private String aScript;
    private String bScript;
    private String cScript;
    private String dScript;
    private String sol;
    private int level;
    private int time;
    private final String LINKBASIC ="http://vntoeic.xyz/api/v1/part1s/";
    private final String LINKSRC="/data/user/0/com.vntoeic.bkteam.vntoeicpro/part1/";


    public ModelPart1(int id, String token, String aScript, String bScript, String cScript, String dScript, String sol, int level, int time) {
        this.id = id;
        this.token = token;
        this.aScript = aScript;
        this.bScript = bScript;
        this.cScript = cScript;
        this.dScript = dScript;
        this.sol = sol;
        this.level = level;
        this.time = time;
    }

    public String getaScript() {
        return aScript;
    }

    public void setaScript(String aScript) {
        this.aScript = aScript;
    }

    public String getbScript() {
        return bScript;
    }

    public void setbScript(String bScript) {
        this.bScript = bScript;
    }

    public String getcScript() {
        return cScript;
    }

    public void setcScript(String cScript) {
        this.cScript = cScript;
    }

    public String getdScript() {
        return dScript;
    }

    public void setdScript(String dScript) {
        this.dScript = dScript;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String getQuestion(int index) {
        return "Script of Question";
    }

    @Override
    public int getCountQuestion() {
        return 1;
    }

    @Override
    public String getA(int index) {
        return aScript;
    }

    @Override
    public String getB(int index) {
        return bScript;
    }

    @Override
    public String getC(int index) {
        return cScript;
    }

    @Override
    public String getD(int index) {
        return dScript;
    }

    @Override
    public String getSol(int index) {
        return sol;
    }

    @Override
    public String getExplan() {
        String  script ="";
        script+="A."+aScript+"\n";
        script+="B."+bScript+"\n";
        script+="C."+cScript+"\n";
        script+="D."+dScript+"\n";
        return script;
    }

    @Override
    public ModelWord[] getListWord() {
        SqlitePart1 sqlitePart1 = new SqlitePart1();
        return sqlitePart1.searchWordPart(1,getId());
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
        return 1;
    }


    @Override
    public View getViewContent(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,50,0);
        imageView.setLayoutParams(params);
        linearLayout.addView(imageView);

       // TaskDowloadImage  taskDowloadImage = new TaskDowloadImage(context,imageView,1);
       // taskDowloadImage.execute();

        ImageView imageView1 = new ImageView(context);
        imageView1.setImageResource(R.mipmap.icon_part1_play);
        imageView1.setId(R.id.img_play);
        linearLayout.addView(imageView1);
        return linearLayout;
    }

    public void getImage(Context context,ImageView imageView){
        Bitmap bitmap =null;
        Bitmap bitmap1= null;
        File data = new File(getSrcFileImage());
        try {
            InputStream inputStream =new FileInputStream(data);
            bitmap = BitmapFactory.decodeStream(inputStream);
            if(bitmap!=null) {
                float W = context.getResources().getDisplayMetrics().widthPixels * 0.95f;
                float heso = W / (float) bitmap.getWidth();
                float h = (float) (heso * bitmap.getHeight());
                bitmap1 = Bitmap.createScaledBitmap(bitmap, (int) W, (int) h, false);
                imageView.setImageBitmap(bitmap1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public String getSrcFile() {
        return LINKSRC+"audio/"+token+".mp3";
    }

    @Override
    public String getLinkDowload() {
        return LINKBASIC+String.valueOf(id)+"/sound";
    }

    @Override
    public String getLinkDowloadImage() {
        return LINKBASIC+String.valueOf(id)+"/image";
    }

    @Override
    public String getLinkFigure(int numberQuestion) {
        return LINKBASIC+String.valueOf(id)+"/result";

    }

    @Override
    public String getSrcFileImage() {
        return LINKSRC+"image/"+token+".png";
    }

    @Override
    public int getCountAnswer() {
        return 4;
    }

    public void setId(int id) {
        this.id = id;
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
}
