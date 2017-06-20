package com.vntoeic.bkteam.vntoeicpro.CustomComponent.CustomCheckBox;

/**
 * Created by giang on 6/3/17.
 */

public interface Checkable {
    void setCheck(ECheckable eCheckable);
    ECheckable getCheck();
    void toggle();
    void setShow(boolean isShow);
}
