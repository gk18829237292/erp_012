package com.gk.erp012.entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ke.gao on 2017/8/23.
 */

public class SuperEntry {
    private String adviceId;
    private String time;
    private int adviceIndex;
    private String comment;
    private List<String> picture=new ArrayList<>();
    private String taskId;
    private int type;
    private String name;
    private int star;

    public String getAdviceId() {
        return adviceId;
    }

    public void setAdviceId(String adviceId) {
        this.adviceId = adviceId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getAdviceIndex() {
        return adviceIndex;
    }

    public void setAdviceIndex(int adviceIndex) {
        this.adviceIndex = adviceIndex;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public static SuperEntry getFromJson(JSONObject json) throws JSONException {
/**
 *     private String adviceId;
 private String time;
 private int adviceIndex;
 private String comment;
 private List<String> picture;
 private String taskId;
 private int type;
 private String name;
 private int star;
 */
        SuperEntry entry = new SuperEntry();
        entry.setAdviceId(json.getString("id"));
        entry.setTime(json.getString("time"));
        entry.setAdviceIndex(json.getInt("adviceIndex"));
        entry.setComment(json.getString("comment"));
        JSONArray pics = json.getJSONArray("pics");
        for(int i =0;i<pics.length();i++){
            entry.getPicture().add(pics.getString(i));
        }
        entry.setTaskId(json.getString("taskId"));
        entry.setType(json.getInt("type"));
        entry.setStar(json.getInt("star"));
        entry.setName(json.getString("name"));
        return entry;
    }
}
