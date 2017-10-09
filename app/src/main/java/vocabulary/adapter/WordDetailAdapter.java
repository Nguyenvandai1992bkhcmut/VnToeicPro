package vocabulary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import model.ModelWord;

/**
 * Created by giang on 7/2/17.
 */

public class WordDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mContext;
    private final ModelWord mWord;

    public WordDetailAdapter(Context context, ModelWord word) {
        this.mContext = context;
        this.mWord = word;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_word_detail_meaning, parent, false);

        return new MeaningViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MeaningViewHolder meaningViewHolder = (MeaningViewHolder) holder;

        meaningViewHolder.similar.setVisibility(View.VISIBLE);
        switch (mWord.getmTypes()[position]){
            case "n": {
                meaningViewHolder.imgType.setImageResource(R.mipmap.noun);
                break;
            }
            case "adj": {
                meaningViewHolder.imgType.setImageResource(R.mipmap.adj);
                break;
            }
            case "adv": {
                meaningViewHolder.imgType.setImageResource(R.mipmap.adv);
                break;
            }
            case "v": {
                meaningViewHolder.imgType.setImageResource(R.mipmap.verb);
                break;
            }
            default:meaningViewHolder.imgType.setImageResource(R.mipmap.none);
        }

        meaningViewHolder.textMean.setText(mWord.getmMeanings()[position]);
        meaningViewHolder.textSimilar.setText(mWord.getmSimilars()[position]);
    }

    @Override
    public int getItemCount() {
        return mWord.getmMeanings().length;
    }

    public class MeaningViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgType;
        public TextView textMean, textSimilar;
        public LinearLayout similar;

        public MeaningViewHolder(View itemView) {
            super(itemView);
            this.imgType = (ImageView) itemView.findViewById(R.id.img_type);
            this.textMean = (TextView) itemView.findViewById(R.id.text_mean);
            this.textSimilar = (TextView) itemView.findViewById(R.id.text_similar);
            this.similar = (LinearLayout) itemView.findViewById(R.id.similar);
        }
    }
}
