package com.gk.erp012.ui.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.gk.erp012.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.spec.PSource;

/**
 * PopWindows 工具类
 * Created by ke.gao on 2017/7/7.
 */
public abstract class HRPopWinwods implements IHRItemClickListener {

    private PopupWindow pop;
    private LinearLayout ll_popup;
    private ListView listView;
    private Context mContext;
    private View mView;
    private List<String> values;
    private HRPopAdapter adapter;

    public HRPopWinwods(final Context context,List<String> values){

        this.values = values;
        this.mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.item_popupwindows, null);
        initView();
    }

    public HRPopWinwods(final Context context,String... args){
        this(context,Arrays.asList(args));
    }

    public void update(List<String> values){
        this.values.clear();
        this.values.addAll(values);
        adapter.notifyDataSetChanged();
    }

    private void initView(){
        pop = new PopupWindow(mContext);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setContentView(mView);

        ll_popup = (LinearLayout) mView.findViewById(R.id.ll_popup);
        mView.findViewById(R.id.parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disMiss();
            }
        });
        listView = (ListView) mView.findViewById(R.id.content_menu);
        adapter = new HRPopAdapter(mContext,values,this);
        listView.setAdapter(adapter);

        mView.findViewById(R.id.item_popupwindows_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disMiss();
            }
        });
    }

    public void show(View v){
        ll_popup.startAnimation(AnimationUtils.loadAnimation(
               mContext, R.anim.activity_translate_in));
        pop.showAtLocation(v, Gravity.BOTTOM,0,0);
    }

    private void disMiss(){
        if(pop.isShowing()){
            pop.dismiss();
            ll_popup.clearAnimation();
        }
    }

    @Override
    public void onItemClick(int position) {
        disMiss();
        onItemClickIt(position);
    }

    public abstract void onItemClickIt(int position);


    private class HRPopAdapter extends BaseAdapter{

        private List<String> menus;
        private Context mContext;
        private IHRItemClickListener listener;
        public HRPopAdapter(Context context, List<String> menus, IHRItemClickListener listener) {
            this.menus = menus;
            this.mContext = context;
            this.listener = listener;
        }

        @Override
        public int getCount() {
            return menus.size();
        }

        @Override
        public Object getItem(int position) {
            return menus.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item,null);
                viewHolder.btn = (Button) convertView.findViewById(R.id.btn_item);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(position);
                }
            });
            viewHolder.btn.setText(menus.get(position));
            return convertView;
        }
    }

    private final class ViewHolder{
        public Button btn;
    }



}

