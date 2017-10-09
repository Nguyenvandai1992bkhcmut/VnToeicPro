package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by nvdai on 15/11/2016.
 */

public class Content implements Serializable {

    private String parent;
    private String vol;
    private  ArrayList<Meanings> meaningses;


    public Content(String parent, String vol, ArrayList<Meanings> meaningses) {
        this.parent = parent;
        this.vol = vol;
        this.meaningses = meaningses;
    }

    public ArrayList<Meanings> getMeaningses() {
        return meaningses;
    }

    public void setMeaningses(ArrayList<Meanings> meaningses) {
        this.meaningses = meaningses;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }
}
