package model;

/**
 * Created by giang on 6/2/17.
 */

public enum  EAnswer {
    A("a"), B("b"), C("c"), D("d");

    private final String mAnswer;
    EAnswer(String s) {
        this.mAnswer = s;
    }


    public String getAnswer() {
        return this.mAnswer;
    }
}
