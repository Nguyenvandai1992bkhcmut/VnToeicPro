package vocabulary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vntoeic.bkteam.vntoeicpro.R;

import model.ModelSection;
import sqlite.SqliteVocabulary;
import vocabulary.adapter.SectionVocabularyAdapter;

/**
 * Created by giang on 6/25/17.
 */

public class VocabularyFragment extends Fragment {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private SqliteVocabulary mSqliteVocabulary = new SqliteVocabulary();
    private ModelSection[] mSections;

    @Override
    public void onAttach(Context context) {
        this.mContext = context;

        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vocabulary, container, false);

        setUpRecyclerView(view);
        return view;
    }

    private void setUpRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mSections = mSqliteVocabulary.searchAllSection();
        SectionVocabularyAdapter sectionVocabularryAdapter = new SectionVocabularyAdapter(mContext);
        mRecyclerView.setAdapter(sectionVocabularryAdapter);

    }
}
