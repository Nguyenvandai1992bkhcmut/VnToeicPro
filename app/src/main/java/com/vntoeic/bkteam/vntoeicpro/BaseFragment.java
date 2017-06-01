package com.vntoeic.bkteam.vntoeicpro;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.vntoeic.bkteam.vntoeicpro.CustomNavigation.CustomNavigation;

/**
 * Created by giang on 5/28/17.
 */

public abstract class BaseFragment extends Fragment {
    public FloatingActionButton mFloatingActionButton;
    private boolean isOpen = false;
    public CustomNavigation topNavi, bottomNavi;
    public Context mContext;
    public FrameLayout mFrameLayout, mBlurLayout;

    public void setItemOnClick(CustomNavigation.OnItemClickedListener listener) {
        topNavi.setOnItemClickedListener(listener);
        bottomNavi.setOnItemClickedListener(listener);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);

        mContext = getActivity();

        mFrameLayout = (FrameLayout) view.findViewById(R.id.frame_layout);
        mBlurLayout = (FrameLayout) view.findViewById(R.id.blur_effect);
        topNavi = (CustomNavigation) view.findViewById(R.id.customNavigation);
        bottomNavi = (CustomNavigation) view.findViewById(R.id.customNavigation2);

        setUpContent(R.id.frame_layout);
        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingButton);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpen = !isOpen;
                if (isOpen) {
                    mBlurLayout.setVisibility(View.VISIBLE);
                    onOpen();
                } else {
                    mBlurLayout.setVisibility(View.GONE);
                    onClose();
                }
            }
        });

        mBlurLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClose();
                isOpen = !isOpen;
                mBlurLayout.setVisibility(View.GONE);

            }
        });

        return view;
    }


    public abstract void setUpContent(int contentLayoutId);

    public abstract void onOpen();
    public abstract void onClose();
}