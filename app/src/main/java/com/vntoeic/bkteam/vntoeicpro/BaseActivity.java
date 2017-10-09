package com.vntoeic.bkteam.vntoeicpro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.FacebookSdk;

import tranthicamgiang.vntoei.com.foldingeffect.FoldingLayout;
import tranthicamgiang.vntoei.com.foldingeffect.FoldingViewGroup;

/**
 * Created by giang on 10/8/17.
 */

public abstract class BaseActivity extends AppCompatActivity{
    private FoldingViewGroup mRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_base);

        setUpLayoutContent();

        setUpDrawerLayout();
    }

    private void setUpDrawerLayout() {
        mRoot = (FoldingViewGroup) findViewById(R.id.foldingViewgroup);
        FoldingLayout foldingLayout = (FoldingLayout) findViewById(R.id.folding_layout);
        View rightFrame = findViewById(R.id.right_frame);
        foldingLayout.setFoldListener(mRoot);
        mRoot.setView(foldingLayout, rightFrame);

        NavigationFragment fragment = new NavigationFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.left_frame, fragment)
                .commit();

    }


    public void onClickMenu(){
        if (mRoot.getFoldFactor() == 1) {
            mRoot.openNav();
        } else mRoot.closeNav();
    }


//    protected abstract void cancelLoginFacebook();

//    protected abstract void successLoginFacebook(LoginResult loginResult);


    protected abstract void setUpLayoutContent();
}
