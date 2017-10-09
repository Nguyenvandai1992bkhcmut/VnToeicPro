package vocabulary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import model.ModelWordLesson;
import vocabulary.adapter.WordPagerAdapter;

/**
 * Created by giang on 7/2/17.
 */

public class WordDetailMainFragment extends Fragment {

    private static final String WORD_LESSONS = "word lesson";
    private static final String POSITION = "position";
    private ViewPager mViewPager;
    private TextView mTitle;
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


        WordPagerAdapter pagerAdapter = new WordPagerAdapter(getChildFragmentManager(), mWords, mPos);
        mViewPager.setAdapter(pagerAdapter);
    }

    private void bindView(View view) {
        this.mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        this.mTitle = (TextView) view.findViewById(R.id.lessonName);
    }
}
