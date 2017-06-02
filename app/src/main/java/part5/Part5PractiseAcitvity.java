package part5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vntoeic.bkteam.vntoeicpro.R;

import model.ModelPart5;
import sqlite.AdapterPart;
import sqlite.SqlitePart5;

/**
 * Created by dainguyen on 6/2/17.
 */

public class Part5PractiseAcitvity extends AppCompatActivity{
    private ModelPart5 data[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part5_practise);

        getData();
        setUpLayout();
    }
    public void getData(){
        SqlitePart5 sqlitePart5 = new SqlitePart5();
        data=sqlitePart5.randomPart5(20);

    }

    public void setUpLayout(){
        FragmentPart5 fragmentPart5 = new FragmentPart5();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",data[0]);
        fragmentPart5.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.frame,fragmentPart5).commit();
    }


}
