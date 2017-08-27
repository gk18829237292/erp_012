package com.gk.erp012.entry;

import com.gk.erp012.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ke.gao on 2017/8/23.
 */

public class ReportEntry{
    String reportId;
    String taskId;
    String comment;
    List<String> picture = new ArrayList<>();
    String reportTime;
    int reportIndex;

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public int getReportIndex() {
        return reportIndex;
    }

    public void setReportIndex(int reportIndex) {
        this.reportIndex = reportIndex;
    }

    @Override
    public String toString() {
        return "ReportEntry{" +
                "reportId='" + reportId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", comment='" + comment + '\'' +
                ", picture=" + picture +
                ", reportTime='" + reportTime + '\'' +
                ", reportIndex=" + reportIndex +
                '}';
    }

    public static ReportEntry getFromJson(JSONObject json) throws JSONException {
        /**
         *    String reportId;
         String taskId;
         String comment;
         List<String> picture;
         String reportTime;
         int reportIndex;
         */
        ReportEntry entry = new ReportEntry();
//        entry.setReportId(json.getString("id"));
        entry.setReportIndex(json.getInt("reportIndex"));
        entry.setComment(json.getString("comment"));
        entry.setReportTime(json.getString("time"));
        JSONArray pics = json.getJSONArray("pics");
        for(int i =0;i<pics.length();i++){
            entry.getPicture().add(Constants.IMG_DOMAIN+pics.getString(i));
        }
        entry.setTaskId(json.getString("taskId"));
        return entry;
    }
}
