package feeds;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dainguyen on 9/4/17.
 */

public class Content implements Serializable {
    private int type;
    private ArrayList<String> source;

    // type =0 image;
    // type=1 fixedimage;
    //type=2 normal
    //type=3 hidden;
    //type=4 quesiton

    public Content(int type) {
        this.type = type;
        source=  new ArrayList<>();
    }
    public Content(int type,String src) {
        this.type = type;
        source=  new ArrayList<>();
        source.add(src);
    }

    public ArrayList<String> getSource() {
        return source;
    }

    public void setSource(ArrayList<String> source) {
        this.source = source;
    }

    public void addSource(String src){
        source.add(src);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
