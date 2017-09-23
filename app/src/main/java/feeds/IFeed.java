package feeds;

import android.os.Bundle;

import java.util.ArrayList;

import model.ModelFeed;

/**
 * Created by dainguyen on 8/28/17.
 */

public interface IFeed {

    public void showDetailFeed(ModelFeed modelFeed);

    public void showImageFull(ArrayList<String>src);

    public void deleteComment();
    public void editComment(Bundle bundle);
}
