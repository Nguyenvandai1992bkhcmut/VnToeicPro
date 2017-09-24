package model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vntoeic.bkteam.vntoeicpro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import feeds.*;
import supportview.ConvertTagView;

/**
 * Created by dainguyen on 8/19/17.
 */

public class ModelFeed implements Serializable{

    private int postId;
    private int userId;
    private String title;
    private ArrayList<feeds.Content>contents;
    private ArrayList<feeds.Question>questions;
    private boolean isAudio;
    private int approves;
    private int disapproves;
    private int subcribes;
    private String time;
    private long mtime;
    private boolean isSub;
    private boolean isApp;
    private boolean isDisapp;
    private ArrayList<Integer>tagIds;
    private ArrayList<String>tagTitle;
    private int countComment;
    private User user;

    private final static String LINKIMAGE="http://vntoeic.xyz/api/v1/posts/";

    public ModelFeed(JSONObject ob) throws JSONException {
        this.postId = ob.getInt("postId");
        this.userId = ob.getInt("userId");
        this.title = ob.getString("title") +"---"+ String.valueOf(postId);
        this.approves = ob.getInt("approves");
        this.disapproves= ob.getInt("disapproves");
        this.subcribes = ob.getInt("subscribes");
        this.isSub = (ob.getInt("isSubscribed")==1);
        this.isApp = (ob.getInt("isApproved")==1);
        this.isDisapp =(ob.getInt("isDisapproved")==1);
        this.countComment = ob.getInt("commentCount");
        this.isAudio = ob.getInt("containsAudio")==1;
        JSONArray arr = ob.getJSONArray("tags");
        tagIds = new ArrayList<>();
        tagTitle = new ArrayList<>();
        for (int i =0;i<arr.length();i++) {
            JSONObject o = arr.getJSONObject(i);
            tagIds.add(o.getInt("tagId"));
            tagTitle.add(o.getString("title"));
        }
        contents= new ArrayList<>();
        String contentAll = ob.getString("content");
        parseContent(contentAll);
        this.mtime = ob.getLong("time");
        setDataUser();

        questions=  new ArrayList<>();
        parseQuestion(ob.getJSONArray("questions"));
    }


    public String getLinkSubcribe(){
        return LINKIMAGE+String.valueOf(postId)+"/subscribe";
    }

    public String getLinkApprove(){
        return LINKIMAGE+String.valueOf(postId)+"/approve";
    }

    public String getLinkDisApprove(){
        return LINKIMAGE+String.valueOf(postId)+"/disapprove";
    }

    public ArrayList<String>getArraySrcImage(){
        ArrayList<String>src= new ArrayList<>();
        for(int i =0;i<contents.size();i++){
            if(contents.get(i).getType()==0) {
                for(int j=0;j<contents.get(i).getSource().size();j++){
                    src.add(contents.get(i).getSource().get(j));
                }
            }
        }
        return src;
    }

    public void parseQuestion(JSONArray arr){
        for(int i=0;i<arr.length();i++){
            try {
                questions.add(new Question(arr.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void parseContent(String c){
        int f= 0;
        String arr[] = c.split("<img>");
        for(int i=0;i<arr.length;i++){
            if(arr[i].startsWith("photo")) {

                String s = arr[i].split(" ")[1];
                if(f!=2){
                    contents.add(new feeds.Content(0,LINKIMAGE + String.valueOf(postId) + "/images/" + s));
                }else {
                    contents.get(contents.size() - 1).addSource(LINKIMAGE + String.valueOf(postId) + "/images/" + s);
                }
                f=2;
            }else if(arr[i].startsWith("fixedPhoto")){
                String s = arr[i].split(" ")[1];
                contents.add(new feeds.Content(1,LINKIMAGE + String.valueOf(postId) + "/images/" + s));
            } else if (!arr[i].equals("\n")){
                if(arr[i].length()!=0 && !arr[i].equals("\n")){
                    parseQuestion(arr[i]);
                    f=1;
                }
            }
        }
    }

    public static boolean isParsable(String input){
        boolean parsable = true;
        try{
            Integer.parseInt(input);
        }catch(NumberFormatException e){
            parsable = false;
        }
        return parsable;
    }

    public Question getQuestionId(String id){
        for(int i=0;i<questions.size();i++){
            int id_ = Integer.valueOf(id);
            if(questions.get(i).getQuestion_id()==id_){
                return questions.get(i);
            }
        }
        return null;
    }

    public void  parseQuestion(String root){
        if(root.contains("<question>")){
            String[]arr = root.split("<question>");
            for(int i=0;i<arr.length;i++){
                if(arr[i].length()>0) {
                    if (isParsable(arr[i])) {
                        contents.add(new feeds.Content(4, arr[i]));
                    } else {
                        if(!arr[i].equals("\n")) {
                            contents.add(new feeds.Content(2, arr[i]));
                        }
                    }
                }
            }
        }else{
            contents.add(new feeds.Content(2,root));
        }
    }

    public void setDataUser(){
        user = ActivityFeed.getUser(userId);
    }

    public int getApproves() {
        return approves;
    }

    public void setApproves(int approves) {
        this.approves = approves;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        long epoch = System.currentTimeMillis();
        int second = (int) ((epoch-mtime)/1000);
        if(second<60)return "few second";
        else if(second<60*60) return String.valueOf(second/60) +"  minute";
        else return new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date (mtime));

    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<String> getTagTitle() {
        return tagTitle;
    }

    public void setTagTitle(ArrayList<String> tagTitle) {
        this.tagTitle = tagTitle;
    }

    public ArrayList<Integer> getTagIds() {
        return tagIds;
    }

    public void setTagIds(ArrayList<Integer> tagIds) {
        this.tagIds = tagIds;
    }

    public int getSubcribes() {
        return subcribes;
    }

    public void setSubcribes(int subcribes) {
        this.subcribes = subcribes;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public boolean isSub() {
        return isSub;
    }

    public void setSub(boolean sub) {
        isSub = sub;
    }

    public boolean isDisapp() {
        return isDisapp;
    }

    public void setDisapp(boolean disapp) {
        isDisapp = disapp;
    }

    public boolean isApp() {
        return isApp;
    }

    public void setApp(boolean app) {
        isApp = app;
    }

    public int getDisapproves() {
        return disapproves;
    }

    public void setDisapproves(int disapproves) {
        this.disapproves = disapproves;
    }

    public int getCountComment() {
        return countComment;
    }

    public void setCountComment(int countComment) {
        this.countComment = countComment;
    }

    public ArrayList<feeds.Content> getContents() {
        return contents;
    }

    public void setContents(ArrayList<feeds.Content> contents) {
        this.contents = contents;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public boolean isAudio() {
        return isAudio;
    }

    public void setAudio(boolean audio) {
        isAudio = audio;
    }


}
