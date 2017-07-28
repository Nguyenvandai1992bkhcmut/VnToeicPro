package model;

/**
 * Created by dainguyen on 5/25/17.
 */

public class DictionaryFavorite {
    int id ;
    String time;
    String meaning;

    public DictionaryFavorite(int id, String time, String meaning) {
        this.id = id;
        this.time = time;
        this.meaning = meaning;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
