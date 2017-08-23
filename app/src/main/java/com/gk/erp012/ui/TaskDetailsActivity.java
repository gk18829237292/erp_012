package com.gk.erp012.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.gk.erp012.R;
import com.gk.erp012.entry.TaskEntry;
import com.gk.erp012.ui.fragment.LeaderFragment;
import com.gk.erp012.ui.fragment.ReportFragment;
import com.gk.erp012.ui.fragment.SuperFragment;
import com.gk.erp012.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskDetailsActivity extends BaseActivity {

    private TaskEntry entry;
    private TextView tv_name,tv_goal,tv_complete,tv_allNum,tv_chairMan,tv_financing,tv_place,tv_startTime,tv_endTime,tv_report,tv_super,tv_leader;

    private ViewPager view_pager;
    private List<Fragment> fragmentList;
    private List<TextView> tv_footer = new ArrayList<>();
    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_task_details);
        entry = (TaskEntry) getIntent().getBundleExtra("task").get("task");
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
        tv_name.setText(entry.getTaskName());
        tv_goal.setText(entry.getGoal());
//        tv_complete.setText("");
//        tv_allNum.setText(");
        tv_chairMan.setText(entry.getChairMan());
        tv_financing.setText(entry.getFinancing());
        tv_place.setText(entry.getPlace());
        tv_startTime.setText(TimeUtils.convert2String(entry.getStartTime()));
        tv_endTime.setText(TimeUtils.convert2String(entry.getEndTime()));
        view_pager = (ViewPager) findViewById(R.id.view_pager);

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

}
