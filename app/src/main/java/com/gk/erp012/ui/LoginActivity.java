package com.gk.erp012.ui;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gk.erp012.Constants;
import com.gk.erp012.ErpApplication;
import com.gk.erp012.R;
import com.gk.erp012.entry.UserEntry;
import com.gk.erp012.ui.view.MyProgressDialog;
import com.gk.erp012.utils.CustomRequest;
import com.gk.erp012.utils.Logger;
import com.gk.erp012.utils.StringUtils;
import com.gk.erp012.utils.ToastUtils;

import org.json.JSONObject;

import static com.gk.erp012.Constants.FLAG_LOGIN;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity  implements Response.Listener<JSONObject>,Response.ErrorListener{

    private static final String TAG = "LoginActivity";

    private EditText et_account,et_password;
    private Button btn_login;
    private boolean mIsLastSuccess = false; //在上次登录成功的时候设置为true
    private MyProgressDialog pDialog;



    @Override
    public void initView() {
        setContentView(R.layout.activity_login);
        pDialog = new MyProgressDialog(mContext, "登录中···", null);
        et_account = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = et_account.getText().toString();
                String password = et_password.getText().toString();
                if(StringUtils.isListSpace(account,password)){
                    ToastUtils.showShortToast(mContext,"请输入正确的用户名和密码");
                }else{
                    login(account,password);
                }
            }
        });
    }

    @Override
    public void initData() {
        mIsLastSuccess = mSpref.getBoolean(FLAG_LOGIN,false);
        UserEntry entry = ErpApplication.getInstance().getUserEntry(mSpref);
        if(mIsLastSuccess && entry != null){
            //自动登录
            login(entry.getAccount(),entry.getPassword());
        }
    }

    private void login(String account,String password){
        pDialog.show();
        Map<String,String> params = new HashMap<>();
        params.put("account",account);
        params.put("password",password);
        CustomRequest jsonReq = new CustomRequest(Constants.METHOD_ACCOUNT_AUTHORIZE,params,this,this);
        ErpApplication.getInstance().addToRequestQueue(jsonReq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //登录失败
        pDialog.dismiss();
        mSpref.putCommit(FLAG_LOGIN,false);
        ToastUtils.showShortToast(mContext,"登录失败");
    }

    @Override
    public void onResponse(JSONObject response) {
        UserEntry entry = UserEntry.getFromJson(response);
        if(entry != null){
            //登录成功
            pDialog.dismiss();
            mSpref.putCommit(FLAG_LOGIN,true);
            entry.writeToSpref(mSpref);
            ErpApplication.getInstance().setUserEntry(entry);
            Intent intent = new Intent(mContext,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }else{
            onErrorResponse(null);
        }

    }
}

