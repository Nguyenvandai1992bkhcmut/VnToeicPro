package part6;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.MainActivity;
import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;

import model.ModelPartResult;
import part5.Part5Activity;


import sqlite.SqlitePart5;
import sqlite.SqlitePart6;
import supportview.AdapterPart;
import supportview.PartPractiseAcitvity;
import supportview.PartReviewActivity;

/**
 * Created by dainguyen on 6/17/17.
 */

public class Part6Activity extends AppCompatActivity implements AdapterPart.ICallActivity{

    public ModelPartResult[]data;
    private RecyclerView recyclerView ;
    private TextView text_part;
    private ImageView img_back;

    private int flag =0;
    public  int time =0;
    private ArrayList<Integer> question;
    private String choosen;
    private int begin=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part5);

        getData();
        setUpLayout();
    }



    public void getData(){
        SqlitePart6 sqlite =new SqlitePart6();
        data=sqlite.searhPartSubjectResult(6);

    }

    public void setUpLayout(){
        text_part = (TextView)findViewById(R.id.text_part);
        text_part.setTypeface(MainActivity.typeface);
        text_part.setText("Part6");
        img_back= (ImageView)findViewById(R.id.img_back);
        recyclerView = (RecyclerView)findViewById(R.id.recycle);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        AdapterPart adapterPart = new AdapterPart(this,data);
        recyclerView.setAdapter(adapterPart);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                Part6Activity.this.overridePendingTransition(R.anim.right_left_in , R.anim.right_left_out);
            }
        });

    }

    @Override
    public void funCallActivity(int idsubject, String title) {
        final Intent intent = new Intent(this, PartPractiseAcitvity.class);
        Intent intent1 = new Intent(this, PartReviewActivity.class);
        final Bundle bundle = new Bundle();
        switch (idsubject){
            case -4:
                bundle.putInt("part",6);
                bundle.putInt("mode",1);
                bundle.putInt("key",0);
                bundle.putString("title",title);
                final Dialog dialog = new Dialog(Part6Activity.this,R.style.ActivityDialog);
                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.width=WindowManager.LayoutParams.WRAP_CONTENT;
                params.height=WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.setContentView(R.layout.dialog_time_part5);
                dialog.getWindow().setWindowAnimations(R.style.PauseDialogAnimation);
                if(!dialog.isShowing()) {
                    dialog.show();
                    dialog.getWindow().setAttributes(params);
                    CheckReference();

                    final SeekBar seekBar = (SeekBar) dialog.findViewById(R.id.seekbar_time);
                    seekBar.setMax(10);
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
                    textTime.setText("Time: " + String.valueOf(20) + " minutes");

                    TextView textStart = (TextView) dialog.findViewById(R.id.text_start);
                    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                            textTime.setText("Time: " + String.valueOf(10 + progress) + " minutes");

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
                            bundle.putInt("time", 10 + seekBar.getProgress());
                            intent.putExtras(bundle);
                            clearReference();
                            startActivityForResult(intent, 99);
                        }
                    });

                }
                break;
            case -3:
                bundle.putInt("part",6);
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
                bundle.putInt("part",6);
                bundle.putInt("mode",3);
                bundle.putInt("part",6);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
            case -1:
               /*
               Activty History
                */
                bundle.putInt("part",6);
                bundle.putInt("mode",4);
                bundle.putInt("part",6);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
            default:
                bundle.putInt("part",6);
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
        editor.putInt("flag6",0);
        editor.commit();

    }

    public void CheckReference(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        flag = sharedPref.getInt("flag6",0);
        if(flag==1){
            time = sharedPref.getInt("time6",1);
            String Squestion = sharedPref.getString("question6","");
            String arr[] = Squestion.split("!");
            question = new ArrayList<>();

            for(int i =0;i<arr.length;i++){
                if(arr[i].length()>0 ){
                    question.add(Integer.valueOf(arr[i]));
                }
            }
            choosen = sharedPref.getString("choose6","");
            begin = sharedPref.getInt("begin6",0);
        }
    }
    public  void saveTest(int timesave , ArrayList<Integer>question ,String choose, int begin){

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("flag6",1);
        editor.putInt("time6",timesave);
        editor.putInt("begin6",begin);
        String Squestion ="";

        for(int i=0;i<question.size();i++){
            if(i==0){
                Squestion +=String.valueOf(question.get(i));

            }
            else {
                Squestion +="!"+ String.valueOf(question.get(i)) ;

            }
        }
        editor.putString("question6",Squestion);
        editor.putString("choose6",choose);
        editor.commit();

    }
}
