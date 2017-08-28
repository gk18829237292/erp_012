package com.gk.erp012.entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ke.gao on 2017/8/3.
 */

public class DepartTypeEntry implements Serializable{
    private String name;
    private String id;
    private List<DepartEntry> departs = new ArrayList<>();

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

    public List<DepartEntry> getDeparts() {
        return departs;
    }


    public static DepartTypeEntry getFormJson(JSONObject json) throws JSONException {
        DepartTypeEntry departType = new DepartTypeEntry();
        departType.setName(json.getString("name"));
        departType.setId(json.getString("id"));
        JSONArray departs = json.getJSONArray("departs");
        for(int i =0;i<departs.length();i++){
            departType.getDeparts().add(DepartEntry.getFormJson(departs.getJSONObject(i)));
        }
        return departType;
    }

    public List<String> getDepartNameList(){
        List<String > list = new ArrayList<>();
        for(DepartEntry entry:departs){
            list.add(entry.getName());
        }
        return list;
    }

    public List<TaskEntry> getAllTaskEntry(int maxNum){
        List<TaskEntry> taskEntries = new ArrayList<>();
        for(DepartEntry entry: departs){
            taskEntries.addAll(entry.getTasks());
            if(taskEntries.size() >= maxNum){
                break;
            }
        }
        while (taskEntries.size()>maxNum){
            taskEntries.remove(taskEntries.size()-1);
        }
        return taskEntries;

    }

}
