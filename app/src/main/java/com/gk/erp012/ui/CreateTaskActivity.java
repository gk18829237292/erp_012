package com.gk.erp012.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.gk.erp012.ErpApplication;
import com.gk.erp012.R;
import com.gk.erp012.ui.adapter.PictureAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wq.photo.widget.PickConfig;
import com.yalantis.ucrop.UCrop;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ch.ielse.view.imagewatcher.ImageWatcher;

public class CreateTaskActivity extends BaseActivity {

    public ImageWatcher vImageWatcher;
    private TextView tv_selectPicture;
    private List<String> pics = new ArrayList<>();
    private GridView gridview;
    private PictureAdapter adapter;
    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_create_task);
        tv_selectPicture = (TextView) findViewById(R.id.tv_selectPicture);
        tv_selectPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UCrop.Options options = new UCrop.Options();
                //图片生成格式
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                //图片压缩比
//                options.setCompressionQuality(seekBar.getProgress());

                new  PickConfig.Builder(CreateTaskActivity.this)
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
        vImageWatcher = ImageWatcher.Helper.with(CreateTaskActivity.this) // 一般来讲， ImageWatcher 需要占据全屏的位置
                .setErrorImageRes(R.mipmap.error_picture) // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
                .setLoader(new ImageWatcher.Loader() {
                    @Override
                    public void load(Context context, String url, final ImageWatcher.LoadCallback lc) {
                        if(url.startsWith("http")) {
                            Picasso.with(context).load(url).into(new Target() {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                    lc.onResourceReady(bitmap);
                                }

                                @Override
                                public void onBitmapFailed(Drawable errorDrawable) {
                                    lc.onLoadFailed(errorDrawable);
                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {
                                    lc.onLoadStarted(placeHolderDrawable);
                                }

                            });
                        }else{
                            Picasso.with(context).load(new File(url)).into(new Target() {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                    lc.onResourceReady(bitmap);
                                }

                                @Override
                                public void onBitmapFailed(Drawable errorDrawable) {
                                    lc.onLoadFailed(errorDrawable);
                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {
                                    lc.onLoadStarted(placeHolderDrawable);
                                }

                            });
                        }
                    }
                })
                .create();
        ErpApplication.getInstance().setImageWatcher(vImageWatcher);
        gridview = (GridView) findViewById(R.id.gridview);

    }

    @Override
    public void initData() {
        adapter = new PictureAdapter(mContext,pics);
        gridview.setAdapter(adapter);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK && requestCode == PickConfig.PICK_REQUEST_CODE){
            //在data中返回 选择的图片列表
            pics.clear();
            pics.addAll(data.getStringArrayListExtra("data"));
            Log.d("GKDEBUG",pics.toString());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        if(!vImageWatcher.handleBackPressed()) {
            super.onBackPressed();
        }
    }
}
