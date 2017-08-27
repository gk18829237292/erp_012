package com.gk.erp012.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Response;
import com.gk.erp012.utils.SprefUtils;

import org.json.JSONObject;


/**
 * Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity  implements Response.Listener<JSONObject>,Response.ErrorListener{

    // 页面上下文
    public Context mContext;
    public SprefUtils mSpref;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        mSpref = new SprefUtils(mContext);
//        // 初始化控件
        initView(savedInstanceState);

//        // 初始化数据
        initData();
    }

    @Override
    protected void onDestroy() {
        dispose();
        super.onDestroy();
    }

    /**
     * 初始化组件(在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。)
     */
    public abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化数据
     */
    public abstract void initData();


    /**
     * 关闭画面
     */
    protected void finishActivity() {
        finish();
    }




    /**
     * 在界面退出等需要解绑观察者的情况下调用此方法统一解绑，防止Rx造成的内存泄漏
     */
    protected void dispose() {

    }
}
