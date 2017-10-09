package vocabulary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.Locale;

import animation.SpringAnimationUtils;
import model.ModelWord;
import vocabulary.ColorConstant;
import vocabulary.adapter.WordDetailAdapter;

/**
 * Created by giang on 7/2/17.
 */

public class WordDetailFragment extends Fragment implements View.OnClickListener{

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
    private FrameLayout mBlurLayout;
    private ImageView mRed, mOrrange, mYellow, mGreen, mBlue, mPurple, mUnFavorite;
    private RelativeLayout mFavoriteLayout;
    private Float[] mColorPosition = new Float[ColorConstant.colorArr.length];
    private int mCurrentColor = 0;
    private TextToSpeech mTextToSpeech;

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

        initColorPosition();
        this.mTextToSpeech = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                mTextToSpeech.setLanguage(Locale.US);
            }
        });
        this.mFavoriteBtn.setOnClickListener(mOnClickListener);

        this.mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextToSpeech.speak(mWord.getmWord().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        this.mWordTxt.setText(mWord.getmWord());
        if (mWord.getmPronounce() != "") {
            this.mPronounce.setText("/" + mWord.getmPronounce() + "/");
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

    private void initColorPosition() {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        float yColorRedPixel = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75, displayMetrics);
        float colorLayoutHeight = displayMetrics.heightPixels - yColorRedPixel;
        float step = (float) (colorLayoutHeight / 8.0);

        mColorPosition[ColorConstant.RED] = ColorConstant.RED * step;
        mColorPosition[ColorConstant.ORANGE] = ColorConstant.ORANGE * step;
        mColorPosition[ColorConstant.YELLOW] = ColorConstant.YELLOW * step;
        mColorPosition[ColorConstant.GREEN] = ColorConstant.GREEN * step;
        mColorPosition[ColorConstant.BLUE] = ColorConstant.BLUE * step;
        mColorPosition[ColorConstant.PURPLE] = ColorConstant.PURPLE * step;
        mColorPosition[ColorConstant.NONE] = ColorConstant.NONE * step;

    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mBlurLayout.setAlpha(1);
            mBlurLayout.setVisibility(View.VISIBLE);
            openFavoriteAnimation();
        }
    };


    public void openFavoriteAnimation() {;
        SpringAnimation redAnimation = SpringAnimationUtils.createSpringAnimation(
                mRed,
                SpringAnimation.TRANSLATION_Y,
                mColorPosition[ColorConstant.RED],
                SpringForce.STIFFNESS_MEDIUM,
                SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
        );

        SpringAnimation orangeAnimation = SpringAnimationUtils.createSpringAnimation(
                mOrrange,
                SpringAnimation.TRANSLATION_Y,
                mColorPosition[ColorConstant.ORANGE],
                SpringForce.STIFFNESS_MEDIUM,
                SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
        );
        SpringAnimation yellowAnimation = SpringAnimationUtils.createSpringAnimation(
                mYellow,
                SpringAnimation.TRANSLATION_Y,
                mColorPosition[ColorConstant.YELLOW],
                SpringForce.STIFFNESS_MEDIUM,
                SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
        );
        SpringAnimation greenAnimation = SpringAnimationUtils.createSpringAnimation(
                mGreen,
                SpringAnimation.TRANSLATION_Y,
                mColorPosition[ColorConstant.GREEN],
                SpringForce.STIFFNESS_MEDIUM,
                SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
        );

        SpringAnimation blueAnimation = SpringAnimationUtils.createSpringAnimation(
                mBlue,
                SpringAnimation.TRANSLATION_Y,
                mColorPosition[ColorConstant.BLUE],
                SpringForce.STIFFNESS_MEDIUM,
                SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
        );
        SpringAnimation purpleAnimation = SpringAnimationUtils.createSpringAnimation(
                mPurple,
                SpringAnimation.TRANSLATION_Y,
                mColorPosition[ColorConstant.PURPLE],
                SpringForce.STIFFNESS_MEDIUM,
                SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
        );
        SpringAnimation unFavoriteAnimation = SpringAnimationUtils.createSpringAnimation(
                mUnFavorite,
                SpringAnimation.TRANSLATION_Y,
                mColorPosition[ColorConstant.NONE],
                SpringForce.STIFFNESS_MEDIUM,
                SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
        );

        redAnimation.start();
        unFavoriteAnimation.start();
        orangeAnimation.start();
        purpleAnimation.start();
        yellowAnimation.start();
        blueAnimation.start();
        greenAnimation.start();
    }

    public void hideFavorite() {
        SpringAnimation blurAnimation = SpringAnimationUtils.createSpringAnimation(
                mBlurLayout,
                SpringAnimation.ALPHA,
                -mBlurLayout.getAlpha(),
                SpringForce.STIFFNESS_MEDIUM,
                SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
        );

        SpringAnimation redAnimation = SpringAnimationUtils.createSpringAnimation(
                mRed,
                SpringAnimation.TRANSLATION_Y,
                -mColorPosition[ColorConstant.RED],
                SpringForce.STIFFNESS_MEDIUM,
                SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
        );

        SpringAnimation orangeAnimation = SpringAnimationUtils.createSpringAnimation(
                mOrrange,
                SpringAnimation.TRANSLATION_Y,
                -mColorPosition[ColorConstant.ORANGE],
                SpringForce.STIFFNESS_MEDIUM,
                SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
        );
        SpringAnimation yellowAnimation = SpringAnimationUtils.createSpringAnimation(
                mYellow,
                SpringAnimation.TRANSLATION_Y,
                -mColorPosition[ColorConstant.YELLOW],
                SpringForce.STIFFNESS_MEDIUM,
                SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
        );
        SpringAnimation greenAnimation = SpringAnimationUtils.createSpringAnimation(
                mGreen,
                SpringAnimation.TRANSLATION_Y,
                -mColorPosition[ColorConstant.GREEN],
                SpringForce.STIFFNESS_MEDIUM,
                SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
        );

        SpringAnimation blueAnimation = SpringAnimationUtils.createSpringAnimation(
                mBlue,
                SpringAnimation.TRANSLATION_Y,
                -mColorPosition[ColorConstant.BLUE],
                SpringForce.STIFFNESS_MEDIUM,
                SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
        );
        SpringAnimation purpleAnimation = SpringAnimationUtils.createSpringAnimation(
                mPurple,
                SpringAnimation.TRANSLATION_Y,
                -mColorPosition[ColorConstant.PURPLE],
                SpringForce.STIFFNESS_MEDIUM,
                SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
        );
        SpringAnimation unFavoriteAnimation = SpringAnimationUtils.createSpringAnimation(
                mUnFavorite,
                SpringAnimation.TRANSLATION_Y,
                -mColorPosition[ColorConstant.NONE],
                SpringForce.STIFFNESS_MEDIUM,
                SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
        );

        unFavoriteAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                mBlurLayout.setVisibility(View.GONE);
            }
        });
        blurAnimation.start();
        redAnimation.start();
        unFavoriteAnimation.start();
        orangeAnimation.start();
        purpleAnimation.start();
        yellowAnimation.start();
        blueAnimation.start();
        greenAnimation.start();

    }

    private void bindView(View rootView) {
        this.mFavoriteLayout = (RelativeLayout) rootView.findViewById(R.id.favorate_layout);
        this.mBlurLayout = (FrameLayout) rootView.findViewById(R.id.blur_layout);

        this.mRed = (ImageView) rootView.findViewById(R.id.btn_red);
        this.mRed.setOnClickListener(this);
        this.mOrrange = (ImageView) rootView.findViewById(R.id.btn_orrange);
        this.mYellow = (ImageView) rootView.findViewById(R.id.btn_yellow);
        this.mGreen = (ImageView) rootView.findViewById(R.id.btn_green);
        this.mBlue = (ImageView) rootView.findViewById(R.id.btn_blue);
        this.mPurple = (ImageView) rootView.findViewById(R.id.btn_purple);
        this.mUnFavorite = (ImageView) rootView.findViewById(R.id.btn_un_favorite);
        this.mPlayBtn = (ImageView) rootView.findViewById(R.id.play_btn);
        this.mFavoriteBtn = (ImageView) rootView.findViewById(R.id.mark_btn);
        this.mWordTxt = (TextView) rootView.findViewById(R.id.word);
        this.mPronounce = (TextView) rootView.findViewById(R.id.pronounce);
        this.mMeanings = (RecyclerView) rootView.findViewById(R.id.meanings);
        this.mExamples = (TextView) rootView.findViewById(R.id.examples);
        this.mExamplesTxt = (TextView) rootView.findViewById(R.id.example_txt);
        this.mMeaningsTxt = (TextView) rootView.findViewById(R.id.meaning_txt);

    }

    @Override
    public void onClick(View v) {
        removeFromFavoriteTable(mWord.getmId(), mCurrentColor);

        mCurrentColor = ColorConstant.getColorIndex(v);
        insertIntoFavoriteTable(mWord.getmId(), mCurrentColor);
        hideFavorite();
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
}

