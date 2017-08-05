package part5;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.MainActivity;
import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.ModelPartResult;
import supportview.AdapterPart;
import sqlite.SqlitePart5;
import supportview.PartPractiseAcitvity;
import supportview.PartReviewActivity;

import static android.R.attr.defaultValue;

/**
 * Created by dainguyen on 6/1/17.
 */

public class Part5Activity extends AppCompatActivity implements AdapterPart.ICallActivity{

    public ModelPartResult[]data;
    private RecyclerView recyclerView ;
    private TextView text_part;

    private int flag =0;
    public  int time =0;
    private ArrayList<String> question;
    private ArrayList<String> choosen;
    private int begin=0;
    private Dialog dialog;
    WindowManager.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part5);

        getData();
        setUpLayout();
    }



    public void getData(){
        SqlitePart5 sqlite =new SqlitePart5();
        data=sqlite.searhPartSubjectResult(5);



    }

    public void setUpLayout(){
        text_part = (TextView)findViewById(R.id.text_part);
        text_part.setTypeface(MainActivity.typeface);

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
                bundle.putInt("part",5);
                bundle.putInt("mode",1);
                bundle.putInt("key",0);
                bundle.putString("title",title);
                if(dialog==null) {
                    dialog = new Dialog(Part5Activity.this, R.style.ActivityDialog);
                    params = dialog.getWindow().getAttributes();
                    int width = (int) (Part5Activity.this.getApplicationContext().getResources().getDisplayMetrics().widthPixels * 0.8);
                    params.width = width;
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    dialog.setContentView(R.layout.dialog_time_part5);
                    dialog.getWindow().setWindowAnimations(R.style.PauseDialogAnimation);
                }
                if(!dialog.isShowing()) {
                    dialog.show();
                    dialog.getWindow().setAttributes(params);
                    CheckReference();

                    final SeekBar seekBar = (SeekBar) dialog.findViewById(R.id.seekbar_time);
                    seekBar.setMax(20);
                    seekBar.setProgress(20);
                    final TextView text_continue = (TextView) dialog.findViewById(R.id.text_continue);
                    if (flag == 1) {
                        text_continue.setVisibility(View.VISIBLE);
                        text_continue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bundle.putInt("key", 1);
                                bundle.putInt("begin", begin);
                                bundle.putInt("time", time);
                                bundle.putStringArrayList("question", question);
                                bundle.putStringArrayList("choose", choosen);
                                intent.putExtras(bundle);
                                dialog.dismiss();
                                clearReference();
                                startActivityForResult(intent, 99);
                            }
                        });
                    }
                    final TextView textTime = (TextView) dialog.findViewById(R.id.text_time);
                    TextView textStart = (TextView) dialog.findViewById(R.id.text_start);
                    textTime.setText("Time: " + String.valueOf(40) + " minutes");
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
                bundle.putInt("part",5);
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
                bundle.putInt("part",5);
                bundle.putInt("mode",3);
                bundle.putInt("part",5);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
            case -1:
               /*
               Activty History
                */
                bundle.putInt("part",5);
                bundle.putInt("mode",4);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
            default:
                bundle.putInt("part",5);
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
                saveTest(bundle.getInt("time"),bundle.getIntegerArrayList("question"),bundle.getStringArrayList("choose"),bundle.getInt("begin"));
            }

        }
    }

    public void clearReference(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("flag5",0).commit();

    }

    public void CheckReference(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        flag = sharedPref.getInt("flag5",0);
        if(flag==1){
            time = sharedPref.getInt("time5",1);
            String Squestion = sharedPref.getString("question5","");
            String Schoose = sharedPref.getString("choose5","");
            String arr[] = Squestion.split("!");
            String arr1[]= Schoose.split("!");
            question = new ArrayList<>();
            choosen= new ArrayList<>();
            for(int i =0;i<arr.length;i++){
                if(arr[i].length()>0 ){
                    question.add(arr[i]);
                    choosen.add(arr1[i]);
                }
            }

            begin = sharedPref.getInt("begin5",0);
        }
    }
    public  void saveTest(int timesave , ArrayList<Integer>question , ArrayList<String>choose, int begin){

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("flag5",1);
        editor.putInt("time5",timesave);
        editor.putInt("begin5",begin);
        String Squestion ="";
        String Schoose= "";
        for(int i=0;i<question.size();i++){
            if(i==0){
                Squestion +=String.valueOf(question.get(i));
                Schoose += choose.get(i);
            }
            else {
                Squestion +="!"+ String.valueOf(question.get(i)) ;
                Schoose+="!"+choose.get(i);
            }
        }
        editor.putString("question5",Squestion);
        editor.putString("choose5",Schoose);
        editor.commit();

    }

}