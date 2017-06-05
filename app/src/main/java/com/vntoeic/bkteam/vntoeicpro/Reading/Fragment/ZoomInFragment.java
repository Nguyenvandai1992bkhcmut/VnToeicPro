package com.vntoeic.bkteam.vntoeicpro.Reading.Fragment;

import android.graphics.Bitmap;
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

public class ZoomInFragment extends Fragment {

    private static final String IMAGE = "image";
    private static ZoomInFragment mInstance;

    public static ZoomInFragment getmInstance(Bitmap bitmap) {
        if (mInstance == null){
            mInstance = new ZoomInFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putParcelable(IMAGE, bitmap);
        mInstance.setArguments(bundle);
        return mInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_zoomin, container, false);
        Bitmap bitmap = getArguments().getParcelable(IMAGE);
        ImageView imageView = (ImageView) view.findViewById(R.id.expanded_imageview);
        imageView.setImageBitmap(bitmap);
        return view;
    }
}
