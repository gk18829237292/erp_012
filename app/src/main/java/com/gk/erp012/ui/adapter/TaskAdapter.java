package com.gk.erp012.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gk.erp012.R;
import com.gk.erp012.entry.TaskEntry;

import org.w3c.dom.Text;

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
        ViewHolder viewHolder;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_task,viewGroup,false);
            viewHolder = new ViewHolder();
            viewHolder.tv_name = view.findViewById(R.id.tv_name);
            viewHolder.tv_goal = view.findViewById(R.id.tv_goal);
            viewHolder.tv_status = view.findViewById(R.id.tv_status);
            viewHolder.tv_time = view.findViewById(R.id.tv_time);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        TaskEntry entry = taskEntries.get(i);
        viewHolder.tv_name.setText(entry.getTaskName());
        viewHolder.tv_goal.setText(entry.getGoal());
//        viewHolder.tv_status.setText(entry.get);
        viewHolder.tv_goal.setText(entry.getUpdateTime());

        return view;
    }

    class ViewHolder{
        TextView tv_name,tv_goal,tv_status,tv_time;
    }
}
