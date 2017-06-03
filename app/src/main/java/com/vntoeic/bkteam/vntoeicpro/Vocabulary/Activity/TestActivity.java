package com.vntoeic.bkteam.vntoeicpro.Vocabulary.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.vntoeic.bkteam.vntoeicpro.R;
import com.vntoeic.bkteam.vntoeicpro.Reading.Fragment.PassageFragment;

import model.ModelPart7;
import model.ModelPart7Passage;

/**
 * Created by giang on 6/1/17.
 */

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        ModelPart7Passage passage1 = new ModelPart7Passage();
        passage1.setIsText(1);
        passage1.setContent("hahahahah passage1");

        ModelPart7Passage passage2 = new ModelPart7Passage();
        passage2.setIsText(0);
        passage2.setToken("token");
        ModelPart7Passage[] passages = {passage1, passage2};
        ModelPart7 part7 = new ModelPart7(1, passages);
        PassageFragment passageFragment = PassageFragment.getInstance(part7);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout, passageFragment).commit();

    }
}
