package com.gk.erp012.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.accessibility.AccessibilityManagerCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gk.erp012.Constants;
import com.gk.erp012.ErpApplication;
import com.gk.erp012.R;
import com.gk.erp012.entry.DepartEntry;
import com.gk.erp012.entry.DepartTypeEntry;
import com.gk.erp012.entry.TaskEntry;
import com.gk.erp012.ui.adapter.TaskAdapter;
import com.gk.erp012.ui.view.HRPopWinwods;
import com.gk.erp012.utils.CustomRequest;
import com.gk.erp012.utils.Logger;
import com.gk.erp012.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {

    private ListView lv_task;
    private SwipeRefreshLayout lv_content;
    private Button btn_select_type, btn_select_depart;
    private HRPopWinwods typePop, departPop;
    private Context mContext;
    private List<String> departTypes = new ArrayList<>();
    private List<String> departs = new ArrayList<>();
    private List<DepartTypeEntry> departTypeEntries = new ArrayList<>();
    private List<DepartEntry> departEntries = null;
    private List<TaskEntry> taskEntries = new ArrayList<>();
    private TaskAdapter taskAdapter;
    public TaskFragment() {
        // Required empty public constructor
    }

    public static TaskFragment getInstance() {
        return new TaskFragment();
    }

    public static TaskFragment getInstance(Bundle bundle) {
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
        lv_content = view.findViewById(R.id.container_items);
        typePop = new HRPopWinwods(mContext, "") {
            @Override
            public void onItemClickIt(int position) {
                DepartTypeEntry entry = departTypeEntries.get(position);
                departPop.update(entry.getDepartNameList());
                btn_select_type.setText(entry.getName());
                btn_select_type.setTag(entry.getId());
                departEntries = entry.getDeparts();
            }
        };
        departPop = new HRPopWinwods(mContext, "") {
            @Override
            public void onItemClickIt(int position) {
                if(departEntries != null){
                    DepartEntry entry = departEntries.get(position);
                    btn_select_depart.setText(entry.getName());
                    btn_select_depart.setTag(entry.getId());
                    taskEntries.clear();
                    taskEntries.addAll(entry.getTasks());
                    if(taskEntries != null){
                        updateShow();
                    }
                }
            }
        };

        lv_content.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ToastUtils.showShortToast(mContext, "获取中···");
                getTasks();
            }
        });
        taskAdapter = new TaskAdapter(mContext,taskEntries);
        lv_task.setAdapter(taskAdapter);

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_select_depart:
                departPop.show(view);
                break;
            case R.id.btn_select_type:
                typePop.show(view);
                break;
        }
    }

    private void getTasks() {
        Map<String, String> params = new HashMap<>();
        params.put("account", ErpApplication.getInstance().getUserEntry().getAccount());
        params.put("type", ErpApplication.getInstance().getUserEntry().getType());
        CustomRequest jsonReq = new CustomRequest(Constants.METHOD_GETALL_TASK, params, this, this);
        ErpApplication.getInstance().addToRequestQueue(jsonReq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        ToastUtils.showShortToast(mContext, "获取任务失败，请重试");
        Logger.d(error.getMessage());
        lv_content.setRefreshing(false);
    }

    @Override
    public void onResponse(JSONObject response) {
        //do refresh
        Logger.d("success");
        try {
            Logger.d(response.toString());
            JSONArray jsonArray = response.getJSONArray("task");
            for (int i = 0; i < jsonArray.length(); i++) {
                DepartTypeEntry entry = DepartTypeEntry.getFormJson(jsonArray.getJSONObject(i));
                departTypeEntries.add(entry);
                departTypes.add(entry.getName());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            lv_content.setRefreshing(false);
        }
        typePop.update(departTypes);
    }

    public void updateShow(){
        taskAdapter.notifyDataSetChanged();
    }
}
