package vocabulary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import model.ModelTag;
import vocabulary.activity.VocabularyActivity;

/**
 * Created by giang on 6/25/17.
 */

public class TagVocabularyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final VocabularyActivity mContext;
    private ModelTag[] mTags;

    public TagVocabularyAdapter(Context context, ModelTag[] tags){
        this.mContext = (VocabularyActivity) context;
        mTags = tags;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tag_vocabulary, parent, false);
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

    public class TagVocabularyItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mImageView;
        private TextView mTagName, mLearned;

        public TagVocabularyItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.mImageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.mTagName = (TextView) itemView.findViewById(R.id.tagName);
            this.mLearned = (TextView) itemView.findViewById(R.id.learned);
        }

        @Override
        public void onClick(View v) {
            mContext.openTagFragment(mTags[getAdapterPosition()].getmTagId());
        }
    }
}
