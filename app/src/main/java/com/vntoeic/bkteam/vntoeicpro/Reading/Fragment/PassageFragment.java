package com.vntoeic.bkteam.vntoeicpro.Reading.Fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vntoeic.bkteam.vntoeicpro.R;
import com.vntoeic.bkteam.vntoeicpro.Utils.ImageUtils;
import com.vntoeic.bkteam.vntoeicpro.Utils.Tag;
import com.vntoeic.bkteam.vntoeicpro.Vocabulary.Activity.TestActivity;


import java.io.File;

import model.ModelPart7;
import model.ModelPart7Passage;

/**
 * Created by giang on 6/2/17.
 */

public class PassageFragment extends Fragment {

    public interface OnZoomInImage {
        void onZoomInImage(Bitmap bitmap, View imageView);
    }

    private static final String MODEL_PART7 = "model_part7";
    private LinearLayout mPassageLayout;
    private Context mContext;
    private ModelPart7 mPart7;
    public static PassageFragment mInstance;
    private OnPassageChanged mListener;
    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;

    private View mView;
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
        mView = view;
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
        final ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
//        File file = SaveImage.createFile(mContext, passage.getToken());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LinearLayout expandedLayout = (LinearLayout) mView.findViewById(R.id.expandedLayout);
//                expandedLayout.setVisibility(View.VISIBLE);
//                zoomImageFromThumb(imageView, imageView.getDrawable());

                createZoomInFragment(imageView, ((BitmapDrawable)imageView.getDrawable()).getBitmap());
            }
        });
        String path = mContext.getFilesDir() + "/" + passage.getToken();
        File file = new File(path);
        if (file.exists()) {
            imageView.setImageBitmap( BitmapFactory.decodeFile(path));
//            imageView.setImageDrawable(Drawable.createFromPath(path));
        } else{
            ((TestActivity)mContext).showProgressDialog();
            ImageUtils imageUtils = new ImageUtils(imageView, mContext);
            imageUtils.execute(passage.getToken());
        }


        mPassageLayout.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    private void createZoomInFragment(View imageView, Bitmap bitmap) {


        ((TestActivity)mContext).onZoomInImage(bitmap, imageView);
    }

    private void addText(ModelPart7Passage passage) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.textview_layout, null, false);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        Tag tag = new Tag(passage.getContent());
        tag.parse();
        textView.setText(tag.getSpan());
        mPassageLayout.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void getData() {
        Bundle bundle = getArguments();
        mPart7 = (ModelPart7) bundle.getSerializable(MODEL_PART7);
    }

    private void zoomImageFromThumb(final View thumbView, Drawable drawable) {
        // If there's an animation in progress, cancel it immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) mView.findViewById(R.id.expanded_imageview);
        expandedImageView.setImageDrawable(drawable);

        // Calculate the starting and ending bounds for the zoomed-in image. This step
        // involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail, and the
        // final bounds are the global visible rectangle of the container view. Also
        // set the container view's offset as the origin for the bounds, since that's
        // the origin for the positioning animation properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        mView.findViewById(R.id.expandedLayout).getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final bounds using the
        // "center crop" technique. This prevents undesirable stretching during the animation.
        // Also calculate the start scaling factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation begins,
        // it will position the zoomed-in view in the place of the thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations to the top-left corner of
        // the zoomed-in view (the default is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and scale properties
        // (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left,
                        finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top,
                        finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down to the original bounds
        // and show the thumbnail instead of the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel, back to their
                // original values.
                AnimatorSet set = new AnimatorSet();
                set
                        .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView, View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView, View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
                LinearLayout expandedLayout = (LinearLayout) mView.findViewById(R.id.expandedLayout);
                expandedLayout.setVisibility(View.INVISIBLE);
            }
        });
    }
}
