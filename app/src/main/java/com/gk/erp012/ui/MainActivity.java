package com.gk.erp012.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.gk.erp012.Constants;
import com.gk.erp012.ErpApplication;
import com.gk.erp012.R;
import com.gk.erp012.entry.UserEntry;
import com.gk.erp012.ui.fragment.DepartFragment;
import com.gk.erp012.ui.fragment.StuffFragment;
import com.gk.erp012.ui.fragment.TaskFragment;
import com.gk.erp012.utils.FragmentUtils;
import com.gk.erp012.utils.StringUtils;
import com.gk.erp012.utils.ToastUtils;

import org.json.JSONObject;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;

    private TextView tv_name,tv_telNum;
    private Toolbar toolbar;


    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if(savedInstanceState == null){
            toolbar.setTitle("任务管理");
            FragmentUtils.commitFragment(getSupportFragmentManager(),TaskFragment.getInstance(),R.id.frame_content);
            navigationView.setCheckedItem(R.id.menu_task);
        }
        setSupportActionBar(toolbar);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //以上是源代码 不动

        tv_telNum =  navigationView.getHeaderView(0).findViewById(R.id.tv_telNum);
        tv_name =  navigationView.getHeaderView(0).findViewById(R.id.tv_name);
        navigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2017/7/30 跳转到个人信息页
                Intent intent = new Intent(mContext,UserInfoActivity.class);
                intent.putExtra("isMySelf",true);
                startActivity(intent);
                ToastUtils.showShortToast(mContext,"click me");
            }
        });
    }

    @Override
    public void initData() {
        UserEntry entry = ErpApplication.getInstance().getUserEntry();
        if(entry.isZhixingzhe()){
            tv_name.setText(entry.getName_1()+"("+entry.getDepartName()+")");
        }else{
            tv_name.setText(entry.getName_1());
        }
        tv_telNum.setText(entry.getTelNum());

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.menu_logout:
                UserEntry.clearSpref(mSpref);
                mSpref.putCommit(Constants.FLAG_LOGIN,false);
                ErpApplication.getInstance().setUserEntry(null);
                Intent intent = new Intent(mContext,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.menu_task:
                FragmentUtils.commitFragment(getSupportFragmentManager(),new TaskFragment(),R.id.frame_content);
                toolbar.setTitle("任务管理");
                break;
            case R.id.menu_depart:
                FragmentUtils.commitFragment(getSupportFragmentManager(),new DepartFragment(),R.id.frame_content);
                toolbar.setTitle("部门管理");
                break;
            case R.id.menu_stuff:
                FragmentUtils.commitFragment(getSupportFragmentManager(),new StuffFragment(),R.id.frame_content);
                toolbar.setTitle("人员管理");
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }
}
