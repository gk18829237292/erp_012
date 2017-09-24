package com.gk.erp012.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.gk.erp012.Constants;
import com.gk.erp012.ErpApplication;
import com.gk.erp012.R;
import com.gk.erp012.entry.DepartEntry;
import com.gk.erp012.entry.DepartTypeEntry;
import com.gk.erp012.utils.CustomRequest;
import com.gk.erp012.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DepartListActivity extends BaseActivity {

    private SweetAlertDialog pDialog;
    private SwipeRefreshLayout lv_content;
    private ListView lv_task;
    private BaseAdapter adpter;
    private List<DepartEntry> departEntryList = new ArrayList<>();
    private List<DepartTypeEntry> departTypeEntries = new ArrayList<>();

    private int index;
    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_depart_list);
        pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        lv_content = (SwipeRefreshLayout) findViewById(R.id.container_items);
        lv_task = (ListView) findViewById(R.id.lv_task);
        lv_content.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDepart();
            }
        });
        lv_task.setAdapter(adpter = new BaseSwipeAdapter() {
            @Override
            public int getSwipeLayoutResourceId(int position) {
                return R.id.swipe;
            }

            @Override
            public View generateView(final int position, ViewGroup parent) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_depart, null);
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
                                        delete(departEntryList.get(position).getId());
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
                        Intent intent = new Intent(mContext,CreateDepartActivity.class);
                        intent.putExtra("departId",departEntryList.get(position).getId());
                        intent.putExtra("departName",departEntryList.get(position).getName());
                        intent.putExtra("update",true);
                        ErpApplication.getInstance().setDepartTypeEntries(departTypeEntries);
                        startActivity(intent);
                    }
                });
                view.setTag(view.findViewById(R.id.tv_departName));
                return view;
            }

            @Override
            public void fillValues(int position, View convertView) {
                TextView tv_depart = (TextView) convertView.getTag();
                tv_depart.setText(departEntryList.get(position).getName());
            }

            @Override
            public int getCount() {
                return departEntryList.size();
            }

            @Override
            public Object getItem(int i) {
                return departEntryList.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }
        });

        findViewById(R.id.btn_add_depart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,CreateDepartActivity.class);
                intent.putExtra("departId","");
                ErpApplication.getInstance().setDepartTypeEntries(departTypeEntries);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDepart();
    }

    @Override
    public void initData() {
        index = getIntent().getIntExtra("index",-1);
        if(index == -1){
            ToastUtils.showShortToast(mContext,"异常退出");
            finish();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mPDialog.dismiss();
        ToastUtils.showShortToast(mContext,"失败-> 没有权限");
        lv_content.setRefreshing(false);
    }

    @Override
    public void onResponse(JSONObject response) {

        try {
            JSONArray jsonArray = response.getJSONArray("task");
            departTypeEntries.clear();
            departEntryList.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                DepartTypeEntry entry = DepartTypeEntry.getFormJson(jsonArray.getJSONObject(i));
                departTypeEntries.add(entry);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            lv_content.setRefreshing(false);
            mPDialog.dismiss();
        }
        departEntryList.addAll(departTypeEntries.get(index).getDeparts());
        adpter.notifyDataSetChanged();
//        ToastUtils.showShortToast(mContext,"成功");
    }

    private void getDepart() {
        mPDialog.show();
        departTypeEntries.clear();
        departEntryList.clear();
        Map<String, String> params = new HashMap<>();
        params.put("account", ErpApplication.getInstance().getUserEntry().getAccount());
        params.put("type", ErpApplication.getInstance().getUserEntry().getType());
//        params.put("depart",ErpApplication.getInstance().getUserEntry().get)
        CustomRequest jsonReq = new CustomRequest(Constants.METHOD_GETALL_DEPART, params, this, this);
        ErpApplication.getInstance().addToRequestQueue(jsonReq);
    }

    private void delete(String id){
        String account = ErpApplication.getInstance().getUserEntry().getAccount();
        String type = ErpApplication.getInstance().getUserEntry().getType();
        Map<String,String> params = new HashMap<>();
        params.put("actionType","1");
        params.put("id",id);
        params.put("account",account);
        params.put("type",type);
        CustomRequest jsonReq = new CustomRequest(Constants.METHOD_DELETE, params, this, this);
        ErpApplication.getInstance().addToRequestQueue(jsonReq);
    }
}
