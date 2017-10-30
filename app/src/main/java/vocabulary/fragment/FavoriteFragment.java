package vocabulary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vntoeic.bkteam.vntoeicpro.R;

import customview.CustomPinnedSectionListview;
import favoritevocabulary.FavoriteAdapter;
import favoritevocabulary.OnItemClick;
import model.ModelAbstractWord;
import model.ModelWord;
import model.ModelWordLesson;
import tranthicamgiang.vntoeic.com.favoritenote.FavoriteView;
import tranthicamgiang.vntoeic.com.favoritenote.FavoriteViewListener;
import vocabulary.ScrollableFragmentInterface;

/**
 * Created by giang on 7/29/17.
 */

public class FavoriteFragment extends Fragment implements ScrollableFragmentInterface, OnItemClick, FavoriteViewListener {

    private static final String LISTENER = "listener";
    private CustomPinnedSectionListview mListView;
    private FavoriteAdapter mAdapter;
    private Context mContext;
    private FavoriteView mFavoriteView;
    private View mBlurLayout;
    private ScrollViewPagerListener mListener;
    private ModelWord mCurrentWord;
    private int mCurrentLessonId;
    private int mCurrentColorChosen;
    private int mOldPos;


    public static FavoriteFragment create(CustomViewPager listener) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(LISTENER, listener);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        mListener = (ScrollViewPagerListener) getArguments().getSerializable(LISTENER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_pager, container, false);
        mBlurLayout = view.findViewById(R.id.framelayout);
        mBlurLayout.setVisibility(View.GONE);
        mBlurLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCloseFavoriteView(-1);
            }
        });
        mFavoriteView = (FavoriteView) view.findViewById(R.id.favoriteview);
        mFavoriteView.setListener(this);
        mListView = (CustomPinnedSectionListview) view.findViewById(R.id.listview);
        mAdapter = new FavoriteAdapter(mContext, this, mListView, 0);
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

    @Override
    public void onItemClick(int oldPos, ModelWord currentWord, int currentLessonId) {
        this.mOldPos = oldPos;
        this.mCurrentWord = currentWord;
        mCurrentLessonId = currentLessonId;
        Toast.makeText(mContext, "on item click", Toast.LENGTH_SHORT).show();
        mBlurLayout.setVisibility(View.VISIBLE);
        mListener.enableScroll(false);
        mFavoriteView.show();
    }

    @Override
    public void onCloseFavoriteView(int index) {
        mListener.enableScroll(true);
        if (index >= 0) {
            /**
             * save to favorite index_th
             */

            removeFromFavoriteTableTo(mCurrentWord.getmId(), mCurrentLessonId, mCurrentColorChosen, mOldPos, 1);
        }

        mFavoriteView.hide(this);
    }


    private void removeFromFavoriteTableTo(int wordId, int oldLessonIndex, int newLessonIndex, int oldPosition, int newPosition) {
        removeFromFavoriteTable(wordId, oldLessonIndex);
        mAdapter.updateIndex(oldPosition, -1);
        insertIntoFavoriteTable(wordId, newLessonIndex);
        mAdapter.updateIndex(newPosition, 1);

        mListView.removeRow(
                new ModelAbstractWord(ModelAbstractWord.ITEM, mCurrentWord.getmWord(), mCurrentLessonId, new ModelWordLesson(mCurrentLessonId, mCurrentWord)),
                oldPosition,
                newPosition
        );

    }
    private void insertIntoFavoriteTable(int wordId, int newColorIndex) {

    }



    private void removeFromFavoriteTable(int wordId, int oldColorIndex) {

    }

    @Override
    public void onChoose(int index) {
        mCurrentColorChosen = index;
    }

    @Override
    public void afterHideFavorite() {
        mBlurLayout.setVisibility(View.GONE);
    }
}
