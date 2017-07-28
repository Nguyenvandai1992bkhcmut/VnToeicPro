package part7;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.vntoeic.bkteam.vntoeicpro.R;

import java.io.Serializable;
import java.util.ArrayList;

import model.IDataQuestion;
import model.IListenPart;
import model.ModelPart6;
import model.ModelPart7;
import supportview.IContentQuestion;

/**
 * Created by dainguyen on 7/19/17.
 */

public class FragmentPagerQuestion extends Fragment {
    private int position =0;

    private IContentQuestion iContentQuestion;
    private int count_question =0;
    ArrayList<FragmentPartQuestion>arrFragment= new ArrayList<>();


    public void setiContentQuestion(IContentQuestion iContentQuestion){
        this.iContentQuestion = iContentQuestion;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            position = bundle.getInt("position");
            count_question =bundle.getInt("countquestion");

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WrapContentHeightViewPager viewPager = new WrapContentHeightViewPager(getContext());
        viewPager.setId(R.id.viewpager);
        AdapterPagerQuestionPart67 adapterPager = new AdapterPagerQuestionPart67(getChildFragmentManager());
        viewPager.setAdapter(adapterPager);
        viewPager.setPageTransformer(false,new CubeOutTransformer());
        viewPager.setCurrentItem(position);
        return viewPager;
    }




    public class AdapterPagerQuestionPart67 extends FragmentPagerAdapter{

        public AdapterPagerQuestionPart67(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            FragmentPartQuestion fragmentPartQuestion = new FragmentPartQuestion();
            Bundle bundle = new Bundle();
            bundle.putInt("position",position);
            fragmentPartQuestion.setArguments(bundle);
            fragmentPartQuestion.setiContentQuestion(iContentQuestion);
            arrFragment.add(fragmentPartQuestion);
            return fragmentPartQuestion;
        }

        @Override
        public int getCount() {
            return count_question;
        }
    }

    public class WrapContentHeightViewPager extends ViewPager {

        /**
         * Constructor
         *
         * @param context the context
         */
        public WrapContentHeightViewPager(Context context) {
            super(context);
        }

        /**
         * Constructor
         *
         * @param context the context
         * @param attrs the attribute set
         */
        public WrapContentHeightViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

            int height = 0;
            for(int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                int h = child.getMeasuredHeight();
                if(h > height) height = h;
            }

            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }


        /**
         * Determines the height of this view
         *
         * @param measureSpec A measureSpec packed into an int
         * @param view the base view with already measured height
         *
         * @return The height of the view, honoring constraints from measureSpec
         */
        private int measureHeight(int measureSpec, View view) {
            int result = 0;
            int specMode = MeasureSpec.getMode(measureSpec);
            int specSize = MeasureSpec.getSize(measureSpec);

            if (specMode == MeasureSpec.EXACTLY) {
                result = specSize;
            } else {
                // set the height from the base view if available
                if (view != null) {
                    result = view.getMeasuredHeight();
                }
                if (specMode == MeasureSpec.AT_MOST) {
                    result = Math.min(result, specSize);
                }
            }
            return result;
        }

    }

}
