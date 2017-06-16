package sqlite;

import model.ModelPart4;

/**
 * Created by dainguyen on 6/16/17.
 */

public class SqlitePart4 extends ManagerPart {

    static {
        System.loadLibrary("jnihhh");
    }

    public native ModelPart4 searchPart4Id(int id);

    public native ModelPart4[]randomPart4(int number);

    public native ModelPart4[]randomPart4Subject(int subject , int number);

    public native ModelPart4[]searchPart4Favorite();
}
