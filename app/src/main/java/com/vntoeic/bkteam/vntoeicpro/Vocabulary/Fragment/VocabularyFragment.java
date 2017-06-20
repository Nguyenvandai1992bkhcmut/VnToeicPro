package com.vntoeic.bkteam.vntoeicpro.Vocabulary.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import model.ModelSection;
import sqlite.SqliteVocabulary;

/**
 * Created by giang on 6/1/17.
 */

public class VocabularyFragment extends Fragment{

    private Context mContext;
    public OnTagItemClick mListener;
    private RecyclerView mRecyclerView;
    private SqliteVocabulary mSqliteVocabulary = new SqliteVocabulary();
    private ModelSection[] mSections;

    public interface OnTagItemClick {
        void onTagItemClick(int tagId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vocabulary, container,false);

        setUpRecyclerView(view);
        setUpActionBar(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        this.mContext = context;
        if (context instanceof OnTagItemClick) {
            mListener = (OnTagItemClick) context;
        } else throw new ClassCastException("context is not instanceof OnTagItemClick");
        super.onAttach(context);
    }

    private void setUpRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mSections = mSqliteVocabulary.searchAllSection();
        SectionVocabularryAdapter sectionVocabularryAdapter = new SectionVocabularryAdapter(mContext, this, mSections);
        mRecyclerView.setAdapter(sectionVocabularryAdapter);

    }

    public void setUpActionBar(View view){
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.back_btn);
        View customActionBar = LayoutInflater.from(mContext).inflate(R.layout.vocabulary_actionbar, null, false);
        TextView textView = (TextView) customActionBar.findViewById(R.id.textView);
        textView.setText("Vocabulary");
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        actionBar.setCustomView(customActionBar, params);
        actionBar.setDisplayShowCustomEnabled(true);

        Toolbar parent = (Toolbar) customActionBar.getParent();
        parent.setContentInsetsAbsolute(0, 0);
    }
}
