package com.gk.erp012.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gk.erp012.R;
import com.gk.erp012.entry.TaskReportEntry;

import ch.ielse.view.imagewatcher.ImageWatcher;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {


    public BaseFragment() {
        // Required empty public constructor
    }


    public abstract void update(TaskReportEntry taskReportEntry);

}
