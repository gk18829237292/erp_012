package com.gk.erp012.ui.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gk.erp012.ErpApplication;
import com.gk.erp012.R;
import com.gk.erp012.entry.ReportEntry;
import com.gk.erp012.entry.TaskReportEntry;
import com.gk.erp012.ui.adapter.PictureAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        gridView = view.findViewById(R.id.grid_view_pics);
        tv_report = view.findViewById(R.id.tv_report);
        adapter = new PictureAdapter(getContext(), pics);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
