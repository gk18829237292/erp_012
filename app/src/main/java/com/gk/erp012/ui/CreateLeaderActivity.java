package com.gk.erp012.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.gk.erp012.Constants;
import com.gk.erp012.ErpApplication;
import com.gk.erp012.R;
import com.gk.erp012.utils.CustomRequest;
import com.gk.erp012.utils.StringUtils;
import com.gk.erp012.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateLeaderActivity extends BaseActivity {

    private TextView tv_content;

    private int index,taskId;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_create_leader);
        tv_content = (TextView) findViewById(R.id.tv_content);
        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }

    @Override
    public void initData() {
        index = getIntent().getIntExtra("index",-1);
        taskId = getIntent().getIntExtra("taskId",-1);
        if(taskId == -1){
            ToastUtils.showShortToast(mContext,"未知错误");
            finish();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mPDialog.dismiss();
        ToastUtils.showShortToast(mContext,"创建失败");
    }

    @Override
    public void onResponse(JSONObject response) {
        mPDialog.dismiss();
//        ToastUtils.showShortToast(mContext,"成功");
        setResult(RESULT_OK);
        finish();
    }

    private void submit(){
        String content = tv_content.getText().toString();
        if(StringUtils.isListSpace(content) ){
            ToastUtils.showShortToast(mContext,"信息不完整");
            return;
        }
        Map<String,String> params = new HashMap<>();
        params.put("index_1",index+"");
        params.put("comment",content);
        params.put("taskId",taskId+"");
        params.put("name",ErpApplication.getInstance().getUserEntry().getName_1());
        CustomRequest jsonReq = new CustomRequest(Constants.CREATE_LEADER,params,this,this);
        ErpApplication.getInstance().addToRequestQueue(jsonReq);
    }
}
