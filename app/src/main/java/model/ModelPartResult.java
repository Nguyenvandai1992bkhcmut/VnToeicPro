package model;

/**
 * Created by dainguyen on 6/1/17.
 */

public class ModelPartResult {

    private int type ;
    private int idsubject;
    private String title;
    private int correct ;
    private int count;

    public ModelPartResult(int type, int idsubject, String title, int correct, int count) {
        this.type = type;
        this.idsubject = idsubject;
        this.title = title;
        this.correct = correct;
        this.count = count;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getIdsubject() {
        return idsubject;
    }

    public void setIdsubject(int idsubject) {
        this.idsubject = idsubject;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
