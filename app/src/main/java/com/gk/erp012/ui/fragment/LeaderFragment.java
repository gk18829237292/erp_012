package com.gk.erp012.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.gk.erp012.R;
import com.gk.erp012.entry.LeaderEntry;
import com.gk.erp012.entry.TaskReportEntry;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderFragment extends BaseFragment {

    private EditText tv_leader;
    LeaderEntry leaderEntry;
    private boolean updateFlag;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leader, container, false);
        tv_leader = view.findViewById(R.id.tv_leader);
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
