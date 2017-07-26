package testreading;

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

import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.vntoeic.bkteam.vntoeicpro.R;

import part5.FragmentSummary;
import supportview.FragmentSummaryPart;
import supportview.ISummaryPart;

/**
 * Created by dainguyen on 7/20/17.
 */

public class FragmentAdapterSummary extends Fragment {
    private int summary=5;
    ViewPager viewPager;

    public IReadTestSummary iReadTestSummary ;
    public interface  IReadTestSummary{
        public Bundle getBunldeFragment(int part);
        public int getSummary();
        public void changePager(int part);

    }

    public void setiReadTestSummary(IReadTestSummary iReadTestSummary){
        this.iReadTestSummary = iReadTestSummary;
    }
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void changePager(int part){
        if(part!=viewPager.getCurrentItem()) {
            viewPager.setCurrentItem(part);
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         viewPager = new ViewPager(getContext());
        viewPager.setId(R.id.viewpager);
        AdapterPagerSummary adapterPager = new AdapterPagerSummary(getChildFragmentManager());
        viewPager.setAdapter(adapterPager);
        viewPager.setPageTransformer(false,new AccordionTransformer());
        viewPager.setCurrentItem(iReadTestSummary.getSummary()-5);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                iReadTestSummary.changePager(5+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return viewPager;
    }


    public  class AdapterPagerSummary extends FragmentPagerAdapter {

        public AdapterPagerSummary(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                FragmentSummary fragmentSummary = new FragmentSummary();
                Bundle bunlde = iReadTestSummary.getBunldeFragment(5);
                fragmentSummary.setArguments(bunlde);
                fragmentSummary.setItemClick((ISummaryPart) iReadTestSummary);
                return fragmentSummary;
            }else {
                FragmentSummaryPart fragmentSummaryPart = new FragmentSummaryPart();
                Bundle bundle = iReadTestSummary.getBunldeFragment(5+position);
                fragmentSummaryPart.setArguments(bundle);
                fragmentSummaryPart.setItemClick((ISummaryPart) iReadTestSummary);
                return fragmentSummaryPart;
            }

        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
