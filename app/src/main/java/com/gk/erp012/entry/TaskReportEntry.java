package com.gk.erp012.entry;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ke.gao on 2017/8/23.
 */

public class TaskReportEntry {
    //TODO 解析json
    ReportEntry reportEntry;
    SuperEntry superEntry;
    LeaderEntry leaderEntry;

    public TaskReportEntry() {
        reportEntry = null;
        superEntry = null;
        leaderEntry = null;
    }

    public ReportEntry getReportEntry() {
        return reportEntry;
    }

    public void setReportEntry(ReportEntry reportEntry) {
        this.reportEntry = reportEntry;
    }

    public SuperEntry getSuperEntry() {
        return superEntry;
    }

    public void setSuperEntry(SuperEntry superEntry) {
        this.superEntry = superEntry;
    }

    public LeaderEntry getLeaderEntry() {
        return leaderEntry;
    }

    public void setLeaderEntry(LeaderEntry leaderEntry) {
        this.leaderEntry = leaderEntry;
    }

    @Override
    public String toString() {
        return "TaskReportEntry{" +
                "reportEntry=" + reportEntry +
                ", superEntry=" + superEntry +
                ", leaderEntry=" + leaderEntry +
                '}';
    }

    public static TaskReportEntry getFromJson(JSONObject json) throws JSONException {
        TaskReportEntry entry = new TaskReportEntry();
        if(json.has("report")){
            entry.reportEntry = ReportEntry.getFromJson(json.getJSONObject("report"));
        }
        if(json.has("super")){
            entry.superEntry = SuperEntry.getFromJson(json.getJSONObject("super"));
        }
        if(json.has("leader")) {
            entry.leaderEntry = LeaderEntry.getFromJson(json.getJSONObject("leader"));
        }
        return entry;
    }
}
