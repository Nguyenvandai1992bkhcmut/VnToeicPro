package model;

/**
 * Created by dainguyen on 8/9/17.
 */

public class ModelTokenPart7 {
    private int part7_id;
    private int passage_id;
    private String token;

    public ModelTokenPart7(int part7_id, int passage_id, String token) {
        this.part7_id = part7_id;
        this.passage_id = passage_id;
        this.token = token;
    }

    public int getPart7_id() {
        return part7_id;
    }

    public void setPart7_id(int part7_id) {
        this.part7_id = part7_id;
    }

    public int getPassage_id() {
        return passage_id;
    }

    public void setPassage_id(int passage_id) {
        this.passage_id = passage_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNameFile(){
        return String.valueOf(part7_id)+String.valueOf(passage_id)+token+".png";
    }
}
