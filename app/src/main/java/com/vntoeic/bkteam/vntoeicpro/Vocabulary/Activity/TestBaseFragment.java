package com.vntoeic.bkteam.vntoeicpro.Vocabulary.Activity;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.vntoeic.bkteam.vntoeicpro.BaseFragment;
import com.vntoeic.bkteam.vntoeicpro.CustomComponent.CustomNavigation.CustomNavigation;
import com.vntoeic.bkteam.vntoeicpro.R;
import com.vntoeic.bkteam.vntoeicpro.Vocabulary.Fragment.TestContentFragment;

/**
 * Created by giang on 5/28/17.
 */

public class TestBaseFragment extends BaseFragment implements CustomNavigation.OnItemClickedListener{
    @Override
    public void setUpContent(int contentLayoutId) {
        topNavi.setTitles(R.array.title_menu2);
        bottomNavi.setTitles(R.array.title_menu2);

        setItemOnClick(this);

        TestContentFragment testContentFragment = new TestContentFragment();
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(contentLayoutId, testContentFragment).commit();

//        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.activity_main, mFrameLayout);
//        mFrameLayout.addView(view);


    }

    @Override
    public void onOpen() {
        topNavi.setVisibility(View.VISIBLE);
        bottomNavi.setVisibility(View.VISIBLE);

        topNavi.onOpen();
        bottomNavi.onOpen();
    }

    @Override
    public void onClose() {
        topNavi.onClose();
        bottomNavi.onClose();
    }


    @Override
    public void onTopItemClicked(int index) {
        switch (index) {
            case 0: {
                Toast.makeText(mContext, "Top on click item 0", Toast.LENGTH_SHORT).show();
                break;
            }
            default:{
                Toast.makeText(mContext, "Top on click another", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    @Override
    public void onBottomItemClicked(int index) {

        switch (index) {
            case 0: {
                Toast.makeText(mContext, "Bottom on click item 0", Toast.LENGTH_SHORT).show();
                break;
            }
            default:{
                Toast.makeText(mContext, "Bottom on click another", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}
