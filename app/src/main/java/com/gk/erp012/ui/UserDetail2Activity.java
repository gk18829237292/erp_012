package com.gk.erp012.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gk.erp012.Constants;
import com.gk.erp012.ErpApplication;
import com.gk.erp012.R;
import com.gk.erp012.entry.DepartEntry;
import com.gk.erp012.entry.UserEntry;
import com.gk.erp012.ui.view.HRPopWinwods;
import com.gk.erp012.utils.CustomRequest;
import com.gk.erp012.utils.StringUtils;
import com.gk.erp012.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDetail2Activity extends BaseActivity {

    private boolean update = false;
    private TextView tv_account,tv_password,tv_name,tv_phone;
    private Button btn_submit;
    private List<DepartEntry> departEntries = new ArrayList<>();
    private List<String> departNames = new ArrayList<>();
    private BaseAdapter adapter;
    private ListView listView;
    private Set<String> checkedIds = new HashSet<>();
    private UserEntry userEntry;
    private String type;
    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_detail2);
        tv_account = (TextView) findViewById(R.id.tv_account);
        tv_password = (TextView) findViewById(R.id.tv_password);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return departNames.size();
            }

            @Override
            public String getItem(int i) {
                return departNames.get(i);
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


    }

    @Override
    public void initData() {
        update = getIntent().getBooleanExtra("update",false);
        type = getIntent().getStringExtra("type");
        if(update){
            userEntry = (UserEntry) getIntent().getSerializableExtra("user");
            tv_account.setText(userEntry.getAccount());
            tv_name.setText(userEntry.getName());
            tv_phone.setText(userEntry.getTelNum());
            tv_account.setEnabled(false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPDialog.show();
        Map<String, String> params = new HashMap<>();

//        params.put("depart",ErpApplication.getInstance().getUserEntry().get)
        CustomRequest jsonReq = new CustomRequest(Constants.METHOD_GETALL_DEPART2, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    mPDialog.dismiss();
                    List<DepartEntry> tmp = DepartEntry.getListFromJson(response);
                    departEntries.clear();
                    departEntries.addAll(tmp);
                    departNames.clear();
                    for(DepartEntry entry:departEntries){
                        departNames.add(entry.getName());
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, this);
        ErpApplication.getInstance().addToRequestQueue(jsonReq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mPDialog.dismiss();
    }

    @Override
    public void onResponse(JSONObject response) {
        mPDialog.dismiss();
    }

    private void submit(){
        mPDialog.show();
        String account = tv_account.getText().toString();
        String password = tv_password.getText().toString();
        String name = tv_name.getText().toString();
        String telNum = tv_phone.getText().toString();
        String departIds = getCheckedIds();
        if(StringUtils.isListSpace(account,password,departIds)){
            ToastUtils.showShortToast(mContext,"请把信息填写完整");
            return;
        }
        departIds = departIds.substring(0,departIds.length()-1);
        Map<String,String> map = new HashMap<>();
        map.put("actionType",update?"1":"0");
        map.put("account",account);
        map.put("password",password);
        map.put("name",name);
        map.put("telNum",telNum);
        map.put("type", type);
        map.put("departId",departIds);
        CustomRequest json = new CustomRequest(Constants.UPDATE_USER2,map,this,this);
        ErpApplication.getInstance().addToRequestQueue(json);
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
}
