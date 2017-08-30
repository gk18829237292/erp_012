package com.gk.erp012.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;

import com.gk.erp012.ErpApplication;
import com.gk.erp012.R;
import com.gk.erp012.entry.ReportEntry;
import com.gk.erp012.entry.TaskReportEntry;
import com.gk.erp012.ui.CreateReportActivity;
import com.gk.erp012.ui.adapter.PictureAdapter;

import java.util.ArrayList;
import java.util.List;

import ch.ielse.view.imagewatcher.ImageWatcher;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends BaseFragment {

    private ImageWatcher vImageWatcher;
    private GridView gridView;
    private PictureAdapter adapter;
    private EditText tv_report;
    private List<String> pics = new ArrayList<>();
    private boolean updateFlag = false;
    private ReportEntry reportEntry;
    private int index,taskId;
    public static final int CREATE_REPORT = 983;
    public ReportFragment() {
        // Required empty public constructor
    }



    @Override
    public void setArguments(Bundle args) {
        vImageWatcher = (ImageWatcher) args.get("ad");
    }

    @Override
    public void update(TaskReportEntry taskReportEntry) {
        reportEntry = taskReportEntry.getReportEntry();
        updateFlag = true;
        try {
            pics.clear();
            if (reportEntry != null) {
                pics.addAll(reportEntry.getPicture());
                tv_report.setText(reportEntry.getComment());
            } else {
                tv_report.setText("");
            }
            adapter.notifyDataSetChanged();
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        gridView = view.findViewById(R.id.grid_view_pics);
        tv_report = view.findViewById(R.id.tv_report);
        adapter = new PictureAdapter(getContext(), pics);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //
        FloatingActionButton fab = view.findViewById(R.id.fab);
        //判断是否该显示
        if(!ErpApplication.getInstance().getUserEntry().isZhixingzhe()){
            fab.setVisibility(View.GONE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateReportActivity.class);
//                ErpApplication.getInstance().get
                intent.putExtra("index", index);
                intent.putExtra("taskId",taskId);
                getActivity().startActivityForResult(intent,CREATE_REPORT);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(updateFlag) {
            pics.clear();
            if (reportEntry != null) {
                pics.addAll(reportEntry.getPicture());
                tv_report.setText(reportEntry.getComment());
            } else {
                tv_report.setText("");
            }
            adapter.notifyDataSetChanged();
            updateFlag = false;
        }
    }


}
