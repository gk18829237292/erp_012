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
import com.gk.erp012.entry.UserEntry;
import com.gk.erp012.utils.CustomRequest;
import com.gk.erp012.utils.StringUtils;
import com.gk.erp012.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class StuffListActivity extends BaseActivity {

    private SwipeRefreshLayout lv_content;
    private ListView lv_task;
    private BaseAdapter adpter;
    private List<UserEntry> userEntries = new ArrayList<>();
    private TextView tv_title;
    private String type;
    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_stuff_list);
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
                                        delete(userEntries.get(position).getAccount());
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
//                        Intent intent = new Intent(mContext,CreateDepartActivity.class);
//                        intent.putExtra("departId",departEntryList.get(position).getId());
//                        intent.putExtra("departName",departEntryList.get(position).getName());
//                        intent.putExtra("update",true);
//                        ErpApplication.getInstance().setDepartTypeEntries(departTypeEntries);
//                        startActivity(intent);
                        addOrUpdate(true,userEntries.get(position));
                    }
                });
                view.setTag(view.findViewById(R.id.tv_departName));
                return view;
            }

            @Override
            public void fillValues(int position, View convertView) {
                TextView tv_depart = (TextView) convertView.getTag();
                tv_depart.setText(userEntries.get(position).getAccount());
            }

            @Override
            public int getCount() {
                return userEntries.size();
            }

            @Override
            public Object getItem(int i) {
                return userEntries.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }
        });
        tv_title = (TextView) findViewById(R.id.title);
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrUpdate(false,null);
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
        type = getIntent().getStringExtra("type");
        if(StringUtils.isSpace(type)){
            ToastUtils.showShortToast(mContext,"异常退出");
            finish();
        }
        switch (type){
            case "1":
                tv_title.setText("督查督办人员");
                break;
            case "2":
                tv_title.setText("部门镇办人员");
                break;
            case "3":
                tv_title.setText("区县领导人员");
                break;
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
            List<UserEntry> tmp = UserEntry.getListFromJson(response);
            userEntries.clear();
            userEntries.addAll(tmp);
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            lv_content.setRefreshing(false);
            mPDialog.dismiss();
        }
        adpter.notifyDataSetChanged();
    }

    private void getDepart() {
        mPDialog.show();
        userEntries.clear();
        Map<String, String> params = new HashMap<>();
        params.put("type", type);
        CustomRequest jsonReq = new CustomRequest(Constants.METHOD_GETALL_USER, params, this, this);
        ErpApplication.getInstance().addToRequestQueue(jsonReq);
    }

    private void delete(String id){
        Map<String,String> params = new HashMap<>();
        params.put("account",id);
        params.put("type",type);
        CustomRequest jsonReq = new CustomRequest(Constants.METHOD_DELETE_USER, params, this, this);
        ErpApplication.getInstance().addToRequestQueue(jsonReq);
    }

    private void addOrUpdate(boolean update,UserEntry entry){
        Intent intent = null;
        if(type.equals("2")){
            intent = new Intent(mContext,UserDetailActivity.class);
        }else {
            intent = new Intent(mContext,UserDetail2Activity.class);
        }
        intent.putExtra("type",type);
        intent.putExtra("update",update);
        intent.putExtra("user",entry);
        startActivity(intent);
    }
}
