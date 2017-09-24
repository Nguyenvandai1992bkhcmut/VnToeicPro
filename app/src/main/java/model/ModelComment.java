package model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import feeds.ActivityFeed;

/**
 * Created by dainguyen on 8/26/17.
 */

public class ModelComment implements Serializable{
    private int comment_id;
    private int user_id;
    private ArrayList<feeds.Content>contents;
    private boolean isApprove;
    private boolean isDisapprove;
    private long mtime;
    private int numberReplies;
    private int like;
    private int unlike;
    private ArrayList<Reply>arrReplies;
    private User user;
    private final static String LINKUSER ="http://vntoeic.xyz/api/v1/users/";
    private final static String LINKIMAGE="http://vntoeic.xyz/api/v1/posts/";
    private int postid ;

    public ModelComment(int postid,JSONObject ob){
        try {
            this.postid = postid;
            comment_id = ob.getInt("commentId");
            user_id = ob.getInt("userId");
            String content = ob.getString("content");
            isApprove= ob.getInt("approves")==1;
            isDisapprove = ob.getInt("disapproves")==1;
            mtime = ob.getLong("time");
            numberReplies = ob.getInt("numberOfReplies");
            like= ob.getInt("isApproved");
            unlike= ob.getInt("isDisapproved");
            JSONArray arr = ob.getJSONArray("replies");
            arrReplies = new ArrayList<>();
            for(int i =0;i<arr.length();i++){
                arrReplies.add(new Reply(arr.getJSONObject(i)));
            }
            setDataUser();
            contents = new ArrayList<>();
            parseContent(content);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setDataUser(){
        user = ActivityFeed.getUser(user_id);
    }
    public String getTime() {
        long epoch = System.currentTimeMillis();
        int second = (int) ((epoch-mtime)/1000);
        if(second<60)return "few second";
        else if(second<60*60) return String.valueOf(second/60) +"  minute";
        else return new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date (mtime));

    }

    public String getLinkApprove(){
        return LINKIMAGE+String.valueOf(postid)+"/comments/"+String.valueOf(comment_id)+"/approve";
    }

    public String getLinkDisApprove(){
        return LINKIMAGE+String.valueOf(postid)+"/comments/"+String.valueOf(comment_id)+"/disapprove";
    }

    public void parseContent(String c){
        int f= 0;
        String arr[] = c.split("<img>");
        for(int i=0;i<arr.length;i++){
            if(arr[i].startsWith("photo")) {

                String s = arr[i].split(" ")[1];
                if(f!=2){
                    contents.add(new feeds.Content(1,LINKIMAGE + String.valueOf(postid) + "/comments" +String.valueOf(comment_id)+"/images/" + s));
                }else {
                    contents.get(contents.size() - 1).addSource(LINKIMAGE + String.valueOf(postid) + "/comments" +String.valueOf(comment_id)+"/images/" + s);
                }
                f=2;
            }else if(arr[i].startsWith("fixedPhoto")){
                String s = arr[i].split(" ")[1];
                contents.add(new feeds.Content(2,LINKIMAGE + String.valueOf(postid) + "/comments" +String.valueOf(comment_id)+"/images/" + s));
            }else if (!arr[i].equals("\n")){


                if(f!=1) {
                    contents.add(new feeds.Content(0, arr[i]));
                }else{
                    contents.get(contents.size() - 1).addSource(arr[i]);
                }
                f=1;

            }
        }
    }

    public String getLinkApproves(){
        return LINKIMAGE+String.valueOf(postid)+"/comments/"+String.valueOf(comment_id)+"/approves";
    }

    public String getLinkDisApproves(){
        return LINKIMAGE+String.valueOf(postid)+"/comments/"+String.valueOf(comment_id)+"/disapproves";
    }


    public ArrayList<Reply> getArrReplies() {
        return arrReplies;
    }

    public void setArrReplies(ArrayList<Reply> arrReplies) {
        this.arrReplies = arrReplies;
    }

    public int getNumberReplies() {
        return numberReplies;
    }

    public void setNumberReplies(int numberReplies) {
        this.numberReplies = numberReplies;
    }

    public int getUnlike() {
        return unlike;
    }

    public void setUnlike(int unlike) {
        this.unlike = unlike;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public long getMtime() {
        return mtime;
    }

    public void setMtime(long mtime) {
        this.mtime = mtime;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public boolean isDisapprove() {
        return isDisapprove;
    }

    public void setDisapprove(boolean disapprove) {
        isDisapprove = disapprove;
    }

    public boolean isApprove() {
        return isApprove;
    }

    public void setApprove(boolean approve) {
        isApprove = approve;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<feeds.Content> getContents() {
        return contents;
    }

    public void setContents(ArrayList<feeds.Content> contents) {
        this.contents = contents;
    }

    public  class Reply implements Serializable{
        private int comment_id;
        private int user_id;
        private ArrayList<feeds.Content>arrcontent;
        private long mtime;
        private User user;

        public Reply(JSONObject ob) {
            try {
                this.comment_id = ob.getInt("commentId");
                this.user_id = ob.getInt("userId");
                String content = ob.getString("content");
                arrcontent = new ArrayList<>();
                parseContent(content);
                this.mtime = ob.getInt("time");
                setDataUser();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        public void setDataUser(){
            user = ActivityFeed.getUser(user_id);
        }

        public void parseContent(String c){
            int f= 0;
            String arr[] = c.split("<img>");
            for(int i=0;i<arr.length;i++){
                if(arr[i].startsWith("photo")) {

                    String s = arr[i].split(" ")[1];
                    if(f!=2){
                        arrcontent.add(new feeds.Content(1,LINKIMAGE + String.valueOf(comment_id) + "/images/" + s));
                    }else {
                        arrcontent.get(contents.size() - 1).addSource(LINKIMAGE + String.valueOf(comment_id) + "/images/" + s);
                    }
                    f=2;
                }else if(arr[i].startsWith("fixedPhoto")){
                    String s = arr[i].split(" ")[1];
                    arrcontent.add(new feeds.Content(2,LINKIMAGE + String.valueOf(comment_id) + "/images/" + s));
                }else if (!arr[i].equals("\n")){
                    if(f!=1) {
                        arrcontent.add(new feeds.Content(0, arr[i]));
                    }else{
                        arrcontent.get(arrcontent.size() - 1).addSource(arr[i]);
                    }
                    f=1;

                }
            }
        }

        public String getTime() {
            long epoch = System.currentTimeMillis();
            int second = (int) ((epoch-mtime)/1000);
            if(second<60)return "few second";
            else if(second<60*60) return String.valueOf(second/60) +"  minute";
            else return new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date (mtime));

        }

        public ArrayList<feeds.Content> getArrcontent() {
            return arrcontent;
        }

        public void setArrcontent(ArrayList<feeds.Content> arrcontent) {
            this.arrcontent = arrcontent;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public long getMtime() {
            return mtime;
        }

        public void setMtime(long mtime) {
            this.mtime = mtime;
        }

        public int getComment_id() {
            return comment_id;
        }

        public void setComment_id(int comment_id) {
            this.comment_id = comment_id;
        }
    }



}
