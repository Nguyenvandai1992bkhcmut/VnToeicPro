package listenvocabularry.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.text.DecimalFormat;
import java.util.Locale;

import listenvocabularry.ListenActivity;
import listenvocabularry.adapter.ListenVocabularyAdapter;

/**
 * Created by giang on 7/15/17.
 */

public class ListenVocabularyFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener{

    private static final String LIST_WORDS = "listwords";
    private static final float MAXRATE = (float) 2.0;
    private static final float MINRATE = (float) 0.5;
    private RecyclerView mRecyclerView;
    private SeekBar mSeekBar;
    private ImageView mNextSpeaker;
    private ImageView mPreviousSpeaker;
    private String[] mWords;
    private Context mContext;
    private ImageView mPlay;
    private ListenActivity mActivity;
    private boolean isPlay = false;
    private ListenVocabularyAdapter mAdapter;
    private float mRate = 0;
    private TextView mRateTxt;
    private ImageView mReplayImg;
    private boolean mIsReplay;
    private ImageView mSpeakerImg;
    private TextView mSpeakerTxt;
    private int mSpeaker = 0;

    private Locale[] mLocales = {Locale.ENGLISH, Locale.US, Locale.UK};
    private String[] mSpeakerTxts;
    private TypedArray mSpeakerImgs;
    private DecimalFormat mDecFormat;


    public static ListenVocabularyFragment newInstance(String[] words) {
        ListenVocabularyFragment fragment = new ListenVocabularyFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(LIST_WORDS, words);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mActivity = (ListenActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSpeakerImgs = getResources().obtainTypedArray(R.array.locale_icon);

        mSpeakerTxts = getResources().getStringArray(R.array.locale_name);

        Bundle bundle = getArguments();
        mWords =  bundle.getStringArray(LIST_WORDS);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listen_vocabulary, container, false);

        mDecFormat = new DecimalFormat("####0.00");

        bindView(view);
        bindData();

        return view;
    }

    public float getRate() {
        return mRate;
    }

    private void bindData() {
        mRate = (float) 1.0;
        mIsReplay = false;
        mAdapter = new ListenVocabularyAdapter(mContext, mWords);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
        mSpeakerTxt.setText(mSpeakerTxts[mSpeaker]);
        mSpeakerImg.setImageResource(mSpeakerImgs.getResourceId(mSpeaker, -1));

    }

    private void bindView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mSeekBar = (SeekBar) view.findViewById(R.id.seekbar);
        mSeekBar.setProgress((int) (0.5 / 1.5 * mSeekBar.getMax()));
        mSeekBar.setOnSeekBarChangeListener(this);

        mRateTxt = (TextView) view.findViewById(R.id.rate_num);
        mRateTxt.setText("1.00");

        mReplayImg = (ImageView) view.findViewById(R.id.replay);
        mReplayImg.setOnClickListener(this);


        mPreviousSpeaker = (ImageView) view.findViewById(R.id.previous_speaker);
        mPreviousSpeaker.setOnClickListener(this);

        mNextSpeaker = (ImageView) view.findViewById(R.id.next_speaker);
        mNextSpeaker.setOnClickListener(this);

        mSpeakerImg = (ImageView) view.findViewById(R.id.speaker_img);

        mSpeakerTxt = (TextView) view.findViewById(R.id.speaker_txt);

        mPlay = (ImageView) view.findViewById(R.id.play);
        mPlay.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.previous_speaker:{
                if (mSpeaker > 0) mSpeaker--;
                changeSpeaker();
                break;
            }
            case R.id.next_speaker:{
                if (mSpeaker + 1 < mLocales.length) mSpeaker++;
                changeSpeaker();
                break;
            }
            case R.id.replay: {
                mIsReplay = !mIsReplay;
                mReplayImg.setImageResource(mIsReplay? R.mipmap.ic_replay : R.mipmap.ic_doubleright);
                mActivity.changeReplay(mIsReplay);
                break;
            }
            case R.id.play:{
                if (isPlay){
                    pause();
                } else {
                    isPlay = true;
                    mPlay.setImageResource(R.mipmap.ic_play);
                    mActivity.play();
                }
                break;
            }
        }
    }

    private void changeSpeaker() {
        mSpeakerTxt.setText(mSpeakerTxts[mSpeaker]);
        mSpeakerImg.setImageResource(mSpeakerImgs.getResourceId(mSpeaker, -1));
        mActivity.changeSpeaker(mLocales[mSpeaker]);
    }

    public void pause() {
        isPlay = false;
        mPlay.setImageResource(R.mipmap.ic_pause);
        mActivity.pause();
    }

    public void updateItem(int item, boolean isActive) {
        mAdapter.updateItem(item, isActive);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mRate = (float)progress / (float)seekBar.getMax() * (MAXRATE - MINRATE) + MINRATE;
        mRateTxt.setText(mDecFormat.format((float)Math.round(mRate * 100) / 100) + "");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mActivity.changeRate(mRate);
    }
}
