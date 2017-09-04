package com.gk.erp012.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.gk.erp012.Constants;
import com.gk.erp012.ErpApplication;
import com.gk.erp012.R;
import com.gk.erp012.entry.UserEntry;
import com.gk.erp012.ui.CreateDepartActivity;
import com.gk.erp012.utils.CustomRequest;
import com.gk.erp012.utils.Logger;
import com.gk.erp012.utils.ToastUtils;

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
public class Stuff0Fragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    private ListView listView;
    private SwipeRefreshLayout layout;
    private List<UserEntry> userEntries = new ArrayList<>();
    private BaseAdapter adapter;
    private Context mContext;
    public Stuff0Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stuff0, container, false);
        mContext = getActivity();
        layout = view.findViewById(R.id.container_items);
        listView = view.findViewById(R.id.listview);

        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        listView.setAdapter(adapter = new BaseSwipeAdapter() {

            @Override
            public int getCount() {
                return userEntries.size();
            }

            @Override
            public UserEntry getItem(int i) {
                return userEntries.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public int getSwipeLayoutResourceId(int position) {
                return R.id.swipe;
            }

            @Override
            public View generateView(int position, ViewGroup parent) {
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
                    }
                });
                view.setTag(view.findViewById(R.id.tv_departName));
                return view;
            }

            @Override
            public void fillValues(int position, View convertView) {
                TextView tv_depart = (TextView) convertView.getTag();
                tv_depart.setText(getItem(position).getAccount());
            }

        });

        return view;
    }

    private void refresh(){
        Map<String,String> params = new HashMap<>();
        params.put("type","1");
        CustomRequest json = new CustomRequest(Constants.METHOD_GETALL_USER,params,this,this);
        ErpApplication.getInstance().addToRequestQueue(json);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        layout.setRefreshing(false);
        ToastUtils.showShortToast(mContext,"更新失败");
    }

    @Override
    public void onResponse(JSONObject response) {
        layout.setRefreshing(false);
        try {
            Logger.d(response.toString());
            List<UserEntry> temp = UserEntry.getListFromJson(response);
            userEntries.clear();
            userEntries.addAll(temp);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
            onErrorResponse(null);
        }
    }
}
