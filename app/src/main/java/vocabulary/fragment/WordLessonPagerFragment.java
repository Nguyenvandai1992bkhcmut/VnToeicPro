package vocabulary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.vntoeic.bkteam.vntoeicpro.R;

import de.halfbit.pinnedsection.PinnedSectionListView;
import dictionary.FragmentDictionary;
import model.Dictionary;
import model.ModelLesson;
import sqlite.SqliteDictionary;
import sqlite.SqliteVocabulary;
import vocabulary.ScrollableFragmentInterface;
import vocabulary.adapter.PinnedSectionAdapter;
import vocabulary.adapter.WordMeaningAdapter;

/**
 * Created by giang on 6/27/17.
 */

public class WordLessonPagerFragment extends Fragment implements ScrollableFragmentInterface {

    private static final String TAG_ID = "tag id";
    public static final String WORD = "word";
    private Context mContext;
    private int mTagId;
    private PinnedSectionListView mListView;
    private PinnedSectionAdapter mAdapter;

    public static WordLessonPagerFragment create(int tagId) {

        WordLessonPagerFragment fragment = new WordLessonPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TAG_ID, tagId);
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
        mTagId = getArguments().getInt(TAG_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_word_lesson_pager, container, false);
        mListView = (PinnedSectionListView) view.findViewById(R.id.listview);
        mAdapter = new PinnedSectionAdapter(mContext, mTagId, (PinnedSectionAdapter.OnItemClickListener) mContext);
        mListView.setAdapter(mAdapter);
        return view;
    }


    @Override
    public void scrollTo(final int position) {
        mListView.post(new Runnable() {
            @Override
            public void run() {
                mListView.smoothScrollToPositionFromTop(mAdapter.getSection(position), 40);
            }
        });
    }
}
