package com.gk.erp012.entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ke.gao on 2017/8/23.
 */

public class LeaderEntry {
    private String adviceId;
    private String time;
    private int adviceIndex;
    private String comment;

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

    public static LeaderEntry getFromJson(JSONObject json) throws JSONException {
        /**
         *     private String adviceId;
         private String time;
         private int adviceIndex;
         private String comment;
         */
        LeaderEntry entry = new LeaderEntry();
        entry.setAdviceId(json.getString("id"));
        entry.setTime(json.getString("time"));
        entry.setAdviceIndex(json.getInt("adviceIndex"));
        entry.setComment(json.getString("comment"));
        return entry;
    }
}
