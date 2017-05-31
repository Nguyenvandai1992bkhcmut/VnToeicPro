package com.vntoeic.bkteam.vntoeicpro.Vocabulary.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vntoeic.bkteam.vntoeicpro.R;
import com.vntoeic.bkteam.vntoeicpro.Vocabulary.Activity.TagActivity;

import model.ModelTag;

/**
 * Created by giang on 5/28/17.
 */

public class TagVocabularyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    ModelTag[] mTags;
    int mSectionId;


    public TagVocabularyAdapter(Context context, ModelTag[] tags) {
        this.mContext = context;
        this.mTags = tags;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tag_vocabulary_item_layout, parent, false);
        return new TagVocabularyItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TagVocabularyItemViewHolder viewHolder = (TagVocabularyItemViewHolder) holder;
        viewHolder.mTagName.setText(mTags[position].getmTagTitle());

    }

    @Override
    public int getItemCount() {
        return mTags.length;
    }

    private class TagVocabularyItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mImageView;
        private TextView mTagName, mLearned;

        public TagVocabularyItemViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.mImageView = (ImageView) view.findViewById(R.id.imageView);
            this.mTagName = (TextView) view.findViewById(R.id.tagName);
            this.mLearned = (TextView) view.findViewById(R.id.learned);
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(mContext, TagActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt(TagActivity.TAG_ID, mTags[getAdapterPosition()].getmTagId());
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        }
    }
}
