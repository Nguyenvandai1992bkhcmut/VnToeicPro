package vocabulary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vntoeic.bkteam.vntoeicpro.R;

import customview.CustomPinnedSectionListview;
import vocabulary.ScrollableFragmentInterface;
import vocabulary.adapter.PinnedSectionFavoriteAdapter;

/**
 * Created by giang on 7/29/17.
 */

public class FavoriteFragment extends Fragment implements ScrollableFragmentInterface{

    private CustomPinnedSectionListview mListView;
    private PinnedSectionFavoriteAdapter mAdapter;
    private Context mContext;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_lesson_pager, container, false);
        mListView = (CustomPinnedSectionListview) view.findViewById(R.id.listview);
        mAdapter = new PinnedSectionFavoriteAdapter(mContext, null, mListView);
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
