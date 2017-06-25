package model;

/**
 * Created by dainguyen on 5/28/17.
 */

public class ModelPartFavorite {
    private int type;
    private int id;
    private String time;

    public ModelPartFavorite(int type, int id, String time) {
        this.type = type;
        this.id = id;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
