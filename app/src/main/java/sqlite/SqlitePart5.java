package sqlite;



import model.ModelPart5;

/**
 * Created by dainguyen on 5/30/17.
 */

public class SqlitePart5 extends ManagerPart {

    static  {
        System.loadLibrary("jnicxc");
    }

    public native ModelPart5[]randomPart5Subject(int subject ,int number);

    public native ModelPart5[]randomPart5(int number);

    public native ModelPart5[]searchPart5Favorite();
}
