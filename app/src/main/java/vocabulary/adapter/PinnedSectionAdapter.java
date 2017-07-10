package vocabulary.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.Date;

import de.halfbit.pinnedsection.PinnedSectionListView;
import model.ModelAbstractWord;
import model.ModelFavoriteWord;
import model.ModelLesson;
import model.ModelWord;
import model.ModelWordLesson;
import sqlite.SqliteVocabulary;

/**
 * Created by giang on 6/30/17.
 */

public class PinnedSectionAdapter extends ArrayAdapter<ModelAbstractWord> implements PinnedSectionListView.PinnedSectionListAdapter{
    private final int mTagId;
    private ModelLesson[] mSections;
    private Context mContext;
    private SqliteVocabulary mSqliteVocabulary;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int lessonId, int position);
    }

    public PinnedSectionAdapter(@NonNull Context context,int tagId, OnItemClickListener listener) {

        super(context, 0);
        this.mContext = context;
        this.mTagId = tagId;
        this.mListener = listener;
        this.mSqliteVocabulary = new SqliteVocabulary();
        prepareList();
    }

    private void prepareList() {
        SqliteVocabulary sqliteVocabulary = new SqliteVocabulary();
        mSections = sqliteVocabulary.searchLessonTag(mTagId);
        int sectionPosition = 0, listPosition = 0;
        for (int i = 0; i < mSections.length; i++) {
            ModelAbstractWord section = new ModelAbstractWord(
                    ModelAbstractWord.SECTION,
                    mSections[i].getmLessonTitle(),
                    mSections[i].getmLessonId(),
                    null
            );
            section.sectionPosition = sectionPosition;
            section.listPosition = listPosition ++;
            add(section);
            ModelWordLesson[] wordLessons = sqliteVocabulary.searchWordLesson(mSections[i].getmTagId());
            for (int j = 0; j < wordLessons.length; j++) {
                ModelAbstractWord item = new ModelAbstractWord(
                        ModelAbstractWord.ITEM,
                        null,
                        -1,
                        wordLessons[j]
                );
                item.sectionPosition = sectionPosition;
                item.listPosition = listPosition++;
                add(item);
            }
            sectionPosition++;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (getItem(position).isSection()) {
            SectionViewHolder sectionViewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_section, parent, false);
                sectionViewHolder = new SectionViewHolder(convertView);
                convertView.setTag(sectionViewHolder);

            } else {
                sectionViewHolder = (SectionViewHolder) convertView.getTag();
            }
            sectionViewHolder.mTextView.setText(getItem(position).mText);


        } else {
            WordViewHolder itemViewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_word_fragment, parent, false);
                itemViewHolder = new WordViewHolder(convertView);
                convertView.setTag(itemViewHolder);
            } else {
                itemViewHolder = (WordViewHolder) convertView.getTag();
            }

            final ModelWord word = getItem(position).mWord.getmWord();
            itemViewHolder.bind(getItem(getItem(position).sectionPosition).mLessonId,
                    position - getItem(getItem(position).sectionPosition).listPosition,
                    mListener);

            itemViewHolder.mBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSqliteVocabulary.checkFavoriteWord(word.getmId())) {
                        mSqliteVocabulary.deleteWordFavorite(word.getmId());
                        v.setBackgroundResource(R.mipmap.icon_heart_while);
                    } else {
                        Date date = new Date();
                        ModelFavoriteWord favoriteWord = new ModelFavoriteWord(word.getmId(), date.toString());
                        mSqliteVocabulary.insertFavoriteWord(favoriteWord);
                        v.setBackgroundResource(R.mipmap.icon_heart);
                    }
                }
            });


            setBackgroundBtn(itemViewHolder.mBtn, word.getmId());
            itemViewHolder.mWord.setText(word.getmWord());
            WordMeaningAdapter adapter = new WordMeaningAdapter(mContext, word.getmTypes(), word.getmMeanings());
            itemViewHolder.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            itemViewHolder.mRecyclerView.setAdapter(adapter);

        }
        return convertView;
    }


    private void setBackgroundBtn(Button btn, int wordId) {
        if (!mSqliteVocabulary.checkFavoriteWord(wordId)) {
            btn.setBackgroundResource(R.mipmap.icon_heart_while);
        } else btn.setBackgroundResource(R.mipmap.icon_heart);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).mType;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == ModelAbstractWord.SECTION;
    }

    class SectionViewHolder {
        public TextView mTextView;
        public SectionViewHolder(View view) {
            mTextView = (TextView) view.findViewById(R.id.textView);
        }
    }
    class WordViewHolder{
        public TextView mWord;
        public Button mBtn;
        public RecyclerView mRecyclerView;
        public View mView;

        public WordViewHolder(View view) {
            mBtn = (Button) view.findViewById(R.id.btn_favorite);
            mWord = (TextView) view.findViewById(R.id.word);
            mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            this.mView = view;
        }

        public void bind(final int lessonId, final int wordId, final OnItemClickListener listener){
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(lessonId, wordId - 1);
                }
            });
        }

    }

}
