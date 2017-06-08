package sqlite;

import model.*;

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

    public native ModelWord[]searchWordPart(int part, int id);

    /*
    is_aware = 0 -> chua thuoc word
    is_aware =1  -> da thuoc tu
    part =[1-7] <=> part1 -> part7

    tim kiem nhung tu trong part filter part isware , id,
    */

    public native ModelWord[]searchWordPartAware(int part ,int is_aware, int id);

    public native ModelPartSubject[]searchPartSubject(int part);

    public native ModelGrammar[] searchAllGrammar();

    public native ModelGrammar searchGrammarId(int idGrammar);

    /*
    Update colum aware trong word
    */

    public native void updateWordAware(int idword, int aware);

    public native ModelPartResult[] searhPartSubjectResult(int part);


    public native boolean updateTimePart(int part, int id);
}
