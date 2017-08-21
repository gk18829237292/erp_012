package com.gk.erp012.entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ke.gao on 2017/8/3.
 */

public class DepartEntry {
    private String name;
    private String id;
    private List<TaskEntry> tasks = new ArrayList<>();

    public DepartEntry() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<TaskEntry> getTasks() {
        return tasks;
    }

    public void addTasks(TaskEntry entry){
        tasks.add(entry);
    }

    public static DepartEntry getFormJson(JSONObject json) throws JSONException {
        DepartEntry depart = new DepartEntry();
        depart.name = json.getString("name");
        depart.setId(json.getString("id"));
        JSONArray tasks = json.getJSONArray("tasks");
        for(int i =0;i<tasks.length();i++){
            depart.getTasks().add(TaskEntry.getFormJson(tasks.getJSONObject(i)));
        }
        return depart;
    }
}
