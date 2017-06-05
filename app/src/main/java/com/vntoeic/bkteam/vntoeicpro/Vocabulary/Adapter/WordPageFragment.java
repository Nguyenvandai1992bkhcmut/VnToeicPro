package com.vntoeic.bkteam.vntoeicpro.Vocabulary.Adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import model.ModelWordLesson;
import sqlite.SqliteVocabulary;

/**
 * Created by giang on 5/30/17.
 */

public class WordPageFragment extends Fragment implements View.OnClickListener{
    private SqliteVocabulary mSqliteVocabulary = new SqliteVocabulary();
    private static final String WORD = "word";
    private static WordPageFragment mInstance;

    private boolean isMark;
    private ImageView mPlayBtn, mMarkBtn;
    private TextView mWord, mPronounce;
    private RecyclerView mMeaningListview;
    private ModelWordLesson mWordLesson;
    private TextView mExample;

    public static Fragment create(ModelWordLesson word) {
        if (mInstance == null) {
            mInstance = new WordPageFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(WORD, word);
        mInstance.setArguments(bundle);
        return mInstance;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.word_page_fragment, container, false);
        init(view);
        bindData(view);

        return view;
    }

    private void bindData(View view) {

        isMark = mSqliteVocabulary.checkWordChecked(mWordLesson.getmWord().getmId());
        mMarkBtn.setImageResource(isMark? R.mipmap.icon_checked : R.mipmap.icon_notchecked);

        mWord.setText(mWordLesson.getmWord().getmWord());
    }


    private void init(View view) {
        mPlayBtn = (ImageView) view.findViewById(R.id.play_btn);
        mMarkBtn = (ImageView) view.findViewById(R.id.mark_btn);
        mWord = (TextView) view.findViewById(R.id.word);
        mPronounce = (TextView) view.findViewById(R.id.pronounce);
        mMeaningListview = (RecyclerView) view.findViewById(R.id.meaning_listview);
        mExample= (TextView) view.findViewById(R.id.example);

        Bundle bundle = getArguments();
        mWordLesson = (ModelWordLesson) bundle.getSerializable(WORD);
    }

    @Override
    public void onClick(View v) {

    }
}
