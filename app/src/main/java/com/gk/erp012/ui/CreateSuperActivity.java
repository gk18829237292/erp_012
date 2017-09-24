package com.gk.erp012.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.gk.erp012.Constants;
import com.gk.erp012.ErpApplication;
import com.gk.erp012.R;
import com.gk.erp012.ui.adapter.PictureAdapter;
import com.gk.erp012.ui.view.HRPopWinwods;
import com.gk.erp012.utils.MultipartRequest;
import com.gk.erp012.utils.StringUtils;
import com.gk.erp012.utils.ToastUtils;
import com.wq.photo.widget.PickConfig;
import com.yalantis.ucrop.UCrop;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateSuperActivity extends BaseActivity {

    private EditText tv_content,tv_star;
    private List<String> pics = new ArrayList<>();
    private int taskId,index;

    private PictureAdapter adapter;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_create_super);
        final UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        findViewById(R.id.tv_selectPicture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PickConfig.Builder(CreateSuperActivity.this)
                        .maxPickSize(9)//最多选择几张
                        .isneedcamera(true)//是否需要第一项是相机
                        .spanCount(4)//一行显示几张照片
                        .actionBarcolor(Color.parseColor("#E91E63"))//设置toolbar的颜色
                        .statusBarcolor(Color.parseColor("#D81B60")) //设置状态栏的颜色(5.0以上)
                        .isneedcrop(false)//受否需要剪裁
                        .setUropOptions(options) //设置剪裁参数
                        .isSqureCrop(true) //是否是正方形格式剪裁
                        .pickMode(PickConfig.MODE_MULTIP_PICK)//单选还是多选
                        .build();
            }
        });
        adapter = new PictureAdapter(mContext,pics);
        ((GridView)findViewById(R.id.gridview)).setAdapter(adapter);

        tv_content = (EditText) findViewById(R.id.tv_content);
        tv_star = (EditText) findViewById(R.id.tv_star);

        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }

    @Override
    public void initData() {
        taskId = getIntent().getIntExtra("taskId",-1);
        index = getIntent().getIntExtra("index",-1);
        if(taskId == -1) {
            ToastUtils.showShortToast(mContext, "未知错误");
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
        String reportContent = tv_content.getText().toString();
        String star = tv_star.getText().toString();
        if(StringUtils.isListSpace(reportContent,star) ){
            ToastUtils.showShortToast(mContext,"信息不完整");
            return;
        }
        Map<String,String> params = new HashMap<>();
        params.put("index",index+"");
        params.put("comment",reportContent);
        params.put("star",star);
        params.put("taskId",taskId+"");
        List<File> fileList = new ArrayList<>();
        for(String pic:pics){
            fileList.add(new File(pic));
        }
        MultipartRequest jsonReq = new MultipartRequest(Constants.CREATE_SUPER,this,this,"fileList",fileList,params);
        ErpApplication.getInstance().addToRequestQueue(jsonReq);
        mPDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK && requestCode == PickConfig.PICK_REQUEST_CODE){
            pics.clear();
            pics.addAll(data.getStringArrayListExtra("data"));
            Log.d("GKDEBUG",pics.toString());
            adapter.notifyDataSetChanged();
        }
    }
}
