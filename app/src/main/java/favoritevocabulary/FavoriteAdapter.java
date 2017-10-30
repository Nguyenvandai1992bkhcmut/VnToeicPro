package favoritevocabulary;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;
import java.util.HashMap;

import customview.CustomPinnedSectionListview;
import de.halfbit.pinnedsection.PinnedSectionListView;
import model.ModelAbstractWord;
import model.ModelColorFavorite;
import model.ModelFavoriteWord;
import model.ModelLesson;
import model.ModelWord;
import model.ModelWordLesson;
import sqlite.SqliteVocabulary;

/**
 * Created by giang on 10/18/17.
 */

public class FavoriteAdapter extends ArrayAdapter<ModelAbstractWord> implements PinnedSectionListView.PinnedSectionListAdapter, AdapterDataModel, AdapterDataView{
    private final CustomPinnedSectionListview mListView;
    private ModelLesson[] mSections;
    private ArrayList<ModelAbstractWord> mSectionsList = new ArrayList<>();
    private Context mContext;
    private HashMap<ModelAbstractWord, Integer> mIdMap = new HashMap<>();
    private int mCounter;
    private ArrayList<ModelAbstractWord> mDatas = new ArrayList<>();
    private OnItemClick mListener;

    public FavoriteAdapter(@NonNull Context context, OnItemClick listener, CustomPinnedSectionListview listView, @LayoutRes int resource) {
        super(context, resource);
        this.mContext = context;
        this.mListView = listView;

        this.mListener = listener;
        prepareList();
        updateStableIds();
    }

    private void updateStableIds() {
        mIdMap.clear();
        mCounter = 0;
        for (int i = 0; i < getCount(); i++) {
            mIdMap.put(mDatas.get(i), mCounter ++);
        }
    }

    public void addStableIdForDataAtPosition(int position) {
        mIdMap.put(mDatas.get(position), ++ mCounter);
    }

    public void removeStableIdForDataAtPosition(int position) {
        mIdMap.remove(mDatas.get(position));
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void notifyDatasetChanged() {
        super.notifyDataSetChanged();
    }


    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == ModelAbstractWord.SECTION;
    }

    @Override
    public void add(int lessonTag, int wordId) {

    }

    @Override
    public ModelFavoriteWord remove(int lessonTag, int wordId) {
        return null;
    }


    @Override
    public void prepareList() {
        SqliteVocabulary sqliteVocabulary = new SqliteVocabulary();
        mSections = sqliteVocabulary.searchLessonTag(15);
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
            mSectionsList.add(section);
            mDatas.add(section);

            ModelWordLesson[] wordLessons = sqliteVocabulary.searchWordLesson(mSections[i].getmLessonId());
            for (int j = 0; j < wordLessons.length; j++) {
                ModelAbstractWord item = new ModelAbstractWord(
                        ModelAbstractWord.ITEM,
                        null,
                        -1,
                        wordLessons[j]
                );
                item.sectionPosition = sectionPosition;
                item.listPosition = listPosition++;
                mDatas.add(item);
            }
            sectionPosition++;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (mDatas.get(position).isSection()) {
            SectionViewHolder sectionViewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_section, parent, false);
                sectionViewHolder = new SectionViewHolder(convertView);
                convertView.setTag(sectionViewHolder);

            } else {
                sectionViewHolder = (SectionViewHolder) convertView.getTag();
            }
            sectionViewHolder.mTextView.setText(mDatas.get(position).mText);


        } else {
            WordViewHolder itemViewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext)
                        .inflate(R.layout.word_itemview, parent, false);
                itemViewHolder = new WordViewHolder(convertView);
                convertView.setTag(itemViewHolder);
            } else {
                itemViewHolder = (WordViewHolder) convertView.getTag();
            }

            final ModelWord word = mDatas.get(position).mWord.getmWord();
            itemViewHolder.setData(word, mDatas.get(position).listPosition);

            if (mDatas.get(position) != null){
                if(!mDatas.get(position).isSection()) {
                    itemViewHolder.bind(
                            mSections[mDatas.get(position).sectionPosition].getmLessonId(),
                            position - mDatas.get(mDatas.get(position).sectionPosition).listPosition );
                }
            }
        }
        return convertView;
    }


    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).mType;
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    public int getSection(int position) {
        return mSectionsList.get(position).listPosition;
    }

    class SectionViewHolder {
        public TextView mTextView;
        public SectionViewHolder(View view) {
            mTextView = (TextView) view.findViewById(R.id.textView);
        }
    }


    class WordViewHolder {

        private TextView mTextView;
        private ImageView mImageView;
        private ModelWord mWord;
        private int mPosInList;

        public WordViewHolder(View view) {
            this.mTextView = (TextView) view.findViewById(R.id.textView);
            this.mImageView = (ImageView) view.findViewById(R.id.imageView);
        }

        public void setData(ModelWord word, int posInList) {
            this.mWord = word;
            this.mTextView.setText(mWord.getmWord());
            this.mPosInList = posInList;
        }

        public void bind(final int lessonId, final int position) {
            this.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(mPosInList, mWord, lessonId);
                }
            });
        }



    }





    public void updateIndex(int from, int value) {
        for (int i = from; i < getCount(); i ++) {
            ModelAbstractWord abstractWord = mDatas.get(i);
            if (abstractWord.mType == ModelAbstractWord.ITEM) {
                abstractWord.listPosition += value;
            }
        }
    }

}


