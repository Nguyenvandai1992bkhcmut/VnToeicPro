package sqlite;

import model.ModelPart7;
<<<<<<< HEAD
=======
import model.ModelTokenPart7;
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce

/**
 * Created by dainguyen on 6/16/17.
 */

public class SqlitePart7 extends ManagerPart {

    static {
        System.loadLibrary("jnifff");
    }

    public native ModelPart7 searchPart7Id(int id);

    public native ModelPart7[]randomPart7(int number);

<<<<<<< HEAD
    public native String[]searchAllImagePart7();
=======
    public native ModelTokenPart7[]searchAllImagePart7();
>>>>>>> bf1972332b0100bf96a643964d543bf1f6f7fbce

    public native ModelPart7[]randomPart7CountQuestion(int number , int countQuestion);

    public native ModelPart7[]randomPart7Subject(int subject , int number);

    public native ModelPart7[]searchPart7Favorite();

    public native ModelPart7[]searchAllPartImage();

    public native ModelPart7[]searchPart7Check();
}
