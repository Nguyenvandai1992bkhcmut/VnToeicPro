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

import animation.SpringAnimationUtils;
import de.halfbit.pinnedsection.PinnedSectionListView;
import model.ModelAbstractWord;
import model.ModelLesson;
import model.ModelWord;
import model.ModelWordLesson;
import sqlite.SqliteVocabulary;
import vocabulary.ColorConstant;

/**
 * Created by giang on 6/30/17.
 */

public class PinnedSectionAdapter extends ArrayAdapter<ModelAbstractWord> implements PinnedSectionListView.PinnedSectionListAdapter{
    private final int mTagId;
    private final SqliteVocabulary mSqliteVocabulary;
    private ModelLesson[] mSections;
    private ArrayList<Boolean> mListVisible = new ArrayList<>();
    private Context mContext;
    private OnItemClickListener mListener;
    private float[] colorPosition = new float[ColorConstant.colorArr.length];
    private float mCenterX = (float) 0.0;
    private int mCircleWidth;
    private ArrayList<ModelAbstractWord> mSectionsList = new ArrayList<>();

    public int getSection(int position) {
        return mSectionsList.get(position).listPosition;
    }


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
                    mSectionsList.add(section);
                    mListVisible.add(false);

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
                        add(item);
                        mListVisible.add(false);
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
            itemViewHolder.setData(word);

            if (getItem(position) != null){
                if(!getItem(position).isSection()) {
                    itemViewHolder.bind(mSections[getItem(position).sectionPosition].getmLessonId(),
                            position - getItem(getItem(position).sectionPosition).listPosition,
                            mListener);
                }
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




    public class WordViewHolder {
        public TextView mWordTxt;
        public Button mBtn;
        public RecyclerView mRecyclerView;
        public RelativeLayout mFavorateLayout;
        public View mView;
        public ImageView mRed, mOrange, mYellow, mGreen, mBlue, mPurple, mUnFavorite;
        private ModelWord mWord;
        public int mCurrentColor;

        private float mXCircle;
        private OnItemClickListener mListener;
        private int mLessonId;
        private int mPos;


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
            this.mCurrentColor = ColorConstant.NONE;
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

        public void setData(ModelWord word) {
            this.mWord = word;
        }

        public void bindData() {
            mWordTxt.setText(mWord.getmWord());
            WordMeaningAdapter adapter = new WordMeaningAdapter(mContext, mWord.getmTypes(), mWord.getmMeanings(), mOnClickListener);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.setAdapter(adapter);

        }

        public void bind(final int lessonId, final int position, final OnItemClickListener listener){
            this.mListener = listener;
            this.mPos = position - 1;
            mLessonId = lessonId;
        }

        private View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mFavorateLayout.getVisibility() == View.VISIBLE) {
                    int colorChosen;
                    switch (v.getId()){

                        case R.id.btn_red:
                            removeFromFavoriteTable(mWord.getmId(), mCurrentColor);
                            mCurrentColor = ColorConstant.RED;
                            colorChosen = ColorConstant.colorArr[mCurrentColor];
                            setBackgroundBtn(mBtn, mWord.getmId(), colorChosen);
                            insertIntoFavoriteTable(mWord.getmId(), mCurrentColor);
                            hideFavoriteLayout();
                            break;
                        case R.id.btn_orrange:
                            removeFromFavoriteTable(mWord.getmId(), mCurrentColor);
                            mCurrentColor = ColorConstant.ORANGE;
                            colorChosen = ColorConstant.colorArr[mCurrentColor];
                            setBackgroundBtn(mBtn, mWord.getmId(), colorChosen);
                            insertIntoFavoriteTable(mWord.getmId(), mCurrentColor);
                            hideFavoriteLayout();
                            break;
                        case R.id.btn_yellow:
                            removeFromFavoriteTable(mWord.getmId(), mCurrentColor);
                            mCurrentColor = ColorConstant.YELLOW;
                            colorChosen = ColorConstant.colorArr[mCurrentColor];
                            setBackgroundBtn(mBtn, mWord.getmId(), colorChosen);
                            insertIntoFavoriteTable(mWord.getmId(), mCurrentColor);
                            hideFavoriteLayout();
                            break;

                        case R.id.btn_green:
                            removeFromFavoriteTable(mWord.getmId(), mCurrentColor);
                            mCurrentColor = ColorConstant.GREEN;
                            colorChosen = ColorConstant.colorArr[mCurrentColor];
                            setBackgroundBtn(mBtn, mWord.getmId(), colorChosen);
                            insertIntoFavoriteTable(mWord.getmId(), mCurrentColor);
                            hideFavoriteLayout();
                            break;
                        case R.id.btn_blue:
                            removeFromFavoriteTable(mWord.getmId(), mCurrentColor);
                            mCurrentColor = ColorConstant.BLUE;
                            colorChosen = ColorConstant.colorArr[mCurrentColor];
                            setBackgroundBtn(mBtn, mWord.getmId(), colorChosen);
                            insertIntoFavoriteTable(mWord.getmId(), mCurrentColor);
                            hideFavoriteLayout();
                            break;
                        case R.id.btn_purple:
                            removeFromFavoriteTable(mWord.getmId(), mCurrentColor);
                            mCurrentColor = ColorConstant.BLUE;
                            colorChosen = ColorConstant.colorArr[mCurrentColor];
                            insertIntoFavoriteTable(mWord.getmId(), colorChosen);
                            setBackgroundBtn(mBtn, mWord.getmId(), colorChosen);
                            insertIntoFavoriteTable(mWord.getmId(), mCurrentColor);
                            hideFavoriteLayout();
                            break;
                        case R.id.btn_un_favorite:
                            removeFromFavoriteTable(mWord.getmId(), mCurrentColor);

                            mCurrentColor = ColorConstant.NONE;
                            colorChosen = ColorConstant.colorArr[mCurrentColor];
                            setBackgroundBtn(mBtn, mWord.getmId(), colorChosen);
                            hideFavoriteLayout();
                            break;

                    }

                    setClosedPosition();
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

        private void hideFavoriteLayout() {
            /**
             * add animation
             */
//            this.mUnFavorite.getViewTreeObserver().removeOnPreDrawListener(mCircleGlobalLayoutListener);
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

            if(oldColorIndex == ColorConstant.NONE) {
                /**
                 * not in favorite table yet
                 */
                return;
            }
            /**
             * todo.......
             */
        }

        private void insertIntoFavoriteTable(int wordId, int newColorIndex) {

        }

        private void setBackgroundBtn(Button btn, int wordId, int colorChosen) {
            if (mSqliteVocabulary.checkFavoriteWord(wordId) >= 0) {
                btn.setBackgroundResource(R.mipmap.icon_heart_while);
            } else btn.setBackgroundResource(R.mipmap.icon_heart);
        }
    }

}
