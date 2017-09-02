package com.gk.erp012.ui;

import android.app.Activity;
import android.bluetooth.le.AdvertisingSetParameters;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.gk.erp012.Constants;
import com.gk.erp012.ErpApplication;
import com.gk.erp012.R;
import com.gk.erp012.entry.DepartEntry;
import com.gk.erp012.entry.DepartTypeEntry;
import com.gk.erp012.utils.CustomRequest;
import com.gk.erp012.utils.StringUtils;
import com.gk.erp012.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CreateDepartActivity extends BaseActivity {
    private TextView tv_departName;
    private Button btn_add;
    private ListView listView;
    private BaseAdapter adapter;
    private List<DepartTypeEntry> departEntries;
    private Set<String> checkedIds = new HashSet<>();
    private String departId;
    private boolean update;
    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_depart);
        tv_departName = (TextView) findViewById(R.id.tv_departName);
        btn_add = (Button) findViewById(R.id.btn_submit);
        listView = (ListView) findViewById(R.id.list_departClass);
        departEntries = ErpApplication.getInstance().getDepartTypeEntries();
        listView.setAdapter(adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return departEntries.size();
            }

            @Override
            public Object getItem(int i) {
                return departEntries.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(final int i, View view, ViewGroup viewGroup) {
                final ViewHolder viewHolder;
                if(view == null){
                    viewHolder = new ViewHolder();
                    view = LayoutInflater.from(mContext).inflate(R.layout.item_depart_class,viewGroup,false);
                    viewHolder.tv_departClass = view.findViewById(R.id.item_tv);
                    viewHolder.cb = view.findViewById(R.id.item_cb);
                    viewHolder.cb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                    viewHolder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if(viewHolder.cb.isChecked()){
                                checkedIds.add(departEntries.get(i).getId());
                            }else{
                                checkedIds.remove(departEntries.get(i).getId());
                            }
                        }
                    });
                    view.setTag(viewHolder);
                }else{
                    viewHolder = (ViewHolder) view.getTag();
                }
                viewHolder.tv_departClass.setText(departEntries.get(i).getName());
                return view;
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDepart();
            }
        });
    }

    @Override
    public void initData() {
        departId = getIntent().getStringExtra("departId");
        update = getIntent().getBooleanExtra("update",false);
        if(update){
            tv_departName.setText(getIntent().getStringExtra("departName"));
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        ToastUtils.showShortToast(mContext,"添加失败");
        mPDialog.dismiss();
    }

    @Override
    public void onResponse(JSONObject response) {
        mPDialog.dismiss();
        ToastUtils.showShortToast(mContext,"添加成功");
    }

    class ViewHolder{
        TextView tv_departClass;
        CheckBox cb;
    }

    private String getCheckedIds(){
        String str="";
        for(String id:checkedIds){
            str+=id+";";
        }
        return str;
    }

    private void addDepart(){

        String departClass = getCheckedIds();
        String departName = tv_departName.getText().toString();
        if(StringUtils.isSpace(departClass)){
            return;
        }
        departClass = departClass.substring(0,departClass.length()-1);
        mPDialog.show();
        Map<String,String> params = new HashMap<>();
        params.put("actionType",update?"1":"0");
        params.put("departName",departName);
        params.put("departClass",departClass);
        params.put("departId",departId);
        Log.d("GKTAG",params.toString());
        CustomRequest json = new CustomRequest(Constants.METHOD_ADD_DEPART,params,this,this);
        ErpApplication.getInstance().addToRequestQueue(json);
    }
}
