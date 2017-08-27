package com.gk.erp012.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.gk.erp012.Constants;
import com.gk.erp012.ErpApplication;
import com.gk.erp012.R;
import com.gk.erp012.entry.TaskEntry;
import com.gk.erp012.entry.TaskReportEntry;
import com.gk.erp012.ui.fragment.BaseFragment;
import com.gk.erp012.ui.fragment.LeaderFragment;
import com.gk.erp012.ui.fragment.ReportFragment;
import com.gk.erp012.ui.fragment.SuperFragment;
import com.gk.erp012.ui.view.HRPopWinwods;
import com.gk.erp012.ui.view.MyProgressDialog;
import com.gk.erp012.utils.CustomRequest;
import com.gk.erp012.utils.TimeUtils;
import com.gk.erp012.utils.ToastUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.ielse.view.imagewatcher.ImageWatcher;

public class TaskDetailsActivity extends BaseActivity {

    private TaskEntry taskEntry;
    private TextView tv_name,tv_goal,tv_complete,tv_allNum,tv_chairMan,tv_financing,tv_place,tv_startTime,tv_endTime,tv_report,tv_super,tv_leader;
    public ImageWatcher vImageWatcher;
    private TextView tv_refresh,tv_report_times;

    private ViewPager view_pager;
    private List<BaseFragment> fragmentList;
    private List<TextView> tv_footer = new ArrayList<>();
    private MyProgressDialog pDialog;
    private List<TaskReportEntry> taskReportEntries = new ArrayList<>();
    private int currentIndex = 0;
    private HRPopWinwods hrPopWinwods;
    private List<String> hrStringList =new ArrayList<>();
    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_task_details);
        pDialog = new MyProgressDialog(mContext,"更新中···",null);;
        taskEntry = (TaskEntry) getIntent().getBundleExtra("task").get("task");
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_goal = (TextView) findViewById(R.id.tv_goal);
        tv_complete = (TextView) findViewById(R.id.tv_complete);
        tv_allNum = (TextView) findViewById(R.id.tv_allNum);
        tv_chairMan = (TextView) findViewById(R.id.tv_chairMan);
        tv_financing = (TextView) findViewById(R.id.tv_financing);
        tv_place = (TextView) findViewById(R.id.tv_place);
        tv_startTime = (TextView) findViewById(R.id.tv_startTime);
        tv_endTime = (TextView) findViewById(R.id.tv_endTime);
        tv_report = (TextView) findViewById(R.id.tv_report);
        tv_super = (TextView) findViewById(R.id.tv_super);
        tv_leader = (TextView) findViewById(R.id.tv_leader);
        tv_footer.add(tv_report);
        tv_footer.add(tv_super);
        tv_footer.add(tv_leader);
        for(int i =0;i<tv_footer.size();i++){
            final int index = i;
            tv_footer.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view_pager.setCurrentItem(index);
                }
            });
        }
        tv_name.setText(taskEntry.getTaskName());
        tv_goal.setText(taskEntry.getGoal());
//        tv_complete.setText("");
//        tv_allNum.setText(");
        tv_chairMan.setText(taskEntry.getChairMan());
        tv_financing.setText(taskEntry.getFinancing());
        tv_place.setText(taskEntry.getPlace());
        tv_startTime.setText(TimeUtils.convert2String(taskEntry.getStartTime()));
        tv_endTime.setText(TimeUtils.convert2String(taskEntry.getEndTime()));
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        tv_report_times = (TextView) findViewById(R.id.tv_report_times);
        view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTextColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        hrPopWinwods = new HRPopWinwods(mContext,"") {
            @Override
            public void onItemClickIt(int position) {
                currentIndex = position;
                updateShow();
                tv_report_times.setText(hrStringList.get(position));
            }
        };
        findViewById(R.id.tv_change_report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hrPopWinwods.show(view);
            }
        });
        //刷新
        tv_refresh = (TextView) findViewById(R.id.tv_refresh);
        tv_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2017/8/27 updateShow
                refresh();

            }
        });
        vImageWatcher = ImageWatcher.Helper.with(TaskDetailsActivity.this) // 一般来讲， ImageWatcher 需要占据全屏的位置
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
        refresh();
    }

    @Override
    public void initData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new ReportFragment());
        fragmentList.add(new SuperFragment());
        fragmentList.add(new LeaderFragment());
        view_pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });

        view_pager.setCurrentItem(0);
    }

    private void setTextColor(int index){
        for(TextView tv:tv_footer){
            tv.setTextColor(getResources().getColor(R.color.colorBlack));
        }
        tv_footer.get(index).setTextColor(getResources().getColor(R.color.colorBlue));
    }

    private void refresh(){
        pDialog.show();
        Map<String,String> params = new HashMap<>();
        params.put("taskId",taskEntry.getTaskId());
        CustomRequest jsonReq = new CustomRequest(Constants.METHOD_GET_REPORT,params,this,this);
        ErpApplication.getInstance().addToRequestQueue(jsonReq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        ToastUtils.showShortToast(mContext,"更新失败，请重试···");
        pDialog.dismiss();
    }

    @Override
    public void onResponse(JSONObject response) {
        pDialog.dismiss();
        taskReportEntries.clear();
        Log.d("GK response",response.toString());
        try {
            JSONArray jsonArray = response.getJSONArray("reports");
            hrStringList.clear();
            for(int i = 0;i<jsonArray.length();i++){
                taskReportEntries.add(TaskReportEntry.getFromJson(jsonArray.getJSONObject(i)));
                hrStringList.add("第 "+(i+1)+" 次报告");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            onErrorResponse(null);
            return;
        }
        // TODO: 更新成功
        updateShow();
        hrPopWinwods.update(hrStringList);
    }

    public void updateShow(){

        //更新显示
        for(BaseFragment fragment:fragmentList){
            fragment.update(taskReportEntries.get(currentIndex));
        }
//        fragmentList.get(view_pager.getCurrentItem()).updateShow(taskReportEntries.get(currentIndex));
    }

    @Override
    public void onBackPressed() {
        if(!vImageWatcher.handleBackPressed()) {
            super.onBackPressed();
        }
    }
}
