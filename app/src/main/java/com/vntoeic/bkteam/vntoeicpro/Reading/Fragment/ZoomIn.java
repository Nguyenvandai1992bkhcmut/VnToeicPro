package com.vntoeic.bkteam.vntoeicpro.Reading.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vntoeic.bkteam.vntoeicpro.R;

/**
 * Created by giang on 6/4/17.
 */

public class ZoomIn extends Fragment {

    private static ZoomIn mInstance;

//    public static ZoomIn createInstance(Drawable drawable) {
//        if (mInstance == null) {
//            mInstance = new ZoomIn();
//        }
//
//        Bundle bundle = new Bundle();
//        bundle.put
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zoomin, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.expanded_imageview);

        return view;
    }
}
