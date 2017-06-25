package model;

import java.util.ArrayList;

/**
 * Created by nvdai on 15/11/2016.
 */

public class Example {
    public int type;
    public String typeword ;
    public String mean;
    public ArrayList<String> exam;

    public Example(int type,String typeword,String mean,ArrayList<String> exam) {
        this.type  = type;
        this.mean = mean;;
        this.exam = exam;
        this.typeword = typeword;
    }

    @Override
    public String toString(){
        String a ="";
        for (String s:
             exam) {
            a +="---"+s;
        };
        return  "mean--" + mean + a;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }
}