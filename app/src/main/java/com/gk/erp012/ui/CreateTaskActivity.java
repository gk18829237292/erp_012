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
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.gk.erp012.Constants;
import com.gk.erp012.ErpApplication;
import com.gk.erp012.R;
import com.gk.erp012.entry.DepartTypeEntry;
import com.gk.erp012.ui.adapter.PictureAdapter;
import com.gk.erp012.ui.view.HRPopWinwods;
import com.gk.erp012.utils.MultipartRequest;
import com.gk.erp012.utils.StringUtils;
import com.gk.erp012.utils.TimeUtils;
import com.gk.erp012.utils.ToastUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wq.photo.widget.PickConfig;
import com.yalantis.ucrop.UCrop;

import org.feezu.liuli.timeselector.TimeSelector;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.ielse.view.imagewatcher.ImageWatcher;

public class CreateTaskActivity extends BaseActivity {

    public ImageWatcher vImageWatcher;
    private TextView tv_selectPicture;
    private List<String> pics = new ArrayList<>();
    private GridView gridview;
    private PictureAdapter adapter;

    private TextView tv_name,tv_goal,tv_chairMan,tv_financing,tv_place,tv_startTime,tv_endTime,tv_type,tv_departType,tv_depart;
    private List<TextView> textViewList = new ArrayList<>();
    UCrop.Options options = new UCrop.Options();

    private Button btn_submit;
    private HRPopWinwods typePop,departTypePop,departPop;
    private TimeSelector startTimeSelector,endTimeSelector;
    private List<String> typeList = new ArrayList<String>();
    private List<DepartTypeEntry> departTypeEntries ;
    private List<String> departTypes = new ArrayList<>();
    private List<String> departs = new ArrayList<>();
    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_create_task);
        tv_selectPicture = (TextView) findViewById(R.id.tv_selectPicture);
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        // TODO: 2017/8/28 获取depart

        departTypeEntries = ErpApplication.getInstance().getDepartTypeEntries();
        if(departTypeEntries == null){
            finish();
        }
        for(DepartTypeEntry entry:departTypeEntries){
            departTypes.add(entry.getName());
        }

        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_goal = (TextView) findViewById(R.id.tv_goal);
        tv_chairMan = (TextView) findViewById(R.id.tv_chairMan);
        tv_financing = (TextView) findViewById(R.id.tv_financing);
        tv_place = (TextView) findViewById(R.id.tv_place);
        tv_startTime = (TextView) findViewById(R.id.tv_startTime);
        tv_endTime = (TextView) findViewById(R.id.tv_endTime);
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_departType = (TextView) findViewById(R.id.tv_departType);
        tv_depart = (TextView) findViewById(R.id.tv_depart);

        textViewList.add(tv_name);
        textViewList.add(tv_goal);
        textViewList.add(tv_chairMan);
        textViewList.add(tv_financing);
        textViewList.add(tv_place);
        textViewList.add(tv_startTime);
        textViewList.add(tv_endTime);
        textViewList.add(tv_type);
        textViewList.add(tv_departType);
        textViewList.add(tv_depart);
        typeList.add("日报");
        typeList.add("周报");
        typeList.add("半月报");
        typeList.add("月报");
        typeList.add("季报");
        typeList.add("半年报");
        typeList.add("年报");
        typePop = new HRPopWinwods(mContext,typeList) {
            @Override
            public void onItemClickIt(int position) {
                tv_type.setText(typeList.get(position));
                tv_type.setTag(position);
            }
        };
        tv_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typePop.show(view);
            }
        });
        //开始时间
        startTimeSelector = new TimeSelector(mContext, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                tv_startTime.setText(time);
                tv_startTime.setTag(TimeUtils.convert2Long(time));
            }
        },"1999-1-1 00:00","2999-1-1 00:00");
        tv_startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimeSelector.show();
            }
        });

        endTimeSelector = new TimeSelector(mContext, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                tv_endTime.setText(time);
                tv_endTime.setTag(TimeUtils.convert2Long(time));
            }
        },"1999-1-1 00:00","2999-1-1 00:00");

        tv_endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTimeSelector.show();
            }
        });

        departTypePop = new HRPopWinwods(mContext,departTypes) {
            @Override
            public void onItemClickIt(int position) {
                DepartTypeEntry entry = departTypeEntries.get(position);
                departPop.update(entry.getDepartNameList());
                tv_departType.setText(entry.getName());
                tv_departType.setTag(entry.getId());
                departs = entry.getDepartNameList();
                departPop.update(departs);
            }
        };
        tv_departType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                departTypePop.show(view);
            }
        });

        departPop = new HRPopWinwods(mContext,"") {
            @Override
            public void onItemClickIt(int position) {

            }
        };

        tv_depart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                departPop.show(view);
            }
        });

        tv_selectPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });

    }

    @Override
    public void initData() {
        adapter = new PictureAdapter(mContext,pics);
        gridview.setAdapter(adapter);
    }

    private void submit(){
        String name = tv_name.getText().toString();
        String place = tv_place.getText().toString();
        String financing = tv_financing.getText().toString();
        String chairMan = tv_chairMan.getText().toString();
        String type = tv_type.getText().toString();
        String startTime = tv_startTime.getTag().toString();
        String endTime = tv_endTime.getTag().toString();
        String departType = tv_departType.getTag().toString();
        String depart = tv_depart.getTag().toString();
        String goal = tv_goal.getText().toString();

        if(StringUtils.isListSpace(name,place,financing,chairMan,type,startTime,endTime,departType,depart,goal)){
            ToastUtils.showShortToast(mContext,"请把信息填写完整");
            return;
        }

        Map<String,String> params = new HashMap<>();
        params.put("place",place);
        params.put("financing",financing);
        params.put("chairMan",chairMan);
        params.put("type",type);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        params.put("departType",departType);
        params.put("depart",depart);
        params.put("goal",goal);
        List<File> fileList = new ArrayList<>();
        for(String pic:pics){
            fileList.add(new File(pic));
        }
        MultipartRequest jsonReq = new MultipartRequest(Constants.CREATE_REPORT,this,this,"fileList",fileList,params);
        ErpApplication.getInstance().addToRequestQueue(jsonReq);
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
