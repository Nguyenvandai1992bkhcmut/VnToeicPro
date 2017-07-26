package vocabulary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vntoeic.bkteam.vntoeicpro.R;

/**
 * Created by giang on 6/30/17.
 */
public class WordMeaningAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private final String[] mMeanings;
    private final String[] mTypes;
    private final View.OnClickListener mOnClickListener;

    public WordMeaningAdapter(Context context, String[] types, String[] meanings, View.OnClickListener onClickListener){
        this.mContext = context;
        this.mTypes = types;
        this.mMeanings = meanings;
        this.mOnClickListener = onClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_meaning_word_viewholder, parent, false);

        return new ItemMeaningViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ItemMeaningViewHolder viewHolder = (ItemMeaningViewHolder) holder;
        viewHolder.mTextView.setText(mTypes[position] + ": " + mMeanings[position]);
    }

    @Override
    public int getItemCount() {
        return mMeanings.length;
    }

    class ItemMeaningViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        public ItemMeaningViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(mOnClickListener);
        }

    }
}
