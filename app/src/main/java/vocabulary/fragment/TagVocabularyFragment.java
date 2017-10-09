package vocabulary.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vntoeic.bkteam.vntoeicpro.R;

import vocabulary.ScrollableFragmentInterface;
import vocabulary.adapter.TagPagerAdapter;

/**
 * Created by giang on 6/27/17.
 */

public class TagVocabularyFragment extends Fragment implements LessonPagerFragment.LessonInterface, View.OnClickListener{
    private static final String TAG_ID = "tag id";
    private int mTagId;
    public ViewPager mViewPager;
    private FragmentManager mFragmentManager ;
    private ScrollableFragmentInterface mWordLessonPagerFragment;
    private LessonPagerFragment mLessonPagerFragment;
    private ImageView mFirstDot, mSecondDot;
    private ImageView mLeftIcon, mRightIcon;
    private ImageView mDoneIcon, mCancelIcon;
    private boolean isSingleMode = true;


    public static TagVocabularyFragment newInstance(int tagId) {
        TagVocabularyFragment fragment = new TagVocabularyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TAG_ID, tagId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tag, container, false);
        mFragmentManager = getChildFragmentManager();
        setUpContent(view);
        return view;
    }

    private void setUpContent(View view) {
        Bundle bundle = getArguments();
        mTagId = bundle.getInt(TAG_ID);

        bindView(view);

        TagPagerAdapter adapter = new TagPagerAdapter(mFragmentManager, this);
        if (mTagId == 16) {
            /**
             * Favorite fragment
             */
            if (mLessonPagerFragment == null) mLessonPagerFragment = LessonPagerFragment.create(mTagId);
            if (mWordLessonPagerFragment == null) mWordLessonPagerFragment = new FavoriteFragment();

        } else {
            if (mLessonPagerFragment == null) mLessonPagerFragment = LessonPagerFragment.create(mTagId);
            if (mWordLessonPagerFragment == null) mWordLessonPagerFragment = WordLessonPagerFragment.create(mTagId);

        }

        adapter.addFragment(mLessonPagerFragment);
        adapter.addFragment((Fragment) mWordLessonPagerFragment);

        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(mOnPageChangeListener);
        setupDotLayout();
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setupDotLayout();
            if (position == 0) {
                showIcon(mRightIcon);
                showIcon(mLeftIcon);
            } else {
                Animator.AnimatorListener listener = new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mLessonPagerFragment.setSingleMode(isSingleMode);
                        mRightIcon.setVisibility(View.GONE);
                        mDoneIcon.setVisibility(View.GONE);
                        mCancelIcon.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                };
                hideIcon(mRightIcon, listener);
                hideIcon(mDoneIcon, listener);
                hideIcon(mCancelIcon, listener);
                showIcon(mLeftIcon);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {


        }
    };

    private void hideIcon(final ImageView view, Animator.AnimatorListener animatorListener) {

        ValueAnimator scaleAnimator = ValueAnimator.ofFloat(1, 0);
        scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setScaleX(value);
                view.setScaleY(value);
            }
        });

        if (animatorListener != null) scaleAnimator.addListener(animatorListener);
        ValueAnimator rotateAnimator = ValueAnimator.ofFloat(0, 720);
        rotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setRotation(value);
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
        set.play(rotateAnimator).with(scaleAnimator);
        set.start();
    }

    private void showIcon(final ImageView view) {
        view.setScaleX(0);
        view.setScaleY(0);
        view.setVisibility(View.VISIBLE);

        ValueAnimator scaleAnimator = ValueAnimator.ofFloat(0, 1);
        scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setScaleX(value);
                view.setScaleY(value);
            }
        });

        ValueAnimator rotateAnimator = ValueAnimator.ofFloat(0, -720);
        rotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setRotation(value);
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
        set.play(rotateAnimator).with(scaleAnimator);
        set.start();
    }

    private void setupDotLayout() {
        int[] dotColors = getResources().getIntArray(R.array.dotColor);
        this.mFirstDot.setColorFilter(dotColors[mViewPager.getCurrentItem()]);
        this.mSecondDot.setColorFilter(dotColors[1 - mViewPager.getCurrentItem()]);
    }
    private void bindView(View view) {
        this.mCancelIcon = (ImageView) view.findViewById(R.id.cancel_icon);
        mCancelIcon.setOnClickListener(this);
        this.mDoneIcon = (ImageView) view.findViewById(R.id.done_icon);
        this.mDoneIcon.setOnClickListener(this);
        this.mLeftIcon = (ImageView) view.findViewById(R.id.left_icon);
        this.mLeftIcon.setOnClickListener(this);
        this.mRightIcon = (ImageView) view.findViewById(R.id.right_icon);
        this.mRightIcon.setOnClickListener(this);
        this.mFirstDot = (ImageView) view.findViewById(R.id.firstDot);
        this.mSecondDot = (ImageView) view.findViewById(R.id.secondDot);
        this.mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
    }

    @Override
    public void changeToWordLessonFragment(int position) {
        mViewPager.setCurrentItem(1, true);
        mWordLessonPagerFragment.scrollTo(position);
    }

    @Override
    public void onClick(View v) {

        Animator.AnimatorListener cancelListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mCancelIcon.setVisibility(View.GONE);
                showIcon(mLeftIcon);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };
        Animator.AnimatorListener doneListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mDoneIcon.setVisibility(View.GONE);
                showIcon(mRightIcon);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };
        Animator.AnimatorListener rightListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mRightIcon.setVisibility(View.GONE);
                showIcon(mDoneIcon);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };
        Animator.AnimatorListener leftListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mLeftIcon.setVisibility(View.GONE);
                showIcon(mCancelIcon);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };

        switch (v.getId()) {
            case R.id.right_icon:
                this.isSingleMode = !this.isSingleMode;
                this.mLessonPagerFragment.setSingleMode(isSingleMode);

                hideIcon(mLeftIcon, leftListener);
                hideIcon(mRightIcon, rightListener);
                break;
            case R.id.left_icon:

                if (mViewPager.getCurrentItem() == 1) mViewPager.setCurrentItem(0);
                break;
            case R.id.done_icon:
                this.isSingleMode = true;

                boolean createDialogSuccess = mLessonPagerFragment.mAdapter.createDialog();
                if (createDialogSuccess) {
                    hideIcon(mDoneIcon, doneListener);
                    hideIcon(mCancelIcon, cancelListener);
                } else {
                    mLessonPagerFragment.mAdapter.changeMode(true);
                    this.isSingleMode = true;

                    hideIcon(mCancelIcon, cancelListener);
                    hideIcon(mDoneIcon, doneListener);
                }

                break;
            case R.id.cancel_icon:
                mLessonPagerFragment.mAdapter.changeMode(true);
                this.isSingleMode = true;

                hideIcon(mCancelIcon, cancelListener);
                hideIcon(mDoneIcon, doneListener);
                break;
        }
    }
}
