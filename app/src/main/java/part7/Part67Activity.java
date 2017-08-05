package part7;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.vntoeic.bkteam.vntoeicpro.MainActivity;
import com.vntoeic.bkteam.vntoeicpro.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;

import model.ModelPart7;
import model.ModelPartResult;
import part3.ListeningActivity;
import sqlite.ManagerPart;
import sqlite.SqlitePart6;
import sqlite.SqlitePart7;
import supportview.AdapterPart;
import supportview.DowloadTask;
import supportview.PartPractiseAcitvity;
import supportview.PartReviewActivity;

/**
 * Created by dainguyen on 7/6/17.
 */

public class Part67Activity extends AppCompatActivity implements AdapterPart.ICallActivity {
    public ModelPartResult[]data;
    private RecyclerView recyclerView ;
    private TextView text_part;

    private int flag =0;
    public  int time =0;
    private ArrayList<Integer> question;
    private String choosen;
    private int begin=0;
    private int part;
    private ArrayList<Boolean>arrdowload = new ArrayList<>();
    private Dialog dialog;


    private final String LINKIMAGE="http://vntoeic.com/api/v1/part7/image/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part5);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            part=bundle.getInt("part");
            getData();
        }
        // check
        //prepare data
       //

            setUpLayout();
    }





    public void getData(){
        ManagerPart sqlite =new ManagerPart();
        data=sqlite.searhPartSubjectResult(part);
    }

    public void setUpLayout(){
        text_part = (TextView)findViewById(R.id.text_part);
        text_part.setTypeface(MainActivity.typeface);
        text_part.setText("Part "+String.valueOf(part));

        recyclerView = (RecyclerView)findViewById(R.id.recycle);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        AdapterPart adapterPart = new AdapterPart(this,data);
        recyclerView.setAdapter(adapterPart);


    }

    @Override
    public void funCallActivity(int idsubject, String title) {
        final Intent intent = new Intent(this, PartPractiseAcitvity.class);
        Intent intent1 = new Intent(this, PartReviewActivity.class);
        final Bundle bundle = new Bundle();
        switch (idsubject){
            case -4:
                bundle.putInt("part",part);
                bundle.putInt("mode",1);
                bundle.putInt("key",0);
                bundle.putString("title",title);
                final Dialog dialog = new Dialog(Part67Activity.this,R.style.ActivityDialog);
                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                int width = (int) (Part67Activity.this.getApplicationContext().getResources().getDisplayMetrics().widthPixels*0.8);
                params.width=width;
                params.height=WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.setContentView(R.layout.dialog_time_part5);
                dialog.getWindow().setWindowAnimations(R.style.PauseDialogAnimation);
                if(!dialog.isShowing()) {
                    dialog.show();
                    dialog.getWindow().setAttributes(params);
                    CheckReference();

                    final SeekBar seekBar = (SeekBar) dialog.findViewById(R.id.seekbar_time);
                    seekBar.setMax(20);
                    seekBar.setProgress(10);


                    final TextView text_continue = (TextView) dialog.findViewById(R.id.text_continue);
                    if (flag == 1) {
                        text_continue.setVisibility(View.VISIBLE);
                        text_continue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bundle.putInt("key", 1);
                                bundle.putInt("begin", begin);
                                bundle.putInt("time", time);
                                bundle.putIntegerArrayList("question", question);
                                bundle.putString("choose", choosen);
                                intent.putExtras(bundle);
                                dialog.dismiss();
                                clearReference();
                                startActivityForResult(intent, 99);
                            }
                        });
                    }
                    final TextView textTime = (TextView) dialog.findViewById(R.id.text_time);
                    textTime.setText("Time: " + String.valueOf(20+seekBar.getProgress()) + " minutes");

                    TextView textStart = (TextView) dialog.findViewById(R.id.text_start);
                    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                            textTime.setText("Time: " + String.valueOf(20 + progress) + " minutes");

                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });
                    textStart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            bundle.putInt("time", 20 + seekBar.getProgress());
                            intent.putExtras(bundle);
                            clearReference();
                            startActivityForResult(intent, 99);
                        }
                    });

                }
                break;
            case -3:
                bundle.putInt("part",part);
                bundle.putInt("mode",0);
                bundle.putInt("key",0);
                bundle.putString("title",title);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case -2:
               /*
               Activity favorite
                */
                bundle.putInt("part",part);
                bundle.putInt("mode",3);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
            case -1:
               /*
               Activty History
                */
                bundle.putInt("part",part);
                bundle.putInt("mode",4);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
            default:
                bundle.putInt("part",part);
                bundle.putInt("mode",2);
                bundle.putInt("key",0);
                bundle.putInt("subject",idsubject);
                bundle.putString("title",title);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==99 && data!=null)
        {
            clearReference();
            Bundle bundle = data.getExtras();
            if(bundle!=null){
                saveTest(bundle.getInt("time"),bundle.getIntegerArrayList("question"),bundle.getString("choose"),bundle.getInt("begin"));
            }

        }
    }

    public void clearReference(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("flag"+String.valueOf(part),0);
        editor.commit();

    }

    public void CheckReference(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        flag = sharedPref.getInt("flag"+String.valueOf(part),0);
        if(flag==1){
            time = sharedPref.getInt("time"+String.valueOf(part),1);
            String Squestion = sharedPref.getString("question"+String.valueOf(part),"");
            String arr[] = Squestion.split("!");
            question = new ArrayList<>();

            for(int i =0;i<arr.length;i++){
                if(arr[i].length()>0 ){
                    question.add(Integer.valueOf(arr[i]));
                }
            }
            choosen = sharedPref.getString("choose"+String.valueOf(part),"");
            begin = sharedPref.getInt("begin"+String.valueOf(part),0);
        }
    }
    public  void saveTest(int timesave , ArrayList<Integer>question ,String choose, int begin){

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("flag"+String.valueOf(part),1);
        editor.putInt("time"+String.valueOf(part),timesave);
        editor.putInt("begin"+String.valueOf(part),begin);
        String Squestion ="";

        for(int i=0;i<question.size();i++){
            if(i==0){
                Squestion +=String.valueOf(question.get(i));

            }
            else {
                Squestion +="!"+ String.valueOf(question.get(i)) ;

            }
        }
        editor.putString("question"+String.valueOf(part),Squestion);
        editor.putString("choose"+String.valueOf(part),choose);
        editor.commit();

    }




    public class  Task extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... params) {
            getBitmapFromURL(params[0]);
            return null;
        }
        public void getBitmapFromURL(String src) {
            File data =new File("/data/user/0/com.vntoeic.bkteam.vntoeicpro/part7/image/"+src+".png");
            if(!data.exists()) {
                try {
                    URL url = new URL(LINKIMAGE + src);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();

                    try {
                        data.createNewFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Boolean b = data.createNewFile();

                    OutputStream outputStream = new FileOutputStream(data);
                    byte[] arr = new byte[1024];
                    int length = 0;
                    while ((length = input.read(arr)) > 0) {
                        outputStream.write(arr, 0, length);
                    }
                    System.out.println("Thanh cong");

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }



}
