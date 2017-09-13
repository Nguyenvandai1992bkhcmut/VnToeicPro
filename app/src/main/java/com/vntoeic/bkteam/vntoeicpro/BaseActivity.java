package com.vntoeic.bkteam.vntoeicpro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vntoeic.bkteam.vntoeicpro.R;

import tranthicamgiang.com.foldingeffect.FoldingLayout;
import tranthicamgiang.com.foldingeffect.FoldingViewGroup;

/**
 * Created by giang on 8/9/17.
 */

public abstract class BaseActivity extends AppCompatActivity {


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
