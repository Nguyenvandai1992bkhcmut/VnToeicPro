package model;

import java.io.Serializable;

/**
 * Created by dainguyen on 9/7/17.
 */

public class PostTag implements Serializable {
    private int id_tag;
    private String tag_title;
    private int is_used;

    public PostTag(int id_tag, String tag_title, int is_used) {
        this.id_tag = id_tag;
        this.tag_title = tag_title;
        this.is_used = is_used;
    }

    public int getId_tag() {
        return id_tag;
    }

    public void setId_tag(int id_tag) {
        this.id_tag = id_tag;
    }

    public int getIs_used() {
        return is_used;
    }

    public void setIs_used(int is_used) {
        this.is_used = is_used;
    }

    public String getTag_title() {
        return tag_title;
    }

    public void setTag_title(String tag_title) {
        this.tag_title = tag_title;
    }
}
