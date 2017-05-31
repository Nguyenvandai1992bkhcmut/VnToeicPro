package com.vntoeic.bkteam.vntoeicpro.CustomNavigation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;

/**
 * Created by giang on 5/28/17.
 */

public class CustomNavigation extends FrameLayout {
    private Context mContext;
    private ArrayList<ItemView> mItemViews = new ArrayList<>();

    private boolean isTop;
    private int textColor;
    private int textSize;
    private int imageSize;
    private int height;
    private int duration;
    private GridView gridView;
    private MyAdapter adapter;
    private String[] titles;
    private int[] icons;
    private LayoutListener listener;

    public CustomNavigation(@NonNull Context context, boolean isTop) {
        super(context);
        mContext = context;
        this.isTop = isTop;
    }

    public CustomNavigation(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context, attrs);
    }

    public void setTitles(int titleArray) {
        titles = mContext.getResources().getStringArray(titleArray);
    }

    public void setIcons(int iconArray) {
        icons = mContext.getResources().getIntArray(iconArray);
    }

    private void init(Context context, AttributeSet attrs) {
        setSaveEnabled(true);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomNavigation, 0, 0);
        this.isTop = typedArray.getBoolean(R.styleable.CustomNavigation_isTop, true);
        this.textColor = typedArray.getColor(R.styleable.CustomNavigation_textColor, Color.WHITE);
        this.textSize = typedArray.getDimensionPixelSize(R.styleable.CustomNavigation_textSize, 0);
        this.imageSize = typedArray.getDimensionPixelSize(R.styleable.CustomNavigation_imageSize, 0);
        this.duration = typedArray.getInteger(R.styleable.CustomNavigation_duration, 0);

        int getTitles = typedArray.getResourceId(R.styleable.CustomNavigation_titles, 0);
        if (getTitles != 0){
            this.titles = getResources().getStringArray(getTitles);
        }

        int getIcons = typedArray.getResourceId(R.styleable.CustomNavigation_icons, 0);
        if (getIcons != 0) {
            this.icons = getResources().getIntArray(getIcons);
        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        gridView = (GridView) inflater.inflate(R.layout.custom_navigation, this, false);

        listener = new LayoutListener();
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(listener);
        gridView.setNumColumns(4);
        adapter = new MyAdapter(mContext, mItemViews);
        gridView.setAdapter(adapter);
        addView(gridView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        typedArray.recycle();

    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        invalidate();
    }

    class LayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

        @Override
        public void onGlobalLayout() {
            height = gridView.getMeasuredHeight();
//            if ( Build.VERSION.SDK_INT >= 16) gridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }

        public int getHeight() {
            return height;
        }
    }

    public void onOpen() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(isTop? -height : height, 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                gridView.setTranslationY(value);
                CustomNavigation.this.invalidate();
            }
        });
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
        invalidate();
    }

    public void onClose() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, isTop? -height : height);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                gridView.setTranslationY(value);
                CustomNavigation.this.invalidate();
            }
        });

        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();


        invalidate();
    }

    class MyAdapter extends BaseAdapter {

        private Context mContext;
        private ArrayList<ItemView> mItemViews;
        MyAdapter(Context context, ArrayList<ItemView> itemViews) {
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void addItem(ItemView itemView) {
            this.mItemViews.add(itemView);
            notifyDataSetChanged();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                View itemView = inflater.inflate(R.layout.menu_item_view, null, false);
                MyViewHolder myViewHolder = new MyViewHolder(itemView);
                myViewHolder.mTextView.setText(titles[position]);
                return myViewHolder.mView;
            } else return convertView;

        }
    }

    class MyViewHolder {
        TextView mTextView;
        View mView;
        MyViewHolder(View itemView) {
            this.mTextView = (TextView) itemView.findViewById(R.id.text);
            this.mView = itemView;
        }
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(state);
        height = savedState.value;
        Toast.makeText(mContext, "on restore instance state", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.value = height;
        Toast.makeText(mContext, "on save instance state", Toast.LENGTH_SHORT).show();
        return savedState;
    }

    private static class SavedState extends BaseSavedState {
        int value;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            value = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(value);
        }

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

}
