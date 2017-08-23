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

    public static TaskReportEntry getFromJson(JSONObject json) throws JSONException {
        TaskReportEntry entry = new TaskReportEntry();
        entry.reportEntry = ReportEntry.getFromJson(json.getJSONObject("report"));
        entry.superEntry = SuperEntry.getFromJson(json.getJSONObject("super"));
        entry.leaderEntry = LeaderEntry.getFromJson(json.getJSONObject("leader"));
        return null;
    }
}
