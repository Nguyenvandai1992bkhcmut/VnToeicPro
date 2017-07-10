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
import android.widget.ImageView;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import model.ModelWord;
import vocabulary.adapter.WordDetailAdapter;

/**
 * Created by giang on 7/2/17.
 */

public class WordDetailFragment extends Fragment {

    private static final String WORD = "word";
    private Context mContext;
    private ModelWord mWord;
    private ImageView mPlayBtn;
    private ImageView mFavoriteBtn;
    private TextView mWordTxt;
    private TextView mPronounce;
    private TextView mExamples;
    private RecyclerView mMeanings;
    private TextView mExamplesTxt;
    private TextView mMeaningsTxt;

    public static WordDetailFragment newInstance(ModelWord word) {
        WordDetailFragment fragment = new WordDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(WORD, word);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mWord = (ModelWord) getArguments().getSerializable(WORD);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_detail, container, false);

        bindView(view);
        bindData();
        return view;
    }

    private void bindData() {

        this.mWordTxt.setText(mWord.getmWord());
        if (mWord.getmPronounce() != "") {
            this.mPronounce.setText(mWord.getmPronounce());
            this.mPronounce.setVisibility(View.VISIBLE);
        }
        if (mWord.getmExamples() != ""){
            this.mExamples.setText(mWord.getmExamples());
            this.mExamples.setVisibility(View.VISIBLE);
            this.mExamplesTxt.setVisibility(View.VISIBLE);
        }
        this.mMeanings.setLayoutManager(new LinearLayoutManager(mContext));
        if (mWord.getmMeanings().length > 0) {
            WordDetailAdapter adapter = new WordDetailAdapter(mContext, mWord);
            this.mMeanings.setAdapter(adapter);
            this.mMeanings.setVisibility(View.VISIBLE);
            this.mMeaningsTxt.setVisibility(View.VISIBLE);

        }


    }

    private void bindView(View rootView) {
        this.mPlayBtn = (ImageView) rootView.findViewById(R.id.play_btn);
        this.mFavoriteBtn = (ImageView) rootView.findViewById(R.id.mark_btn);
        this.mWordTxt = (TextView) rootView.findViewById(R.id.word);
        this.mPronounce = (TextView) rootView.findViewById(R.id.pronounce);
        this.mMeanings = (RecyclerView) rootView.findViewById(R.id.meanings);
        this.mExamples = (TextView) rootView.findViewById(R.id.examples);
        this.mExamplesTxt = (TextView) rootView.findViewById(R.id.example_txt);
        this.mMeaningsTxt = (TextView) rootView.findViewById(R.id.meaning_txt);

    }
}

