package model;

import java.io.Serializable;

/**
 * Created by dainguyen on 9/4/17.
 */

public class User implements  Serializable{
    private int use_id;
    private String name;
    private int like;
    private int unlike;
    private String linkAvatar;

    public User(int use_id,String name, String linkAvatar, int like, int unlike) {
        this.use_id = use_id;
        this.name = name;
        this.linkAvatar = linkAvatar;
        this.like = like;
        this.unlike = unlike;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getLinkAvatar() {
        return linkAvatar;
    }

    public void setLinkAvatar(String linkAvatar) {
        this.linkAvatar = linkAvatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnlike() {
        return unlike;
    }

    public void setUnlike(int unlike) {
        this.unlike = unlike;
    }

    public int getUse_id() {
        return use_id;
    }

    public void setUse_id(int use_id) {
        this.use_id = use_id;
    }
}
