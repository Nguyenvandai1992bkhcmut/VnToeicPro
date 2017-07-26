package part3;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vntoeic.bkteam.vntoeicpro.MainActivity;
import com.vntoeic.bkteam.vntoeicpro.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import model.ModelPart1;
import model.ModelPart2;
import model.ModelPart3;
import model.ModelPart4;
import model.ModelPartResult;
import part5.Part5Activity;
import sqlite.ManagerPart;
import sqlite.SqlitePart1;
import sqlite.SqlitePart2;
import sqlite.SqlitePart3;
import sqlite.SqlitePart4;
import sqlite.SqlitePart5;
import supportview.AdapterPart;
import supportview.DowloadTask;
import supportview.ListenPraticseActivity;
import supportview.PartPractiseAcitvity;
import supportview.PartReviewActivity;

/**
 * Created by dainguyen on 7/11/17.
 */

public class ListeningActivity extends AppCompatActivity implements AdapterPart.ICallActivity, DowloadTask.IDowload{
    public ModelPartResult[]data;
    private RecyclerView recyclerView ;
    private TextView text_part;

    public  int time =0;
    private ArrayList<Integer> question;
    private String choosen;
    private int begin=0;
    private int part=0;
    private ArrayList<Boolean>arrDowload;
    private Dialog dialog_dowload;
    private Dialog dialog;
    WindowManager.LayoutParams params;
    private int flag=0;

