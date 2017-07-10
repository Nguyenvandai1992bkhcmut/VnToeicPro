package vocabulary.adapter;

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
 * Created by giang on 6/25/17.
 */

public class SectionVocabularyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private ModelSection[] mSections;

    public SectionVocabularyAdapter(Context context) {
        this.mContext = context;
        SqliteVocabulary sqliteVocabulary = new SqliteVocabulary();
        mSections = sqliteVocabulary.searchAllSection();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_section_vocabulary, parent, false);
        return new SectionVocabularyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        SectionVocabularyViewHolder viewHolder = (SectionVocabularyViewHolder) holder;
        SqliteVocabulary sqliteVocabulary = new SqliteVocabulary();
        ModelTag[] tags = sqliteVocabulary.searchTaginSection(mSections[position].getmSectionId());
        viewHolder.mTextView.setText(mSections[position].getmSectionTitle());
        viewHolder.mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        TagVocabularyAdapter tagVocabularyAdapter = new TagVocabularyAdapter(mContext, tags);
        viewHolder.mRecyclerView.setAdapter(tagVocabularyAdapter);
    }

    @Override
    public int getItemCount() {
        return mSections.length;
    }

    public class SectionVocabularyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private RecyclerView mRecyclerView;

        public SectionVocabularyViewHolder(View itemView) {
            super(itemView);
            this.mTextView = (TextView) itemView.findViewById(R.id.textView);
            this.mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
        }
    }
}
