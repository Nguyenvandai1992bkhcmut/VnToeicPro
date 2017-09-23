package sqlite;

import model.ModelPart7;
import model.ModelTokenPart7;

/**
 * Created by dainguyen on 6/16/17.
 */

public class SqlitePart7 extends ManagerPart {

    static {
        System.loadLibrary("jnifff");
    }

    public native ModelPart7 searchPart7Id(int id);

    public native ModelPart7[]randomPart7(int number);

    public native ModelTokenPart7[]searchAllImagePart7();

    public native ModelPart7[]randomPart7CountQuestion(int number , int countQuestion);

    public native ModelPart7[]randomPart7Subject(int subject , int number);

    public native ModelPart7[]searchPart7Favorite();

    public native ModelPart7[]searchAllPartImage();

    public native ModelPart7[]searchPart7Check();
}
