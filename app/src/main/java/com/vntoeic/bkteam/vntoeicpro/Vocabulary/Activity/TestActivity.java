package com.vntoeic.bkteam.vntoeicpro.Vocabulary.Activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.vntoeic.bkteam.vntoeicpro.R;
import com.vntoeic.bkteam.vntoeicpro.Reading.Fragment.PassageFragment;
import com.vntoeic.bkteam.vntoeicpro.Reading.Fragment.ZoomInFragment;
import com.vntoeic.bkteam.vntoeicpro.Reading.Part7.Part7ContentFragment;

import model.ModelPart7;
import model.ModelPart7Passage;

/**
 * Created by giang on 6/1/17.
 */

public class TestActivity extends AppCompatActivity implements PassageFragment.OnPassageChanged, PassageFragment.OnZoomInImage{

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);


        Part7ContentFragment part7Fragment = new Part7ContentFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout, part7Fragment).commit();

    }

    @Override
    public void onPassageChanged(int currentPart7Id) {

    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading ... ");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onZoomInImage(Bitmap bitmap, View imageView) {
        ZoomInFragment zoomInFragment = ZoomInFragment.getmInstance(bitmap);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, zoomInFragment)
                .addToBackStack(null)
                .addSharedElement(imageView, "imageTransition")
                .commit();

    }
}
