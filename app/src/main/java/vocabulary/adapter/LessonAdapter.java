package vocabulary.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.vntoeic.bkteam.vntoeicpro.R;

import java.util.ArrayList;

import customview.CustomDialog;
import customview.custom_checkbox.CheckBoxController;
import customview.custom_checkbox.CustomCheckBox;
import customview.custom_checkbox.ECheckable;
import customview.statable_viewgroup.StatableViewGroup;
import model.ModelLesson;
import model.ModelStatableLesson;
import vocabulary.activity.VocabularyActivity;
import vocabulary.fragment.LessonPagerFragment;

/**
 * Created by giang on 6/27/17.
 */

public class LessonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements CustomDialog.DialogOnClickListener {
    private final ModelStatableLesson[] mLessons;
    private final Context mContext;
    private final TagPagerAdapter mPagerAdapter;
    private CheckBoxController mCheckBoxController;
    private boolean isSingleMode = true;
    private ArrayList<LessonItemViewHolder> mViewHolderList= new ArrayList<>();
    private ArrayList<Integer> mListLessonId = new ArrayList<>();
    private int mCurrentItemClicked = -1;
    private CustomDialog mCustomDialog;


    public LessonAdapter(Context context, ModelStatableLesson[] lessons, TagPagerAdapter pagerAdapter) {
        this.mContext = context;
        this.mLessons = lessons;
        this.mCheckBoxController = new CheckBoxController();
        this.mPagerAdapter = pagerAdapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_lesson_viewholder, parent, false);
        return new LessonItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        LessonItemViewHolder viewHolder = (LessonItemViewHolder) holder;
        if (mViewHolderList.size() <= position) mViewHolderList.add(viewHolder);
        viewHolder.mLesson.setText(mLessons[position].mLesson.getmLessonTitle());
        viewHolder.mCheckBox.setTag(position);

        viewHolder.mCheckBox.setChecked(mLessons[position].isChecked());
        viewHolder.mStatableViewGroup.setEnable(!isSingleMode);
    }

    @Override
    public int getItemCount() {
        return mLessons.length;
    }

    public void changeMode(boolean singleMode) {
        mListLessonId.clear();
        for (ModelStatableLesson statableLesson :
                mLessons) {
            statableLesson.setCheck(false);
        }
        this.isSingleMode = singleMode;
        for (int i = 0; i < mViewHolderList.size(); i++) {
            mViewHolderList.get(i).mStatableViewGroup.setEnable(!isSingleMode);
        }

        notifyDataSetChanged();
    }

    @Override
    public void dialogOnClick(int index) {
        switch (index){
            case 1:
                mPagerAdapter.changeToWordLessonFragment(mCurrentItemClicked);
                break;
            case 2:
//                if (isSingleMode) mListLessonId.add(mLessons[mCurrentItemClicked].mLesson.getmLessonId());
                ((VocabularyActivity)mContext).onOpenLearningActivity(mListLessonId);
                break;
            case 3:
//                if (isSingleMode) mListLessonId.add(mLessons[mCurrentItemClicked].mLesson.getmLessonId());
                ((VocabularyActivity)mContext).onOpenPractingActivity(mListLessonId);
                break;
            case 4:
//                if (isSingleMode) mListLessonId.add(mLessons[mCurrentItemClicked].mLesson.getmLessonId());
                ((VocabularyActivity)mContext).onOpenListenActivity(mListLessonId);
                break;
        }

        changeMode(true);
    }

    public boolean createDialog() {
        if (mListLessonId.size() == 0) return false;
        mCustomDialog = new CustomDialog(mContext, R.style.dialog_tran);
        mCustomDialog.setOnClickListener(this);
        mCustomDialog.show();
        return true;
    }

    private class LessonItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mLesson, mLearnedText;
        private StatableViewGroup mStatableViewGroup;
        private CheckBox mCheckBox;

        public LessonItemViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mStatableViewGroup = (StatableViewGroup) view.findViewById(R.id.statableViewGroup);

            mCheckBox = (CheckBox) mStatableViewGroup.findViewById(R.id.checkbox);
            mCheckBox.setVisibility(View.GONE);
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int positionTag = (int) buttonView.getTag();
                    mLessons[positionTag].setCheck(buttonView.isChecked());
                    mStatableViewGroup.setStateChecked(buttonView.isChecked());
                }
            });

            StatableViewGroup.OnStateChange onStateChange = new StatableViewGroup.OnStateChange() {
                @Override
                public void onStateChanged(int state) {
                    if (mStatableViewGroup.isCheck()) mCheckBox.setChecked(true);
                    else mCheckBox.setChecked(false);
                }

                @Override
                public void onEnableChanged(boolean enable) {

                    if (!mStatableViewGroup.isEnable) {
                        mCheckBox.setVisibility(View.GONE);

                    } else {
                        mCheckBox.setVisibility(View.VISIBLE);
                    }
                }
            };
            mStatableViewGroup.setListener(onStateChange);

            mStatableViewGroup.setEnable(!isSingleMode);

            this.mLesson = (TextView) view.findViewById(R.id.txt_lesson);
            this.mLearnedText = (TextView) view.findViewById(R.id.txt_learn);
        }


        @Override
        public void onClick(View v) {
            mCurrentItemClicked = getAdapterPosition();

            if (isSingleMode) {
                mListLessonId.add(mLessons[getAdapterPosition()].mLesson.getmLessonId());
                createDialog();
            } else {
                mStatableViewGroup.onClick(v);
                if (mStatableViewGroup.isCheck()) mListLessonId.add(mLessons[getAdapterPosition()].mLesson.getmLessonId());
                else {
                    mListLessonId.remove(mLessons[getAdapterPosition()].mLesson.getmLessonId());
                }
            }

        }

    }
}
