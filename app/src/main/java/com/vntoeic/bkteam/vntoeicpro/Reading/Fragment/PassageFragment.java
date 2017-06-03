package com.vntoeic.bkteam.vntoeicpro.Reading.Fragment;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vntoeic.bkteam.vntoeicpro.R;
import com.vntoeic.bkteam.vntoeicpro.Utils.DownloadImage;
import com.vntoeic.bkteam.vntoeicpro.Utils.SaveImage;
import com.vntoeic.bkteam.vntoeicpro.Utils.Tag;

import model.ModelPart7;
import model.ModelPart7Passage;

/**
 * Created by giang on 6/2/17.
 */

public class PassageFragment extends Fragment {
    private static final String MODEL_PART7 = "model_part7";
    private LinearLayout mPassageLayout;
    private Context mContext;
    private ModelPart7 mPart7;
    public static PassageFragment mInstance;
    private OnPassageChanged mListener;

    public interface OnPassageChanged {
        public void onPassageChanged(int currentPart7Id);
    }

    @Override
    public void onAttach(Context context) {
        this.mContext = context;
        if (mContext instanceof OnPassageChanged) {
            mListener = (OnPassageChanged) mContext;
        } else throw new ClassCastException("Context must implement PassageFragment.OnPassageChanged");

        super.onAttach(context);
    }

    public static PassageFragment getInstance(ModelPart7 part7) {
        if (mInstance == null) {
            mInstance = new PassageFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(MODEL_PART7, part7);
        mInstance.setArguments(bundle);

        return mInstance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_passage, container, false);
        this.mPassageLayout = (LinearLayout) view.findViewById(R.id.passage_content);

        getData();
        bindView();
        return view;
    }

    private void bindView() {
        for (ModelPart7Passage passage : mPart7.getPassages()) {
            if (passage.getIsText() == 1) addText(passage);
            else addImage(passage);

        }
    }

    private void addImage(ModelPart7Passage passage) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.imageview_layout, null, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        if (SaveImage.createFile(mContext, passage.getToken()).exists()) {

            Bitmap bitmap = SaveImage.getImageFromStorage(mContext, passage.getToken());
            Glide.with(mContext)
                    .load(bitmap)
                    .into(imageView);
        } else {
            String request = "";
            DownloadImage downloadImage = new DownloadImage(mContext, request, imageView, passage.getToken());
            SaveImage.saveImage(downloadImage.mBitmap, SaveImage.createFile(mContext, passage.getToken()));
        }

        mPassageLayout.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    private void addText(ModelPart7Passage passage) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.textview_layout, null, false);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        Tag tag = new Tag(passage.getContent());
        tag.parse();
        textView.setText(tag.getSpan());
        mPassageLayout.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private void getData() {
        Bundle bundle = getArguments();
        mPart7 = (ModelPart7) bundle.getSerializable(MODEL_PART7);
    }
}
