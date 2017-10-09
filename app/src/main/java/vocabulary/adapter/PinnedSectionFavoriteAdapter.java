package vocabulary.adapter;

import android.content.Context;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import animation.SpringAnimationUtils;
import customview.CustomPinnedSectionListview;
import de.halfbit.pinnedsection.PinnedSectionListView;
import model.ModelAbstractFavoriteWord;
import model.ModelColorFavorite;
import model.ModelLesson;
import model.ModelWordLesson;
import sqlite.SqliteVocabulary;
import vocabulary.ColorConstant;

//import sqlite.SqliteFavoriteVocabulary;

/**
 * Created by giang on 7/29/17.
 */

public class PinnedSectionFavoriteAdapter extends ArrayAdapter<ModelAbstractFavoriteWord> implements PinnedSectionListView.PinnedSectionListAdapter{

    private final Context mContext;
    private final CustomPinnedSectionListview mListview;
    private float[] colorPosition = new float[ColorConstant.colorArr.length];
    private float mCenterX = (float) 0.0;
    private int mCircleWidth;
    private ArrayList<Boolean> mListVisible = new ArrayList<>();
//    SqliteFavoriteVocabulary mSqlite = new SqliteFavoriteVocabulary();
    private ModelColorFavorite[] mFavoriteTables;
    private PinnedSectionAdapter.OnItemClickListener mListener;
    private HashMap<ModelAbstractFavoriteWord, Integer> mIdMap = new HashMap<>();
    private int mCounter;
    private List<ModelAbstractFavoriteWord> mData = new ArrayList<>();
    private ArrayList<ModelAbstractFavoriteWord> mSectionList = new ArrayList<>();

    public PinnedSectionFavoriteAdapter(Context context, PinnedSectionAdapter.OnItemClickListener listener, CustomPinnedSectionListview listview) {
        super(context, 0);
        this.mListview = listview;
        this.mContext = context;
        this.mListener = listener;
//        prepareList();
        /**
         * Just for Test ...
         */

        SqliteVocabulary sqliteVocabulary = new SqliteVocabulary();
        ModelLesson[] sectionsData = sqliteVocabulary.searchLessonTag(15);

        int sectionPosition = 0, listPosition = 0;
        for (int i = 0; i < ColorConstant.colorArr.length - 1; i++) {
            ModelAbstractFavoriteWord section = new ModelAbstractFavoriteWord(
                    ModelAbstractFavoriteWord.SECTION,
                    new ModelColorFavorite(i),
                    null
            );
            section.mSectionPosition = sectionPosition;
            section.mListPosition = listPosition ++;
            mData.add(section);
            mSectionList.add(section);
            mListVisible.add(false);

            ModelWordLesson[] wordLessons = sqliteVocabulary.searchWordLesson(sectionsData[i].getmLessonId());
            for (int j = 0; j < wordLessons.length; j++) {
                ModelAbstractFavoriteWord item = new ModelAbstractFavoriteWord(
                        ModelAbstractFavoriteWord.ITEM,
                        new ModelColorFavorite(i),
                        wordLessons[j]
                );
                item.mSectionPosition = sectionPosition;
                item.mListPosition = listPosition++;
                mData.add(item);
                mListVisible.add(false);
            }
            sectionPosition++;
        }


        /*********************************************************************/
        updateStableIds();
    }

    public int getSection(int position) {
        return mSectionList.get(position).mListPosition;
    }

    private void updateStableIds() {
        mIdMap.clear();
        mCounter = 0;
        for (int i = 0; i < mData.size(); i++) {
            mIdMap.put(mData.get(i), mCounter ++);
        }
    }

    public long getItemId(int position) {
        ModelAbstractFavoriteWord item = getItem(position);
        if (mIdMap.containsKey(item)) return mIdMap.get(item);
        return -1;
    }

    public void addStableIdForDataAtPosition(int position) {
        mIdMap.put(mData.get(position), ++ mCounter);
    }

    public void removeStableIdForDataAtPosition(int position) {
        mIdMap.remove(mData.get(position));
    }

