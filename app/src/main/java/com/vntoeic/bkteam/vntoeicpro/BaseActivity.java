package com.vntoeic.bkteam.vntoeicpro;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.vntoeic.bkteam.vntoeicpro.CustomNavigation.CustomNavigation;

/**
 * Created by giang on 5/28/17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public FloatingActionButton mFloatingActionButton;
    private boolean isOpen = false;
    public CustomNavigation topNavi, bottomNavi;
    public Context mContext;
    public FrameLayout mFrameLayout, mBlurLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
        mContext = this;

        mFrameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        mBlurLayout = (FrameLayout) findViewById(R.id.blur_effect);
        topNavi = (CustomNavigation) findViewById(R.id.customNavigation);
        bottomNavi = (CustomNavigation) findViewById(R.id.customNavigation2);

        setUpContent();
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floatingButton);
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

    }

    public abstract void setUpContent();

    public abstract void onOpen();
    public abstract void onClose();
}
