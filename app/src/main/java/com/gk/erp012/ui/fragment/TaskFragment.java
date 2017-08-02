package com.gk.erp012.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gk.erp012.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment {


    public TaskFragment() {
        // Required empty public constructor
    }

    public static TaskFragment getInstance(){
        return new TaskFragment();
    }

    public static TaskFragment getInstance(Bundle bundle){
        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

}
