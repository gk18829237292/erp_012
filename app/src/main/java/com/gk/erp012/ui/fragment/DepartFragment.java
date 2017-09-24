package com.gk.erp012.ui.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.gk.erp012.Constants;
import com.gk.erp012.ErpApplication;
import com.gk.erp012.R;
import com.gk.erp012.entry.DepartTypeEntry;
import com.gk.erp012.ui.BaseActivity;
import com.gk.erp012.ui.DepartListActivity;
import com.gk.erp012.ui.adapter.TaskAdapter2;
import com.gk.erp012.utils.CustomRequest;
import com.gk.erp012.utils.Logger;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    private List<DepartTypeEntry> departTypeEntries = new ArrayList<>();
    private SweetAlertDialog pDialog;
    private Context mContext;
    private SwipeRefreshLayout lv_content;
    private ListView lv_task;
    private BaseAdapter adpter;
    public DepartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragment_depart, container, false);
        pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        lv_content = view.findViewById(R.id.container_items);
        lv_task = view.findViewById(R.id.lv_task);
        lv_content.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ToastUtils.showShortToast(mContext, "更新任务中");
                //清空操作
                getDepart();
            }
        });
        view.findViewById(R.id.btn_add_departClass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(false,"","");
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
                                        delete(departTypeEntries.get(position).getId());
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
                        DepartTypeEntry entry = departTypeEntries.get(position);
                        showAlertDialog(true,entry.getName(),entry.getId());
                    }
                });
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), DepartListActivity.class);
                        intent.putExtra("index",position);
                        startActivity(intent);
                    }
                });
                view.setTag(view.findViewById(R.id.tv_departName));
                return view;
            }

            @Override
            public void fillValues(int position, View convertView) {
                TextView tv_depart = (TextView) convertView.getTag();
//                tv_depart.setText("asd");
                tv_depart.setText(departTypeEntries.get(position).getName());
            }

            @Override
            public int getCount() {
                return departTypeEntries.size();
            }

            @Override
            public Object getItem(int i) {
                return departTypeEntries.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDepart();
    }

    private void getDepart() {
        pDialog.show();
        departTypeEntries.clear();
        Map<String, String> params = new HashMap<>();
        params.put("account", ErpApplication.getInstance().getUserEntry().getAccount());
        params.put("type", ErpApplication.getInstance().getUserEntry().getType());
//        params.put("depart",ErpApplication.getInstance().getUserEntry().get)
        CustomRequest jsonReq = new CustomRequest(Constants.METHOD_GETALL_DEPART, params, this, this);
        ErpApplication.getInstance().addToRequestQueue(jsonReq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        pDialog.dismiss();
        ToastUtils.showShortToast(mContext,"失败-> 没有权限");
        lv_content.setRefreshing(false);

    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray jsonArray = response.getJSONArray("task");
            departTypeEntries.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                DepartTypeEntry entry = DepartTypeEntry.getFormJson(jsonArray.getJSONObject(i));
                departTypeEntries.add(entry);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            lv_content.setRefreshing(false);
            pDialog.dismiss();
        }
        adpter.notifyDataSetChanged();
//        ToastUtils.showShortToast(mContext,"成功");

    }
    private void showAlertDialog(final boolean update, String name, final String departClassId) {
        final EditText editText = new EditText(mContext);
        if(update){
            editText.setText(name);
        }
        new AlertDialog.Builder(mContext).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String departName = editText.getText().toString();
                if(StringUtils.isSpace(departName)){
                    ToastUtils.showShortToast(mContext,"不可为空");
                    return;
                }
                if(update){
                    addOrUpdate("1",departName,departClassId);
                }else{
                    addOrUpdate("0",departName,"");
                }

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setView(editText).setTitle("添加").show();
    }

    private void addOrUpdate(String actionType,String name,String departClassId){
        String account = ErpApplication.getInstance().getUserEntry().getAccount();
        String type = ErpApplication.getInstance().getUserEntry().getType();
        Map<String,String> params = new HashMap<>();
        params.put("actionType",actionType);
        params.put("departClassName",name);
        params.put("departClassId",departClassId);
        params.put("account",account);
        params.put("type",type);
        CustomRequest jsonReq = new CustomRequest(Constants.METHOD_ADD_DEPARTCLASS, params, this, this);
        ErpApplication.getInstance().addToRequestQueue(jsonReq);
    }

    private void delete(String id){
        String account = ErpApplication.getInstance().getUserEntry().getAccount();
        String type = ErpApplication.getInstance().getUserEntry().getType();
        Map<String,String> params = new HashMap<>();
        params.put("actionType","0");
        params.put("id",id);
        params.put("account",account);
        params.put("type",type);
        CustomRequest jsonReq = new CustomRequest(Constants.METHOD_DELETE, params, this, this);
        ErpApplication.getInstance().addToRequestQueue(jsonReq);
    }

}
