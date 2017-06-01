package com.vntoeic.bkteam.vntoeicpro.Vocabulary.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;
import com.vntoeic.bkteam.vntoeicpro.Vocabulary.Adapter.SectionVocabularryAdapter;
import com.vntoeic.bkteam.vntoeicpro.Vocabulary.Fragment.ViewPagerTagFragment;
import com.vntoeic.bkteam.vntoeicpro.Vocabulary.Fragment.VocabularyFragment;

import model.ModelSection;
import sqlite.SqliteVocabulary;

/**
 * Created by giang on 5/28/17.
 */

public class VocabularyActivity extends AppCompatActivity implements VocabularyFragment.OnTagItemClick{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        Fragment savedFragment = getSupportFragmentManager().findFragmentById(R.id.contentLayout);
        if (savedFragment == null) {
            VocabularyFragment vocabularyFragment = new VocabularyFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.add(R.id.contentLayout, vocabularyFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onTagItemClick(int tagId) {

        ViewPagerTagFragment fragment = new ViewPagerTagFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ViewPagerTagFragment.TAG_ID, tagId);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentLayout, fragment)
                .addToBackStack(null)
                .commit();
    }
}
