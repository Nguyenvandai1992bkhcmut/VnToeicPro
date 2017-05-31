package com.vntoeic.bkteam.vntoeicpro.Vocabulary.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import model.ModelLesson;

/**
 * Created by giang on 5/29/17.
 */

public class LessonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ModelLesson[] mLessons;
    private final Context mContext;

    public LessonAdapter(Context context, ModelLesson[] lessons) {
        this.mContext = context;
        this.mLessons = lessons;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.lesson_item_viewholder, parent, false);
        return new LessonItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LessonItemViewHolder viewHolder = (LessonItemViewHolder) holder;
        viewHolder.mEngText.setText(mLessons[position].getmLessonTitle());
    }

    @Override
    public int getItemCount() {
        return mLessons.length;
    }

    private class LessonItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mEngText, mLearnedText;

        public LessonItemViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.mEngText = (TextView) view.findViewById(R.id.eng_lessonName);
            this.mLearnedText = (TextView) view.findViewById(R.id.learn_txt);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
