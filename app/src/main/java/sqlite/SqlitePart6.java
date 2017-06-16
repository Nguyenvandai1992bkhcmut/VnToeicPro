package sqlite;

import model.ModelPart6;

/**
 * Created by dainguyen on 6/16/17.
 */

public class SqlitePart6 extends ManagerPart {

    static {
        System.loadLibrary("jnimmm");
    }

    public native ModelPart6 searchPart6Id(int id);

    public native ModelPart6[]randomPart6(int number);

    public native ModelPart6[]randomPart6Subject(int subject , int number);

    public native ModelPart6[]searchPart6Favorite();
}
