package supportview;

/**
 * Created by dainguyen on 7/10/17.
 */

public interface IPartControl {
    public void removeFragmentControl();
    public void changeQuestion(int passage,int index);
    public void changePart(int part);
    public void notfySubmit();
}

