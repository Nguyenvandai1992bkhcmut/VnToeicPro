package sqlite;

import model.ModelPartCheck;
import model.ModelPartFavorite;

/**
 * Created by dainguyen on 5/28/17.
 */

public  class ManagerPart {
    static {
        System.loadLibrary("jnixcc");
    }


    public native ModelPartFavorite[] searchAllFavoritePart(int part);

    public native ModelPartCheck [] searchAllCheckedPart(int part);

    public native boolean checkPartFavorite(int part, int id);

    public native  void insertPartFavorite(ModelPartFavorite model);

    public native void insertPartCheck(ModelPartCheck model);

    public native void deletePartFavorite(int part, int id);

    public native void deletePartCheck(int part, int id);

}
