package com.vntoeic.bkteam.vntoeicpro.Vocabulary.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
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

import java.util.ArrayList;

import model.ModelSection;
import sqlite.SqliteVocabulary;

/**
 * Created by giang on 5/28/17.
 */

public class VocabularyActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SqliteVocabulary mSqliteVocabulary = new SqliteVocabulary();
    private ModelSection[] mSections;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        setUpRecyclerView();
        setUpActionBar();

    }

    private void setUpRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSections = mSqliteVocabulary.searchAllSection();
        SectionVocabularryAdapter sectionVocabularryAdapter = new SectionVocabularryAdapter(this, mSections);
        mRecyclerView.setAdapter(sectionVocabularryAdapter);

    }

    public void setUpActionBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.back_btn);
        View view = LayoutInflater.from(this).inflate(R.layout.vocabulary_actionbar, null, false);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText("Vocabulary");
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        actionBar.setCustomView(view, params);
        actionBar.setDisplayShowCustomEnabled(true);

        Toolbar parent = (Toolbar) view.getParent();
        parent.setContentInsetsAbsolute(0, 0);
    }
}
