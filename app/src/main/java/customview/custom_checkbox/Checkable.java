package customview.custom_checkbox;

/**
 * Created by giang on 6/28/17.
 */

public interface Checkable {
    void setCheck(ECheckable eCheckable);
    ECheckable getCheck();
    void toggle();
    void setShow(boolean isShow);
}
