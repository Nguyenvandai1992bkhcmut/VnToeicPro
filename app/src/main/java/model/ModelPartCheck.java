package model;

/**
 * Created by dainguyen on 5/28/17.
 */

public class ModelPartCheck {
    private int type;
    private int id;
    private String time;
    private int result;

    public ModelPartCheck(int type, int id, String time, int result) {
        this.type = type;
        this.id = id;
        this.time = time;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
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
