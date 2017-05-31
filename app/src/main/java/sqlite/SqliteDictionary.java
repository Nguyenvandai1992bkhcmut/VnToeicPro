package sqlite;

import model.Dictionary;
import model.DictionaryFavorite;

/**
 * Created by dainguyen on 5/21/17.
 */

public class SqliteDictionary {

    static {
        System.loadLibrary("jnixyz");
    }
    public native Dictionary[] searchSimilar(String name);

    public native Dictionary searchId(int id);

    public native DictionaryFavorite[] searchFavorite();

    public native void insertFavorite(DictionaryFavorite dictionaryFavorite);
}
