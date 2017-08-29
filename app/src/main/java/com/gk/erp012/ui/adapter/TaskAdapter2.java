package com.gk.erp012.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.gk.erp012.R;
import com.gk.erp012.entry.TaskEntry;
import com.gk.erp012.utils.TimeUtils;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ke.gao on 2017/8/30.
 */

public class TaskAdapter2 extends BaseSwipeAdapter {

    private Context mContext;
    private List<TaskEntry> taskEntries;
    TaskSwipe taskSwipe;

    public TaskAdapter2(Context mContext, List<TaskEntry> taskEntries,TaskSwipe taskSwipe) {
        this.mContext = mContext;
        this.taskEntries = taskEntries;
        this.taskSwipe = taskSwipe;
    }


    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_task, null);
        final SwipeLayout swipeLayout = (SwipeLayout)view.findViewById(getSwipeLayoutResourceId(position));
        view.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SweetAlertDialog dialog = new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("删除")
                        .setContentText("删除操作不可逆。")
                        .setConfirmText("确认删除")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                swipeLayout.close();
                                sDialog.dismiss();
                                taskSwipe.delete(taskEntries.get(position).getTaskId());
//                                pDialog.show();
                            }
                        });
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });
        view.findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeLayout.close();
                taskSwipe.update(position);
            }
        });
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.tv_name = view.findViewById(R.id.tv_name);
        viewHolder.tv_goal = view.findViewById(R.id.tv_goal);
        viewHolder.tv_status = view.findViewById(R.id.tv_status);
        viewHolder.tv_time = view.findViewById(R.id.tv_time);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void fillValues(int position, View convertView) {
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        TaskEntry entry = taskEntries.get(position);
        viewHolder.tv_name.setText(entry.getTaskName());
        viewHolder.tv_goal.setText(entry.getGoal());
        if(entry.getAtTime()){
            viewHolder.tv_status.setText("已按期限");
            viewHolder.tv_status.setTextColor(mContext.getResources().getColor(R.color.colorBlue));
        }else {
            viewHolder.tv_status.setText("未按期限");
            viewHolder.tv_status.setTextColor(mContext.getResources().getColor(R.color.colorRed));
        }
        viewHolder.tv_time.setText("更新时间：" + TimeUtils.convert2String(entry.getUpdateTime()));
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

    class ViewHolder{
        TextView tv_name,tv_goal,tv_status,tv_time;
    }

    public interface TaskSwipe {
        void delete(String taskId);
        void update(int position);
    }
}
