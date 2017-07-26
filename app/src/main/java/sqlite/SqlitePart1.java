package sqlite;

import android.graphics.PorterDuff;

import model.ModelPart1;
import model.ModelPartFavorite;

/**
 * Created by dainguyen on 5/27/17.
 */

public class SqlitePart1 extends ManagerPart {

    static {
        System.loadLibrary("jnizcc");
    }

    public native ModelPart1 searchPart1Id(int id);

    public native ModelPart1[]randomPart1(int number);

    public native ModelPart1[]randomPart1Subject(int subject ,int number);

    public native ModelPart1[]searchPart1Favorite();
//
   // chua viet
    public native ModelPart1[]searchPart1Check();


}
