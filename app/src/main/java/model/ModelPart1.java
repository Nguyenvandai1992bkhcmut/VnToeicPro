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
    private final String LINKAUDIO="http://vntoeic.com/api/v1/part1/sound/";
    private final String LINKIMAGE="http://vntoeic.com/api/v1/part1/image/";

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
    public View getViewContent(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,50,0);
        imageView.setLayoutParams(params);
        linearLayout.addView(imageView);

        TaskDowloadImage  taskDowloadImage = new TaskDowloadImage(context,imageView,1);
        taskDowloadImage.execute();

        ImageView imageView1 = new ImageView(context);
        imageView1.setImageResource(R.mipmap.icon_part1_play);
        imageView1.setId(R.id.img_play);
        linearLayout.addView(imageView1);
        return linearLayout;
    }

    public void getImage(Context context,ImageView imageView){
        TaskDowloadImage taskDowloadImage = new TaskDowloadImage(context,imageView,0);
        taskDowloadImage.execute();
    }



    @Override
    public String getSrcFile() {
        return "/data/user/0/com.vntoeic.bkteam.vntoeicpro/part1/audio/"+token+".mp3";
    }

    @Override
    public String getLinkDowload() {
        return LINKAUDIO+token;
    }

    @Override
    public String getLinkDowloadImage() {
        return LINKIMAGE+token;
    }

    @Override
    public String getLinkFigure(int numberQuestion) {
        return "Http://vntoeic.com/api/v1/part1/result/"+String.valueOf(getId());

    }

    @Override
    public String getSrcFileImage() {
        return "/data/user/0/com.vntoeic.bkteam.vntoeicpro/part1/image/"+token+".png";
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


    public class TaskDowloadImage extends AsyncTask<String,Void,Bitmap> {
        private ImageView imageView;
        private int type =0;
        private Context context;
        public TaskDowloadImage(Context context,ImageView imageView,int type){
            this.imageView = imageView;
            this.context= context;
            this.type = type;
        }
        @Override
        protected  Bitmap doInBackground(String... params) {
            Bitmap bitmap= null;
            File dataimage = new File(getSrcFileImage());
            if(dataimage.exists()){
                bitmap= getBitmap(dataimage);
            }else {
               // bitmap = getBitmapFromURL();
            }
            return bitmap;
        }

        public Bitmap getBitmap(File data){
            Bitmap bitmap =null;
            try {
                InputStream inputStream =new FileInputStream(data);
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(bitmap==null)imageView.setImageBitmap(null);
            Bitmap bitmap1;
            if(type==0) {
                float W = context.getResources().getDisplayMetrics().widthPixels * 0.95f;
                float heso = W / (float) bitmap.getWidth();
                float h = (float) (heso * bitmap.getHeight());
                bitmap1= Bitmap.createScaledBitmap(bitmap, (int) W, (int) h, false);
            }else{
                float W = context.getResources().getDisplayMetrics().density*100;
                float heso = W / (float) bitmap.getWidth();
                float h = (float) (heso * bitmap.getHeight());
                bitmap1= Bitmap.createScaledBitmap(bitmap,(int)W,(int)h,false);
            }
            if(bitmap!=null) {
                imageView.setImageBitmap(bitmap1);
            }
        }

        public Bitmap getBitmapFromURL() {
            try {
                URL url = new URL(getLinkDowloadImage());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                return bitmap;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