    private void prepareList() {
//        SqliteFavoriteVocabulary sqlite = new SqliteFavoriteVocabulary();
//        mFavoriteTables = sqlite.searchAllFavoriteTable();
//        int sectionPosition = 0, listPosition = 0;
//
//        for (int i = 0; i < mFavoriteTables.length; i++) {
//            ModelAbstractFavoriteWord section = new ModelAbstractFavoriteWord(
//                    ModelAbstractFavoriteWord.SECTION,
//                    mFavoriteTables[i],
//                    null
//            );
//
//            section.mSectionPosition = sectionPosition;
//            section.mListPosition = listPosition ++;
//            mSectionList.add(section);
//            mData.add(section);
//
//            ModelWordLesson[] words = sqlite.searchAllWordInFavoriteTable(mFavoriteTables[i].mIndex);
//            for (int j = 0; j < words.length; j++) {
//                ModelAbstractFavoriteWord item = new ModelAbstractFavoriteWord(
//                        ModelAbstractFavoriteWord.ITEM,
//                        null,
//                        words[j]
//                );
//                item.mSectionPosition = sectionPosition;
//                item.mListPosition = listPosition ++;
//                mData.add(item);
//            }
//
//            sectionPosition ++;
//        }

    }


    @Override
    public int getCount() {
        return mData.size();
    }

    public void updateIndex(int from, int value) {
        for (int i = from; i < mData.size(); i ++) {
            ModelAbstractFavoriteWord abstractFavoriteWord = mData.get(i);
            if (abstractFavoriteWord.mType == ModelAbstractFavoriteWord.ITEM) {
                abstractFavoriteWord.mListPosition += value;
            }
        }
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }



    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == ModelAbstractFavoriteWord.SECTION;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).mType;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (mData.get(position).isSection()) {

            SectionViewholder sectionViewholder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_favorite_section, parent, false);
                sectionViewholder = new SectionViewholder(convertView);
                convertView.setTag(sectionViewholder);

            } else {
                sectionViewholder = (SectionViewholder) convertView.getTag();
            }

            sectionViewholder.mTextView.setText(mData.get(position).mModelColorFavorite.toString());
