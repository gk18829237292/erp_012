package com.gk.erp012.entry;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by ke.gao on 2017/8/3.
 */

public class TaskEntry implements Serializable{

    private String taskId;
    private String taskName;
    private String startTime;
    private String endTime;
    private String updateTime;
    private String chairMan;
    private String place;
    private String financing;
    private String goal;
    private String reportType; // -- 0日报 1 周报，2半月报，3月报，4季报，5 半年报，6年报--
    private String departClassId;
    private Boolean isAtTime;

    public Boolean getAtTime() {
        return isAtTime;
    }

    public void setAtTime(Boolean atTime) {
        isAtTime = atTime;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getChairMan() {
        return chairMan;
    }

    public void setChairMan(String chairMan) {
        this.chairMan = chairMan;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getFinancing() {
        return financing;
    }

    public void setFinancing(String financing) {
        this.financing = financing;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getDepartClassId() {
        return departClassId;
    }

    public void setDepartClassId(String departClassId) {
        this.departClassId = departClassId;
    }

    // TODO: 2017/8/3
    public static TaskEntry getFormJson(JSONObject json) throws JSONException {
       TaskEntry entry = new TaskEntry();
        entry.setTaskId(json.getString("id"));
        entry.setTaskName(json.getString("name"));
        entry.setStartTime(json.getString("startTime"));
        entry.setEndTime(json.getString("endTime"));
        entry.setUpdateTime(json.getString("updateTime"));
        entry.setChairMan(json.getString("chairMan"));
        entry.setPlace(json.getString("place"));
        entry.setFinancing(json.getString("financing"));
        entry.setGoal(json.getString("goal"));
        entry.setReportType(json.getString("reportType"));
        entry.setDepartClassId(json.getString("departClassId"));
        entry.setAtTime(json.getBoolean("isAtTime"));
        return entry;
    }
}
