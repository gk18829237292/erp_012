package com.gk.erp012.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.gk.erp012.Constants;
import com.gk.erp012.ErpApplication;
import com.gk.erp012.R;
import com.gk.erp012.ui.TaskDetailsActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ke.gao on 2017/8/24.
 */

public class PictureAdapter extends BaseAdapter {

    private Context mContext;
    private List<ImageView> imageViews = new ArrayList<>();
    private Map<Integer,ImageView> imageViewMap = new HashMap<>();
    private List<String> pics;
    public PictureAdapter(Context context, List<String> pics) {
        this.mContext = context;
        this.pics = pics;
    }

    @Override
    public int getCount() {
        return pics.size();
    }

    @Override
    public Object getItem(int i) {
        return pics.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_pic,parent,false);
            viewHolder.view = (ImageView) convertView.findViewById(R.id.view_photo);
//            imageViewMap.put(position,viewHolder.view);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(!imageViewMap.containsKey(position)){
            imageViewMap.put(position,viewHolder.view);
        }
        if(pics.get(position).startsWith("http")){
            Picasso.with(mContext).load(pics.get(position))
                    .into(viewHolder.view);
        }else{
            Picasso.with(mContext).load(new File(pics.get(position)))
                    .into(viewHolder.view);
        }

//                viewHolder.view.setImageURI(pics.get(position));
//        viewHolder.view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                imageViews.clear();
//                for(int i =0;i<pics.size();i++){
//                    imageViews.add(imageViewMap.get(i));
//                }
//
//                ErpApplication.getInstance().getImageWatcher().show(imageViews.get(position),imageViews,pics);
//            }
//        });
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
////        imageViewMap.clear();
//        imageViews.clear();
//        for(int i =0;i<getCount();i++){
//            imageViews.add(new ImageView(mContext));
//        }
    }

    class ViewHolder{
        ImageView view;
    }

}
