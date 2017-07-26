package part3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.vntoeic.bkteam.vntoeicpro.R;

import supportview.FragmentPartExplan;
import supportview.FragmentSummaryListen;
import supportview.IPartControlListen;

/**
 * Created by dainguyen on 7/14/17.
 */

public class FragmentContent extends Fragment {

    private Fragment fragment;
    private FrameLayout frameLayout;
    private int part=0;
    private IPartControlListen iPartControlListen;

    public void setiPartControlListen(IPartControlListen iPartControlListen){
        this.iPartControlListen = iPartControlListen;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            part= bundle.getInt("part");
        }
        setUPData();
    }

    public void setUPData(){
        if(part==3 || part==4){
            fragment = new FragmentPart34Question();
            ((FragmentPart34Question)fragment).setIPartControl(iPartControlListen);
        }else if(part==2|| part==1){
            fragment = new FragmentContentPart12();
            ((FragmentContentPart12)fragment).setiPartControlListen(iPartControlListen);
        }else if(part==5){
            fragment = new FragmentSummaryListen();
            ((FragmentSummaryListen)fragment).setiPartControlListen(iPartControlListen);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frame_layout,container,false);
        frameLayout = (FrameLayout) view.findViewById(R.id.frame_layout);
        getChildFragmentManager().beginTransaction().addToBackStack(null).add(frameLayout.getId(),fragment).commit();
        return view;
    }

    public void reLoadContent(){
        if(frameLayout.getChildCount()>0){
            if(part==5){
                ((FragmentSummaryListen)fragment).reloadContent();
            }else if(part==1|| part==2) {
                if(fragment instanceof FragmentContentPart12) {
                    ((FragmentContentPart12) fragment).reloadData();
                }else {
                    setUPData();
                    getChildFragmentManager().beginTransaction().addToBackStack(null).replace(frameLayout.getId(),fragment).commit();
                }
            }else{
                if(fragment instanceof FragmentPart34Question) {
                    ((FragmentPart34Question) fragment).reloadContent();
                }else{
                   setUPData();
                    getChildFragmentManager().beginTransaction().addToBackStack(null).replace(frameLayout.getId(),fragment).commit();
                }
            }
        }
    }


    public void showResult(){
        if(part==3 || part==4){
            if(fragment instanceof FragmentPart34Question){
                ((FragmentPart34Question)fragment).hideFigure();
                ((FragmentPart34Question)fragment).showResult();
            }else{
                setUPData();
                getChildFragmentManager().beginTransaction().addToBackStack(null).replace(frameLayout.getId(),fragment).commit();
            }
        }else if(part==1 || part==2){
            if(fragment instanceof FragmentContentPart12){
                ((FragmentContentPart12)fragment).showResult();
            }else{
               setUPData();
                getChildFragmentManager().beginTransaction().addToBackStack(null).replace(frameLayout.getId(),fragment).commit();
            }
        }
    }

    public void showFigure(){
            if(fragment instanceof FragmentPart34Question){
                ((FragmentPart34Question)fragment).showFigure();
            }else{
                fragment = new FragmentPart34Question();
                ((FragmentPart34Question)fragment).setIPartControl(iPartControlListen);
                getChildFragmentManager().beginTransaction().addToBackStack(null).replace(frameLayout.getId(),fragment).commit();
            }
    }

    public void showExplan(){
        fragment = new FragmentPartExplan();
        Bundle bundle = iPartControlListen.getBunldeExplan();
        fragment.setArguments(bundle);
        getChildFragmentManager().beginTransaction().addToBackStack(null).replace(frameLayout.getId(),fragment).commit();
    }
}
