package com.vntoeic.bkteam.vntoeicpro.Vocabulary.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vntoeic.bkteam.vntoeicpro.BaseActivity;
import com.vntoeic.bkteam.vntoeicpro.R;

/**
 * Created by giang on 5/28/17.
 */

public class TestActivity extends BaseActivity{
    @Override
    public void setUpContent() {
        topNavi.setTitles(R.array.title_menu2);
        bottomNavi.setTitles(R.array.title_menu2);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_main, mFrameLayout);
//        mFrameLayout.addView(view);

        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestActivity.this, "on click text", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onOpen() {
        topNavi.setVisibility(View.VISIBLE);
        bottomNavi.setVisibility(View.VISIBLE);

        topNavi.onOpen();
        bottomNavi.onOpen();
    }

    @Override
    public void onClose() {
        topNavi.onClose();
        bottomNavi.onClose();
    }


}
