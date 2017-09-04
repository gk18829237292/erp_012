package com.gk.erp012.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gk.erp012.Constants;
import com.gk.erp012.ErpApplication;
import com.gk.erp012.R;
import com.gk.erp012.entry.DepartEntry;
import com.gk.erp012.entry.DepartTypeEntry;
import com.gk.erp012.entry.UserEntry;
import com.gk.erp012.ui.view.HRPopWinwods;
import com.gk.erp012.utils.CustomRequest;
import com.gk.erp012.utils.StringUtils;
import com.gk.erp012.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDetailActivity extends BaseActivity {

    private boolean update = false;
    private TextView tv_account,tv_password,tv_name,tv_phone,tv_depart;
    private Button btn_submit;
    private HRPopWinwods popWinwods;
    private UserEntry userEntry;
    private List<DepartEntry> departEntries = new ArrayList<>();
    private List<String> departNames = new ArrayList<>();
    private String type;
    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_detail);
        tv_account = (TextView) findViewById(R.id.tv_account);
        tv_password = (TextView) findViewById(R.id.tv_password);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_depart = (TextView) findViewById(R.id.tv_depart);
        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });

        popWinwods = new HRPopWinwods(mContext,"") {
            @Override
            public void onItemClickIt(int position) {
                tv_depart.setText(departEntries.get(position).getName());
                tv_depart.setTag(departEntries.get(position).getId());
            }
        };
        tv_depart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWinwods.show(view);
            }
        });
    }

    @Override
    public void initData() {
        update = getIntent().getBooleanExtra("update",false);
        type = getIntent().getStringExtra("type");
        if(update){
            userEntry = (UserEntry) getIntent().getSerializableExtra("user");
            tv_account.setText(userEntry.getAccount());
            tv_name.setText(userEntry.getName());
            tv_phone.setText(userEntry.getTelNum());
            tv_account.setEnabled(false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPDialog.show();
        Map<String, String> params = new HashMap<>();

//        params.put("depart",ErpApplication.getInstance().getUserEntry().get)
        CustomRequest jsonReq = new CustomRequest(Constants.METHOD_GETALL_DEPART2, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    mPDialog.dismiss();
                    List<DepartEntry> tmp = DepartEntry.getListFromJson(response);
                    departEntries.clear();
                    departEntries.addAll(tmp);
                    departNames.clear();
                    for(DepartEntry entry:departEntries){
                        departNames.add(entry.getName());
                    }
                    popWinwods.update(departNames);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, this);
        ErpApplication.getInstance().addToRequestQueue(jsonReq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mPDialog.dismiss();
        ToastUtils.showShortToast(mContext,"添加失败");
    }

    @Override
    public void onResponse(JSONObject response) {
        mPDialog.dismiss();
        ToastUtils.showShortToast(mContext,"添加成功");
        finish();
    }

    private void submit(){
        mPDialog.show();
        String account = tv_account.getText().toString();
        String password = tv_password.getText().toString();
        String name = tv_name.getText().toString();
        String telNum = tv_phone.getText().toString();
        String depart = tv_depart.getTag().toString();
        if(StringUtils.isListSpace(account,password,depart)){
            ToastUtils.showShortToast(mContext,"请把信息填写完整");
            return;
        }
        Map<String,String> map = new HashMap<>();
        map.put("actionType",update?"1":"0");
        map.put("account",account);
        map.put("password",password);
        map.put("name",name);
        map.put("telNum",telNum);
        map.put("departId",depart);
        map.put("type", type);
        CustomRequest json = new CustomRequest(Constants.UPDATE_USER2,map,this,this);
        ErpApplication.getInstance().addToRequestQueue(json);
    }
}
