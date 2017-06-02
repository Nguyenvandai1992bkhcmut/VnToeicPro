package part5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.MainActivity;
import com.vntoeic.bkteam.vntoeicpro.R;

import model.ModelPartResult;
import sqlite.AdapterPart;
import sqlite.SqlitePart5;

/**
 * Created by dainguyen on 6/1/17.
 */

public class Part5Activity extends AppCompatActivity implements AdapterPart.ICallActivity{

    public ModelPartResult[]data;
    private RecyclerView recyclerView ;
    private TextView text_part;
    private ImageView img_back;
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
                Part5Activity.this.overridePendingTransition(R.anim.right_left_in , R.anim.right_left_out);
            }
        });
    }

    @Override
    public void funCallActivity(int idsubject) {
        Intent intent = new Intent(this,Part5PractiseAcitvity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("subject",idsubject);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
