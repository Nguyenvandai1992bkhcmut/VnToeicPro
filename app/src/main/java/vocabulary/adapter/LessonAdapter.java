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

import customview.custom_checkbox.CheckBoxController;
import customview.custom_checkbox.CustomCheckBox;
import customview.custom_checkbox.ECheckable;
import customview.statable_viewgroup.StatableViewGroup;
import model.ModelLesson;
import model.ModelStatableLesson;
import vocabulary.activity.VocabularyActivity;

/**
 * Created by giang on 6/27/17.
 */

public class LessonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final ModelStatableLesson[] mLessons;
    private final Context mContext;
    private CheckBoxController mCheckBoxController;

    public LessonAdapter(Context context, ModelStatableLesson[] lessons) {
        this.mContext = context;
        this.mLessons = lessons;
        this.mCheckBoxController = new CheckBoxController();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_lesson_viewholder, parent, false);
        return new LessonItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        LessonItemViewHolder viewHolder = (LessonItemViewHolder) holder;
        viewHolder.mLesson.setText(mLessons[position].mLesson.getmLessonTitle());
        viewHolder.mCheckBox.setTag(position);

        viewHolder.mCheckBox.setChecked(mLessons[position].isChecked());
    }

    @Override
    public int getItemCount() {
        return mLessons.length;
    }

    private class LessonItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mLesson, mLearnedText;
        private StatableViewGroup mStatableViewGroup;
        private CheckBox mCheckBox;

        public LessonItemViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mStatableViewGroup = (StatableViewGroup) view.findViewById(R.id.statableViewGroup);
//            mStatableViewGroup.setEnable(true);
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



            this.mLesson = (TextView) view.findViewById(R.id.txt_lesson);
            this.mLearnedText = (TextView) view.findViewById(R.id.txt_learn);
        }

        @Override
        public void onClick(View v) {
//            if (!mStatableViewGroup.isEnable) mStatableViewGroup.setEnable(true);
//            mStatableViewGroup.onClick(mStatableViewGroup);

            ((VocabularyActivity)mContext).onOpenLearningActivity(mLessons[getAdapterPosition()].mLesson.getmLessonId());
        }
    }
}
