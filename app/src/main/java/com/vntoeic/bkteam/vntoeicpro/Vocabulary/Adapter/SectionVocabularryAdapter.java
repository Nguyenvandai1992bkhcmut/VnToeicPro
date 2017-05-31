package com.vntoeic.bkteam.vntoeicpro.Vocabulary.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import model.ModelSection;
import model.ModelTag;
import sqlite.SqliteVocabulary;

/**
 * Created by giang on 5/28/17.
 */

public class SectionVocabularryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    SqliteVocabulary mSqliteVocabulary = new SqliteVocabulary();
    Context mContext;
    ModelSection[] mSections;

    public SectionVocabularryAdapter(Context context, ModelSection[] sections) {
        this.mContext = context;
        this.mSections = sections;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.section_vocabulary_item_layout, parent, false);

        return new SectionVocabularryItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ModelTag[] tags = mSqliteVocabulary.searchTaginSection(mSections[position].getmSectionId());
        SectionVocabularryItemViewHolder viewHolder = (SectionVocabularryItemViewHolder) holder;
        viewHolder.mTextView.setText(mSections[position].getmSectionTitle());
        viewHolder.mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        TagVocabularyAdapter adapter = new TagVocabularyAdapter(mContext, tags);
        viewHolder.mRecyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mSections.length;
    }

    private class SectionVocabularryItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private RecyclerView mRecyclerView;

        public SectionVocabularryItemViewHolder(View view) {
            super(view);
            this.mTextView = (TextView) view.findViewById(R.id.textView);
            this.mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        }
    }
}
