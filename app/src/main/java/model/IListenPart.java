package model;

/**
 * Created by dainguyen on 7/11/17.
 */

public interface IListenPart {

    public String getQuestion(int index);

    public int getCountQuestion();

    public String getA(int index);

    public String getB(int index);

    public String getC(int index);

    public String getD(int index);

    public String getSol(int index);

    public int getId();

    public String getSrcFile();

    public String getLinkDowload();

    public String getLinkDowloadImage();

    public String getLinkFigure(int numberQuestion);

    public String getSrcFileImage();

    public int getCountAnswer();


    public String getToken();

}
