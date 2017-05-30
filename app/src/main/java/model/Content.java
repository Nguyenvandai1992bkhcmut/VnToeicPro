package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by nvdai on 15/11/2016.
 */

public class Content implements Serializable {

    public String parent;
    public String vol;
    public  ArrayList<Meanings> meaningses;


    public Content(String parent, String vol, ArrayList<Meanings> meaningses) {
        this.parent = parent;
        this.vol = vol;
        this.meaningses = meaningses;
    }

}
