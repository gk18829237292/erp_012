package com.gk.erp012.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gk.erp012.entry.TaskEntry;

import java.util.List;

/**
 * Created by ke.gao on 2017/8/21.
 */

public class TaskAdapter extends BaseAdapter {

    private Context mContext;
    private List<TaskEntry> taskEntries;

    public TaskAdapter(Context mContext, List<TaskEntry> taskEntries) {
        this.mContext = mContext;
        this.taskEntries = taskEntries;
    }

    @Override
    public int getCount() {
        return taskEntries.size();
    }

    @Override
    public Object getItem(int i) {
        return taskEntries.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
