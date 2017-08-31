package com.gk.erp012.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.gk.erp012.Constants;
import com.gk.erp012.ErpApplication;
import com.gk.erp012.R;
import com.gk.erp012.entry.UserEntry;
import com.gk.erp012.ui.view.HRSwitch;
import com.gk.erp012.utils.CustomRequest;
import com.gk.erp012.utils.StringUtils;
import com.gk.erp012.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserInfoActivity extends BaseActivity {


    private boolean isMySelf = false;
    private HRSwitch hrSwitch;
    private TextView tv_account,tv_password,tv_name,tv_phone;
    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_info);

        hrSwitch = (HRSwitch) findViewById(R.id.hrSwitch);
        tv_account = (TextView) findViewById(R.id.tv_account);
        tv_password = (TextView) findViewById(R.id.tv_password);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_phone = (TextView) findViewById(R.id.tv_phone);

        isMySelf = getIntent().getBooleanExtra("isMySelf",false);
        if(isMySelf){
            hrSwitch.setVisibility(View.VISIBLE);
        }
        hrSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hrSwitch.changeStatue();
                submit();
            }
        });
    }

    @Override
    public void initData() {
        UserEntry entry = ErpApplication.getInstance().getUserEntry();
        tv_account.setText(entry.getAccount());
        tv_name.setText(entry.getName());
        tv_phone.setText(entry.getTelNum());

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mPDialog.dismiss();
        ToastUtils.showShortToast(mContext,"更新失败");
    }

    @Override
    public void onResponse(JSONObject response) {
        mPDialog.dismiss();
        ToastUtils.showShortToast(mContext,"更新成功");
        UserEntry entry = ErpApplication.getInstance().getUserEntry();
        entry.setPassword(tv_password.getText().toString());
        entry.setName(tv_name.getText().toString());
        entry.setTelNum(tv_phone.getText().toString());
    }

    private void submit(){
        mPDialog.show();
        String account = tv_account.getText().toString();
        String password = tv_password.getText().toString();
        String name = tv_name.getText().toString();
        String telNum = tv_phone.getText().toString();

        if(StringUtils.isListSpace(account,password)){
            ToastUtils.showShortToast(mContext,"请把信息填写完整");
            return;
        }
        Map<String,String> map = new HashMap<>();
        map.put("account",account);
        map.put("password",password);
        map.put("name",name);
        map.put("telNum",telNum);
        map.put("type",ErpApplication.getInstance().getUserEntry().getType());
        CustomRequest json = new CustomRequest(Constants.UPDATE_USER,map,this,this);
        ErpApplication.getInstance().addToRequestQueue(json);
    }
}