    private Intent intent;
    private Intent intent1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part5);

        getData();
        setUpLayout();
    }



    public void getData(){
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            part=bundle.getInt("part");
            ManagerPart managerPart = new ManagerPart();
            data=  managerPart.searhPartSubjectResult(part);
        }

    }

    public void setUpLayout(){
        text_part = (TextView)findViewById(R.id.text_part);
        text_part.setTypeface(MainActivity.typeface);
        text_part.setText("Part "+ String.valueOf(part));

        recyclerView = (RecyclerView)findViewById(R.id.recycle);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        AdapterPart adapterPart = new AdapterPart(this,data);
        recyclerView.setAdapter(adapterPart);


    }

    @Override
    public void funCallActivity(int idsubject, String title) {
         intent = new Intent(this, ListenPraticseActivity.class);
        intent1 = new Intent(this, PartReviewActivity.class);
        final Bundle bundle = new Bundle();
        switch (idsubject){

            case -4:
                bundle.putInt("part",part);
                bundle.putInt("mode",1);
                bundle.putString("title",title);
                checkReference();
                if(flag==1) {
                    if (dialog == null) {
                        dialog = new Dialog(this, R.style.ActivityDialog);
                        params = dialog.getWindow().getAttributes();
                        int width = (int) (getApplicationContext().getResources().getDisplayMetrics().widthPixels * 0.8);
                        params.width = width;
                        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        dialog.setContentView(R.layout.dialog_test_listen);
                        dialog.getWindow().setWindowAnimations(R.style.PauseDialogAnimation);
                    }
                    if (!dialog.isShowing()) {
                        dialog.show();
                        dialog.getWindow().setAttributes(params);
                        ((TextView)dialog.findViewById(R.id.text_continue)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bundle.putInt("key",1);
                                bundle.putIntegerArrayList("question",question);
                                bundle.putInt("begin",begin);
                                bundle.putString("choose",choosen);
                                intent.putExtras(bundle);
                                clearReference();
                                dialog.dismiss();
                                startActivityForResult(intent,99);
                                return;
                            }
                        });
                        ((TextView)dialog.findViewById(R.id.text_new_test)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                clearReference();
                                bundle.putInt("key",0);
                                intent.putExtras(bundle);
                                prepareData();
                                return;
                            }
                        });
                    }


                }else {

                    bundle.putInt("key",0);
                    intent.putExtras(bundle);
                    prepareData();
                    return;
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

    private void prepareData(){
        switch (part){
            case 1:
                prepareDowloadPart1();
                break;
            case 2:
                prepareDowloadPart2();
                break;
            case 3:
                prepareDowloadPart3();
                break;
            case 4:
                prepareDowloadPart4();
                break;
        }
    }

    private void prepareDowloadPart1() {
        SqlitePart1 sqlite= new SqlitePart1();
        ModelPart1 datapart1[]= sqlite.randomPart1(10);
        arrDowload = new ArrayList<>();
        for(int i=0;i<10;i++ ) {
            File fileAudio = new File(datapart1[i].getSrcFile());
            File fileIMage = new File(datapart1[i].getSrcFileImage());
            if (fileAudio.exists()) arrDowload.add(true);
            else {
                arrDowload.add(false);
                DowloadTask dowloadTask = new DowloadTask();
                dowloadTask.setiDowload(this);
                dowloadTask.execute(String.valueOf(i), datapart1[i].getLinkDowload(), datapart1[i].getSrcFile());
            }
            if (fileIMage.exists()) arrDowload.add(true);
            else {
                arrDowload.add(false);
                DowloadTask dowloadTask = new DowloadTask();
                dowloadTask.setiDowload(this);
                dowloadTask.execute(String.valueOf(i), datapart1[i].getLinkDowloadImage(), datapart1[i].getSrcFileImage());
            }
        }
        if(arrDowload.contains(false)){
            showDialogDowload();
        }else{
            startActivityForResult(intent,99);
        }
    }

    private void prepareDowloadPart2() {
        SqlitePart2 sqlite= new SqlitePart2();
        ModelPart2 datapart2[]= sqlite.randomPart2(10);
        arrDowload = new ArrayList<>();
        for(int i=0;i<10;i++ ) {
            File fileAudio = new File(datapart2[i].getSrcFile());

            if (fileAudio.exists()) arrDowload.add(true);
            else {
                arrDowload.add(false);
                DowloadTask dowloadTask = new DowloadTask();
                dowloadTask.setiDowload(this);
                dowloadTask.execute(String.valueOf(i), datapart2[i].getLinkDowload(), datapart2[i].getSrcFile());
            }

        }
        if(arrDowload.contains(false)){
         showDialogDowload();
        }else{
            startActivityForResult(intent,99);
        }
    }
    private void prepareDowloadPart3() {
        SqlitePart3 sqlite= new SqlitePart3();
        ModelPart3 datapart2[]= sqlite.randomPart3(10);
        arrDowload = new ArrayList<>();
        for(int i=0;i<10;i++ ) {
            File fileAudio = new File(datapart2[i].getSrcFile());

            if (fileAudio.exists()) arrDowload.add(true);
            else {
                arrDowload.add(false);
                DowloadTask dowloadTask = new DowloadTask();
                dowloadTask.setiDowload(this);
                dowloadTask.execute(String.valueOf(i), datapart2[i].getLinkDowload(), datapart2[i].getSrcFile());
            }

        }
        if(arrDowload.contains(false)){
           showDialogDowload();
        }else{
            startActivityForResult(intent,99);
        }
    }

    private void prepareDowloadPart4() {
        SqlitePart4 sqlite= new SqlitePart4();
        ModelPart4 datapart2[]= sqlite.randomPart4(10);
        arrDowload = new ArrayList<>();
        for(int i=0;i<10;i++ ) {
            File fileAudio = new File(datapart2[i].getSrcFile());

            if (fileAudio.exists()) arrDowload.add(true);
            else {
                arrDowload.add(false);
                DowloadTask dowloadTask = new DowloadTask();
                dowloadTask.setiDowload(this);
                dowloadTask.execute(String.valueOf(i), datapart2[i].getLinkDowload(), datapart2[i].getSrcFile());
            }

        }
        if(arrDowload.contains(false)){
           showDialogDowload();
        }else{
            startActivityForResult(intent,99);
        }
    }

    public void showDialogDowload(){
        dialog_dowload = new Dialog(ListeningActivity.this,R.style.ActivityDialog);
        WindowManager.LayoutParams params = dialog_dowload.getWindow().getAttributes();
        int width = (int) (getApplicationContext().getResources().getDisplayMetrics().widthPixels*0.8);
        params.width=width;
        params.height=WindowManager.LayoutParams.WRAP_CONTENT;
        dialog_dowload.setCanceledOnTouchOutside(false);
        dialog_dowload.setContentView(R.layout.dialog_dowload);
        dialog_dowload.getWindow().setWindowAnimations(R.style.PauseDialogAnimation);
        dialog_dowload.show();;
        dialog_dowload.getWindow().setAttributes(params);
    }


    @Override
    public void notifySuccess(int numbertask, boolean b,String url,String file) {
        arrDowload.set(numbertask,b);
        if(b==false){
            Toast.makeText(getApplicationContext(),"Dowload failure. Please check your internet!",Toast.LENGTH_LONG).show();
            return;
        }
        if(!arrDowload.contains(false)){

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog_dowload.dismiss();
                    startActivityForResult(intent,99);
                    return;
                }
            });
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
                saveTest(bundle);
            }

        }
    }
    public void clearReference(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("flag"+String.valueOf(part),0);
        editor.commit();

    }

    public void checkReference(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        flag = sharedPref.getInt("flag"+String.valueOf(part),0);
        if(flag==1){
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
    public  void saveTest(Bundle bunlde){

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("flag"+String.valueOf(part),1);
        editor.putInt("begin"+String.valueOf(part),bunlde.getInt("begin"));
        String Squestion ="";
        question = bunlde.getIntegerArrayList("question");
        choosen = bunlde.getString("choose");
        for(int i=0;i<question.size();i++){
            if(i==0){
                Squestion +=String.valueOf(question.get(i));

            }
            else {
                Squestion +="!"+ String.valueOf(question.get(i)) ;

            }
        }

        editor.putString("question"+String.valueOf(part),Squestion);
        editor.putString("choose"+String.valueOf(part),bunlde.getString("choose"));
        editor.commit();

    }
}
