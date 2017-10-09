package model;

/**
 * Created by dainguyen on 5/28/17.
 */

public class ModelPartSubject {
    private int type;
    private int id;
    private String title;

    public ModelPartSubject(int type,int id, String title) {
        this.id = id;
        this.title = title;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
