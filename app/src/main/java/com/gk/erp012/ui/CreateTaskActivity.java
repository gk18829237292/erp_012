package com.gk.erp012.ui;

import android.app.Activity;
import android.os.Bundle;

import com.android.volley.VolleyError;
import com.gk.erp012.R;

import org.json.JSONObject;

public class CreateTaskActivity extends BaseActivity {



    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_create_task);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }
}
