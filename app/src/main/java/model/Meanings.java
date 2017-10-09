package model;

import java.util.ArrayList;

/**
 * Created by nvdai on 15/11/2016.
 */

public class Meanings {
    public String type ;
    public ArrayList<Example> Example;
    public ArrayList<Example>extend;
    public Meanings(String type, ArrayList<Example> example,ArrayList<Example>extend) {
        this.type = type;
        Example = example;
        this.extend = extend;
    }
}