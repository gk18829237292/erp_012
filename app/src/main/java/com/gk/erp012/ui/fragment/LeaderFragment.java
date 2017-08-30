package com.gk.erp012.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.gk.erp012.ErpApplication;
import com.gk.erp012.R;
import com.gk.erp012.entry.LeaderEntry;
import com.gk.erp012.entry.TaskReportEntry;
import com.gk.erp012.ui.CreateLeaderActivity;
import com.gk.erp012.ui.CreateSuperActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderFragment extends BaseFragment {

    private EditText tv_leader;
    LeaderEntry leaderEntry;
    private boolean updateFlag;
    private int index,taskId;
    public static final int CREATE_LEADER = 338;
    public LeaderFragment() {
        // Required empty public constructor
    }

    @Override
    public void update(TaskReportEntry taskReportEntry) {
        leaderEntry = taskReportEntry.getLeaderEntry();
        updateFlag = true;
        try {
            if (leaderEntry != null) {
                tv_leader.setText(leaderEntry.getComment());
            } else {
                tv_leader.setText("");
            }
        }catch (Exception e){

        }
    }

    @Override
    public void update(TaskReportEntry taskReportEntry, int index,int taskId) {
        update(taskReportEntry);
        this.index = index;
        this.taskId = taskId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leader, container, false);
        tv_leader = view.findViewById(R.id.tv_leader);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        //判断是否该显示
        if(!ErpApplication.getInstance().getUserEntry().isLeader()){
            fab.setVisibility(View.GONE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateLeaderActivity.class);
//                ErpApplication.getInstance().get
                intent.putExtra("index", index);
                intent.putExtra("taskId",taskId);
                getActivity().startActivityForResult(intent,CREATE_LEADER);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(updateFlag) {
            if (leaderEntry != null) {
                tv_leader.setText(leaderEntry.getComment());
            } else {
                tv_leader.setText("");
            }
            updateFlag = false;
        }
    }
}
