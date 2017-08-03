package com.gk.erp012.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.gk.erp012.R;
import com.gk.erp012.ui.view.HRPopWinwods;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment implements View.OnClickListener{

    private ListView lv_task;
    private SwipeRefreshLayout lv_content;
    private Button btn_select_type,btn_select_depart;
    private HRPopWinwods typePop,departPop;
    private Context mContext;
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
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        lv_task = view.findViewById(R.id.lv_task);
        lv_content = view.findViewById(R.id.container_items);
        btn_select_type = view.findViewById(R.id.btn_select_type);
        btn_select_depart = view.findViewById(R.id.btn_select_depart);
        btn_select_type.setOnClickListener(this);
        btn_select_depart.setOnClickListener(this);
        typePop = new HRPopWinwods(mContext,"","","") {
            @Override
            public void onItemClickIt(int position) {

            }
        };
        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
