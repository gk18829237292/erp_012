package com.gk.erp012.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.gk.erp012.R;
import com.gk.erp012.entry.TaskEntry;
import com.gk.erp012.utils.TimeUtils;

import java.util.Map;

public class TaskDetailsActivity extends BaseActivity {

    private TaskEntry entry;
    private TextView tv_name,tv_goal,tv_complete,tv_allNum,tv_chairMan,tv_financing,tv_place,tv_startTime,tv_endTime;
    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_task_details);
        entry = (TaskEntry) getIntent().getBundleExtra("task").get("task");
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_goal = (TextView) findViewById(R.id.tv_goal);
        tv_complete = (TextView) findViewById(R.id.tv_complete);
        tv_allNum = (TextView) findViewById(R.id.tv_allNum);
        tv_chairMan = (TextView) findViewById(R.id.tv_chairMan);
        tv_financing = (TextView) findViewById(R.id.tv_financing);
        tv_place = (TextView) findViewById(R.id.tv_place);
        tv_startTime = (TextView) findViewById(R.id.tv_startTime);
        tv_endTime = (TextView) findViewById(R.id.tv_endTime);

        tv_name.setText(entry.getTaskName());
        tv_goal.setText(entry.getGoal());
//        tv_complete.setText("");
//        tv_allNum.setText(");
        tv_chairMan.setText(entry.getChairMan());
        tv_financing.setText(entry.getFinancing());
        tv_place.setText(entry.getPlace());
        tv_startTime.setText(TimeUtils.convert2String(entry.getStartTime()));
        tv_endTime.setText(TimeUtils.convert2String(entry.getEndTime()));
    }

    @Override
    public void initData() {

    }
}