//            int favoriteTableIndex = mSqlite.checkIfAlreadyInFavorite(mData.get(position).mModelWordLesson.getmWord().getmId());
//            if (favoriteTableIndex != -1) {
//                sectionViewholder.mImageView.setImageResource(ColorConstant.colorArr[favoriteTableIndex]);
//            }

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

            final ModelWordLesson wordLesson = mData.get(position).mModelWordLesson;
            itemViewHolder.setData(wordLesson, position);

            if(!mData.get(position).isSection()) {
                itemViewHolder.bind(wordLesson.getmLessonId(),
                        position - mData.get(mData.get(position).mSectionPosition).mListPosition,
                        mListener);
            }


            itemViewHolder.bindData();

            itemViewHolder.mBtn.setTag(position);
            if (mListVisible.get(position)) {
                itemViewHolder.mFavorateLayout.setVisibility(View.VISIBLE);
                itemViewHolder.setOpenedPosition();
            } else {
                itemViewHolder.mFavorateLayout.setVisibility(View.GONE);
            }
            itemViewHolder.mFavorateLayout.setVisibility(mListVisible.get(position)? View.VISIBLE : View.GONE);
        }

        return convertView;
    }



    /* ======================== ViewHolder ===========================*/
    class SectionViewholder {
        public TextView mTextView;
        public ImageView mImageView;

        public SectionViewholder(View view) {
            mTextView = (TextView) view.findViewById(R.id.textView);
            mImageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }


    class WordViewHolder {
        public TextView mWordTxt;
        public Button mBtn;
        public RecyclerView mRecyclerView;
        public RelativeLayout mFavorateLayout;
        public View mView;
        public ImageView mRed, mOrange, mYellow, mGreen, mBlue, mPurple, mUnFavorite;
        private ModelWordLesson mWord;

        private float mXCircle;
        private PinnedSectionAdapter.OnItemClickListener mListener;
        private int mLessonId;
        private int mPos;
        private int mListviewPosition;


        public void setOpenedPosition() {
            mRed.animate().x((float) (mCenterX - mCircleWidth/2.0 + colorPosition[ColorConstant.RED])).setDuration(0).start();
            mOrange.animate().x((float) (mCenterX - mCircleWidth/2.0 + colorPosition[ColorConstant.ORANGE])).setDuration(0).start();
            mYellow.animate().x((float) (mCenterX - mCircleWidth/2.0 + colorPosition[ColorConstant.YELLOW])).setDuration(0).start();
            mGreen.animate().x((float) (mCenterX - mCircleWidth/2.0 + colorPosition[ColorConstant.GREEN])).setDuration(0).start();
            mBlue.animate().x((float) (mCenterX - mCircleWidth/2.0 + colorPosition[ColorConstant.BLUE])).setDuration(0).start();
            mPurple.animate().x((float) (mCenterX - mCircleWidth/2.0 + colorPosition[ColorConstant.PURPLE])).setDuration(0).start();
            mUnFavorite.animate().x((float) (mCenterX - mCircleWidth/2.0 + colorPosition[ColorConstant.NONE])).setDuration(0).start();
        }

        public void setClosedPosition() {
            if (mXCircle <= 0) return;
            mRed.animate().x(mXCircle).setDuration(0).start();
            mOrange.animate().x(mXCircle).setDuration(0).start();
            mYellow.animate().x(mXCircle).setDuration(0).start();
            mGreen.animate().x(mXCircle).setDuration(0).start();
            mBlue.animate().x(mXCircle).setDuration(0).start();
            mPurple.animate().x(mXCircle).setDuration(0).start();
            mUnFavorite.animate().x(mXCircle).setDuration(0).start();
        }


        public ViewTreeObserver.OnGlobalLayoutListener mCircleGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    mUnFavorite.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                else {
                    mUnFavorite.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                mCircleWidth = mUnFavorite.getWidth();
                mXCircle = mUnFavorite.getX();

                int widthPixel = mFavorateLayout.getWidth();
                int padding = mFavorateLayout.getPaddingLeft();
                float measureWidth = widthPixel - 2 * padding - mCircleWidth;
                mCenterX = (float) (mXCircle + mCircleWidth / 2.0);
                float step = (float) (measureWidth / 6.0);

                initColorPosition(mCenterX, step, padding, mCircleWidth);
                openFavoriteAnimation();
            }
        };

        private void openFavoriteAnimation() {
            SpringAnimation redAnimation = SpringAnimationUtils.createSpringAnimation(
                    mRed,
                    SpringAnimation.TRANSLATION_X,
                    colorPosition[ColorConstant.RED],
                    SpringForce.STIFFNESS_MEDIUM,
                    SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
            );

            SpringAnimation orangeAnimation = SpringAnimationUtils.createSpringAnimation(
                    mOrange,
                    SpringAnimation.TRANSLATION_X,
                    colorPosition[ColorConstant.ORANGE],
                    SpringForce.STIFFNESS_MEDIUM,
                    SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
            );
            SpringAnimation yellowAnimation = SpringAnimationUtils.createSpringAnimation(
                    mYellow,
                    SpringAnimation.TRANSLATION_X,
                    colorPosition[ColorConstant.YELLOW],
                    SpringForce.STIFFNESS_MEDIUM,
                    SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
            );
            SpringAnimation greenAnimation = SpringAnimationUtils.createSpringAnimation(
                    mGreen,
                    SpringAnimation.TRANSLATION_X,
                    colorPosition[ColorConstant.GREEN],
                    SpringForce.STIFFNESS_MEDIUM,
                    SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
            );

            SpringAnimation blueAnimation = SpringAnimationUtils.createSpringAnimation(
                    mBlue,
                    SpringAnimation.TRANSLATION_X,
                    colorPosition[ColorConstant.BLUE],
                    SpringForce.STIFFNESS_MEDIUM,
                    SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
            );
            SpringAnimation purpleAnimation = SpringAnimationUtils.createSpringAnimation(
                    mPurple,
                    SpringAnimation.TRANSLATION_X,
                    colorPosition[ColorConstant.PURPLE],
                    SpringForce.STIFFNESS_MEDIUM,
                    SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
            );
            SpringAnimation unFavoriteAnimation = SpringAnimationUtils.createSpringAnimation(
                    mUnFavorite,
                    SpringAnimation.TRANSLATION_X,
                    colorPosition[ColorConstant.NONE],
                    SpringForce.STIFFNESS_MEDIUM,
                    SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
            );

            greenAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                @Override
                public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                    Log.d("++++++", "END ANIMATION GREEN " + mGreen.getX());
                }
            });
            redAnimation.start();
            unFavoriteAnimation.start();
            orangeAnimation.start();
            purpleAnimation.start();
            yellowAnimation.start();
            blueAnimation.start();
            greenAnimation.start();
        }

        private void initColorPosition(float centerX, float step, int padding, float circleWidth) {
            colorPosition[ColorConstant.RED] = (float) (-centerX + (ColorConstant.RED * step) + padding + circleWidth / 2.0);
            colorPosition[ColorConstant.ORANGE] = (float) (-centerX + (ColorConstant.ORANGE * step) + padding + circleWidth / 2.0);
            colorPosition[ColorConstant.YELLOW] = (float) (-centerX + (ColorConstant.YELLOW * step) + padding + circleWidth / 2.0);
            colorPosition[ColorConstant.GREEN] = (float) (-centerX + (ColorConstant.GREEN * step) + padding + circleWidth / 2.0);
            colorPosition[ColorConstant.BLUE] = (float) (-centerX + (ColorConstant.BLUE * step) + padding + circleWidth / 2.0);
            colorPosition[ColorConstant.PURPLE] = (float) (-centerX + (ColorConstant.PURPLE * step) + padding + circleWidth / 2.0);
            colorPosition[ColorConstant.NONE] = (float) (-centerX + (ColorConstant.NONE * step) + padding + circleWidth / 2.0);

        }


        public WordViewHolder(View view) {
            view.setOnClickListener(mOnClickListener);

            mBtn = (Button) view.findViewById(R.id.btn_favorite);
            mBtn.setOnClickListener(mOnClickListener);

            mWordTxt = (TextView) view.findViewById(R.id.word);
            mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            mFavorateLayout  = (RelativeLayout) view.findViewById(R.id.favorate_layout);


            mRed = (ImageView) view.findViewById(R.id.btn_red);
            mRed.setOnClickListener(mOnClickListener);

            mOrange = (ImageView) view.findViewById(R.id.btn_orrange);
            mOrange.setOnClickListener(mOnClickListener);
            mYellow = (ImageView) view.findViewById(R.id.btn_yellow);
            mYellow.setOnClickListener(mOnClickListener);
            mGreen = (ImageView) view.findViewById(R.id.btn_green);
            mGreen.setOnClickListener(mOnClickListener);
            mBlue = (ImageView) view.findViewById(R.id.btn_blue);
            mBlue.setOnClickListener(mOnClickListener);
            mPurple = (ImageView) view.findViewById(R.id.btn_purple);
            mPurple.setOnClickListener(mOnClickListener);
            mUnFavorite = (ImageView) view.findViewById(R.id.btn_un_favorite);

            mUnFavorite.setOnClickListener(mOnClickListener);
        }

        public void setData(ModelWordLesson word, int listviewPosition) {
            this.mWord = word;
            this.mListviewPosition = listviewPosition;
        }

        public void bindData() {
            mWordTxt.setText(mWord.getmWord().getmWord());
            WordMeaningAdapter adapter = new WordMeaningAdapter(mContext, mWord.getmWord().getmTypes(), mWord.getmWord().getmMeanings(), mOnClickListener);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.setAdapter(adapter);

        }

        public void bind(final int lessonId, final int position, final PinnedSectionAdapter.OnItemClickListener listener){
            this.mListener = listener;
            this.mPos = position - 1;
            mLessonId = lessonId;
        }


        private View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currentColor = -1;

                if(mFavorateLayout.getVisibility() == View.VISIBLE) {
                    if (v.getId() == R.id.btn_un_favorite) {
                        removeFromFavoriteTableTo(
                                mWord.getmWord().getmId(),
                                mFavoriteTables[mData.get(mListviewPosition).mSectionPosition].mIndex,
                                -1,
                                mListviewPosition,
                                -1
                        );

                        setBackgroundBtn(mBtn, mWord.getmWord().getmId(), -1);
                        hideFavoriteLayout();
                    } else {
                        int colorChosen;
                        switch (v.getId()){

                            case R.id.btn_red:
                                currentColor = ColorConstant.RED;
                                break;
                            case R.id.btn_orrange:
                                currentColor = ColorConstant.ORANGE;
                                break;
                            case R.id.btn_yellow:
                                currentColor = ColorConstant.YELLOW;
                                break;

                            case R.id.btn_green:
                                currentColor = ColorConstant.GREEN;
                                break;
                            case R.id.btn_blue:
                                currentColor = ColorConstant.BLUE;
                                break;
                            case R.id.btn_purple:
                                currentColor = ColorConstant.BLUE;
                                break;
                        }

                        removeFromFavoriteTableTo(
                                mWord.getmWord().getmId(),
                                mFavoriteTables[mData.get(mListviewPosition).mSectionPosition].mIndex,
                                currentColor,
                                mListviewPosition,
                                mSectionList.get(currentColor).mListPosition + 1
                        );

                        colorChosen = ColorConstant.colorArr[currentColor];
                        setBackgroundBtn(mBtn, mWord.getmWord().getmId(), colorChosen);
                        hideFavoriteLayout();

                        setClosedPosition();
                        notifyDataSetChanged();
                    }

                } else {
                    switch (v.getId()){
                        case R.id.btn_favorite:
                            showFavoriteLayout();
                            break;
                        default: mListener.onItemClick(mLessonId, mPos);
                    }
                }
            }
        };

        private void removeFromFavoriteTableTo(int wordId, int oldColorIndex, int newColorIndex, int oldPosition, int newPosition) {
            removeFromFavoriteTable(wordId, oldColorIndex);
            updateIndex(oldPosition, -1);
            insertIntoFavoriteTable(wordId, newColorIndex);
            updateIndex(newPosition, 1);

            mListview.removeRow(
                    new ModelAbstractFavoriteWord(ModelAbstractFavoriteWord.ITEM, new ModelColorFavorite(oldColorIndex), mWord),
                    oldPosition,
                    newPosition
            );

        }


        private void hideFavoriteLayout() {
            /**
             * add animation
             */
            this.mFavorateLayout.setVisibility(View.GONE);
            int position = (int) this.mBtn.getTag();
            mListVisible.set(position, false);
        }

        private void showFavoriteLayout() {
            this.mFavorateLayout.setVisibility(View.VISIBLE);
            mUnFavorite.getViewTreeObserver().addOnGlobalLayoutListener(mCircleGlobalLayoutListener);
            int position = (int) this.mBtn.getTag();
            mListVisible.set(position, true);
            /**
             * add animation
             */


        }

        private void removeFromFavoriteTable(int wordId, int oldColorIndex){

            /**
             * todo.......
             */
//            mSqlite.removeFromFavorite(wordId, oldColorIndex);
        }

        private void insertIntoFavoriteTable(int wordId, int newColorIndex) {

//            mSqlite.insertIntoFavorite(wordId, newColorIndex);
            notifyDataSetChanged();
        }

        private void setBackgroundBtn(Button btn, int wordId, int colorChosen) {
//            if (!mSqlite.checkFavoriteWord(wordId)) {
//                btn.setBackgroundResource(R.mipmap.icon_heart_while);
//            } else btn.setBackgroundResource(R.mipmap.icon_heart);
        }
    }

}
