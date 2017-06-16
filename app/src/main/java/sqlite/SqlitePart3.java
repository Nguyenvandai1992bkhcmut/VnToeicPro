package sqlite;

import model.ModelPart3;

/**
 * Created by dainguyen on 6/15/17.
 */

public class SqlitePart3 extends ManagerPart {
    static {
        System.loadLibrary("jnippp");
    }

    public native ModelPart3 searchPart3Id(int id);

    public native ModelPart3[]randomPart3(int number);

    public native ModelPart3[]randomPart3Subject(int subject , int number);

    public native ModelPart3[]searchPart3Favorite();

}
