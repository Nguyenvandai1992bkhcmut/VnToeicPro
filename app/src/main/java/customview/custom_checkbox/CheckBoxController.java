package customview.custom_checkbox;

import java.util.ArrayList;

/**
 * Created by giang on 6/28/17.
 */

public class CheckBoxController {

    private ArrayList<CustomCheckBox> mCheckBoxes = new ArrayList<>();
    private ArrayList<Boolean> states = new ArrayList<>();
    private int mCurrentEnable = -1;

    public void add(CustomCheckBox customCheckBox) {
        mCheckBoxes.add(customCheckBox);
        states.add(false);
    }

    public void itemOnChangeState(int id) {
        states.set(id, true);
        mCheckBoxes.get(id).setState(ECheckable.CHECKED);

        if (mCurrentEnable >= 0) {
            states.set(mCurrentEnable, false);
            mCheckBoxes.get(mCurrentEnable).setState(ECheckable.NOT_CHECKED);
        }

        mCurrentEnable = id;
    }
}
