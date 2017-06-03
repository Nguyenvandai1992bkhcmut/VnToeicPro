package com.vntoeic.bkteam.vntoeicpro.Reading.Fragment;

import android.view.View;

import com.vntoeic.bkteam.vntoeicpro.BaseFragment;

/**
 * Created by giang on 6/2/17.
 */

public class Part7TestFragment extends BaseFragment {
    @Override
    public void setUpContent(int contentLayoutId) {

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
}
