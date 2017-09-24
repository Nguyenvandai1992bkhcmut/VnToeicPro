package feeds;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;

/**
 * Created by dainguyen on 9/4/17.
 */

public class ActivityImageComment extends AppCompatActivity{
    private ViewPager viewPager ;
    private TextView text_done;
    private TextView text_save;
    private ArrayList<String>src;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_comment);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            src=bundle.getStringArrayList("data");
        }
        setUpLayout();
    }

    public void setUpLayout(){
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        AdapterImageComment adapterImageComment = new AdapterImageComment(getSupportFragmentManager(),src);
        viewPager.setAdapter(adapterImageComment);

        text_done = (TextView)findViewById(R.id.text_done);
        text_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        text_save =(TextView)findViewById(R.id.text_save);
    }

}
