package vocabulary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.Timer;
import java.util.TimerTask;

import model.ModelWord;
import model.ModelWordLesson;
import vocabulary.activity.VocabularyActivity;
import vocabulary.adapter.WordDetailAdapter;
import vocabulary.adapter.WordPagerAdapter;

/**
 * Created by giang on 7/2/17.
 */

public class WordDetailMainFragment extends Fragment {

    private static final String WORD_LESSONS = "word lesson";
    private static final String POSITION = "position";
    private ViewPager mViewPager;
    private TextView mTitle;
    private ImageView mCircleImage;
    private Context mContext;
    private ModelWordLesson[] mWords;
    private int mPos;

    public static WordDetailMainFragment newInstance(ModelWordLesson[] wordLessons, int currentPos){
        WordDetailMainFragment fragment = new WordDetailMainFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(WORD_LESSONS, wordLessons);
        bundle.putInt(POSITION, currentPos);
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
        Bundle bundle = getArguments();
        this.mWords = (ModelWordLesson[]) bundle.getSerializable(WORD_LESSONS);
        this.mPos = bundle.getInt(POSITION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_detail_main, container, false);
        bindView(view);
        setUpData();
        bindData();
        return view;
    }

    private void setUpData() {

    }

    private void bindData() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, final float positionOffset, int positionOffsetPixels) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ((VocabularyActivity)mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(positionOffset >=0.9)mCircleImage.setImageResource(R.mipmap.iconcricle0);
                                else  if(positionOffset >0.8f)mCircleImage.setImageResource(R.mipmap.iconcricle0);
                                else  if(positionOffset >.7f )mCircleImage.setImageResource(R.mipmap.iconcricle1);
                                else  if(positionOffset >0.6f )mCircleImage.setImageResource(R.mipmap.iconcricle2);
                                else  if(positionOffset >0.5f )mCircleImage.setImageResource(R.mipmap.iconcricle3);
                                else  if(positionOffset >0.4f )mCircleImage.setImageResource(R.mipmap.iconcricle4);
                                else  if(positionOffset >0.3f )mCircleImage.setImageResource(R.mipmap.iconcricle5);
                                else  if(positionOffset >0.3f )mCircleImage.setImageResource(R.mipmap.iconcricle6);
                                else  if(positionOffset >0.1f )mCircleImage.setImageResource(R.mipmap.iconcricle7);
                                else mCircleImage.setImageResource(R.mipmap.iconcricle8);
                            }
                        });
                    }
                }, 50);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        WordPagerAdapter pagerAdapter = new WordPagerAdapter(getChildFragmentManager(), mWords, mPos);
        mViewPager.setAdapter(pagerAdapter);
    }

    private void bindView(View view) {
        this.mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        this.mTitle = (TextView) view.findViewById(R.id.lessonName);
        this.mCircleImage = (ImageView) view.findViewById(R.id.circleImg);
    }
}
