package part3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vntoeic.bkteam.vntoeicpro.R;

import supportview.IPartControlListen;
import supportview.PartPractiseAcitvity;

/**
 * Created by dainguyen on 7/14/17.
 */

public class FragmentViewPagerListen extends Fragment {
    private ViewPager viewPager;

    private Fragment fragment_content;
    private Fragment fragment_summary;
    private IPartControlListen iPartControlListen;
    private int part;

    public void setiPartControlListen(IPartControlListen iPartControlListen){
        this.iPartControlListen = iPartControlListen;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            part=bundle.getInt("part");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager_listenning,container,false);
        viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        AdapterPager adapterPager = new AdapterPager(getChildFragmentManager());
        viewPager.setAdapter(adapterPager);
        return view;
    }

    public class AdapterPager extends FragmentPagerAdapter{

        public AdapterPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                fragment_content= new FragmentContent();
                Bundle bundle = new Bundle();
                bundle.putInt("part",part);
                ((FragmentContent)fragment_content).setiPartControlListen(iPartControlListen);
                fragment_content.setArguments(bundle);
                return fragment_content;
            }else{
                fragment_summary= new FragmentContent();
                Bundle bundle = new Bundle();
                bundle.putInt("part",5);
                ((FragmentContent)fragment_summary).setiPartControlListen(iPartControlListen);
                fragment_summary.setArguments(bundle);
                return fragment_summary;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    public void reLoadContent(){
        ((FragmentContent)fragment_content).reLoadContent();
        viewPager.setCurrentItem(0);
    }

    public void reLoadSummary(){
        ((FragmentContent)fragment_summary).reLoadContent();
    }


    public void showFigure(){
        ((FragmentContent)fragment_content).showFigure();
        viewPager.setCurrentItem(0);
    }

    public void showQuestion(){
        ((FragmentContent)fragment_content).showResult();
        viewPager.setCurrentItem(0);
    }

    public void showExplan(){
        ((FragmentContent)fragment_content).showExplan();
        viewPager.setCurrentItem(0);
    }
}
