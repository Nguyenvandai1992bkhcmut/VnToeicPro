package listenvocabularry.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;

import listenvocabularry.ListenActivity;

/**
 * Created by giang on 7/15/17.
 */

public class ListenVocabularyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final String[] mWords;
    private final Context mContext;
    private ArrayList<IActiveItem> mActiveItems = new ArrayList<>();

    public ListenVocabularyAdapter(Context context, String[] words){
        this.mContext = context;
        this.mWords = words;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_listen_vocabulary, parent, false);
        return new ItemListenVocabularyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemListenVocabularyViewHolder viewHolder = (ItemListenVocabularyViewHolder) holder;


        if (position == mActiveItems.size()) mActiveItems.add(viewHolder);
        viewHolder.mTextView.setText(mWords[position]);
    }

    @Override
    public int getItemCount() {
        return mWords.length;
    }

    public void updateItem(int item, boolean isActive) {
        mActiveItems.get(item).onActive(isActive);
    }

    interface IActiveItem {
        void onActive(boolean isActive);
    }

    class ItemListenVocabularyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, IActiveItem{
        private ImageView mImageView;
        private TextView mTextView;

        public ItemListenVocabularyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.mImageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.mTextView = (TextView) itemView.findViewById(R.id.textView);
        }

        @Override
        public void onClick(View v) {
            ((ListenActivity)mContext).onClickItem(getAdapterPosition());
        }

        @Override
        public void onActive(boolean isActive) {
            if (isActive) {
                this.mImageView.setVisibility(View.VISIBLE);
                this.mTextView.setTextColor(mContext.getResources().getColor(R.color.text_icon));
            } else {
                this.mImageView.setVisibility(View.INVISIBLE);
                this.mTextView.setTextColor(Color.WHITE);
            }
        }

    }
}
