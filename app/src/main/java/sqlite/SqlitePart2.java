package sqlite;

import model.ModelPart1;
import model.ModelPart2;

/**
 * Created by dainguyen on 6/14/17.
 */

public class SqlitePart2 extends ManagerPart {

    static {
        System.loadLibrary("jniuuu");
    }

    public native ModelPart2 searchPart2Id(int id);

    public native ModelPart2[]randomPart2(int number);

    public native ModelPart2[]randomPart2Subject(int subject , int number);

    public native ModelPart2[]searchPart2Favorite();

    public native ModelPart2[]searchPart2Check();
}
