package gamevocabulary.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;

import gamevocabulary.Fragment.GameFragment;
import gamevocabulary.Fragment.TypingFragment;
import gamevocabulary.GameInterface;

/**
 * Created by giang on 7/11/17.
 */

public class GameTypingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final String mWord;
    private Context mContext;
    private ArrayList<GameFragment.OnShowBackgroundResult> mShowBackgroundResults;
    private GameInterface mListener;
    private TypingFragment mFragment;

    public GameTypingAdapter(Context context, TypingFragment fragment, String word, ArrayList<GameFragment.OnShowBackgroundResult> showBackgroundResults){
        this.mContext = context;
        this.mFragment = fragment;
        this.mWord = word;
        this.mShowBackgroundResults = showBackgroundResults;

        if (context instanceof GameInterface) mListener = (GameInterface) context;
        else throw new ClassCastException("this context must implement GameInterface");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_game_typing, parent, false);
        return new GameTypingItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        GameTypingItemViewHolder itemViewHolder = (GameTypingItemViewHolder) holder;
        mShowBackgroundResults.add(itemViewHolder);
        itemViewHolder.mTextView.setText("");
        itemViewHolder.mTextView.setOnClickListener(itemViewHolder);
    }

    @Override
    public int getItemCount() {
        return mWord.length();
    }

    public class GameTypingItemViewHolder extends RecyclerView.ViewHolder implements GameFragment.OnShowBackgroundResult, View.OnClickListener{
        public TextView mTextView;
        public boolean isTrue;

        public GameTypingItemViewHolder(View itemView) {
            super(itemView);
            this.mTextView = (TextView) itemView.findViewById(R.id.cell_typing);
            this.isTrue = false;
        }

        public void setResult(boolean isTrue){
            this.isTrue = isTrue;
        }

        @Override
        public void onShowBackgroundResult() {

            if (isTrue) mTextView.setBackgroundResource(R.drawable.rectangle_engvi_true);
            else mTextView.setBackgroundResource(R.drawable.rectangle_engvi_false);
        }

        @Override
        public void onClick(View v) {
            mListener.showKeyBoard(mFragment);
        }
    }
}
