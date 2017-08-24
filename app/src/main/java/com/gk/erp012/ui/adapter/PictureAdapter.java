package com.gk.erp012.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.gk.erp012.R;
import com.squareup.picasso.Picasso;

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
    private ImageWatcherClick callback;
    public PictureAdapter(Context context, List<String> pics,ImageWatcherClick callback) {
        this.mContext = context;
        this.pics = pics;
        this.callback = callback;
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
            imageViewMap.put(position,viewHolder.view);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(mContext).load(pics.get(position))
                .into(viewHolder.view);
//                viewHolder.view.setImageURI(pics.get(position));
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViews.clear();
                for(int i =0;i<pics.size();i++){
                    imageViews.add(imageViewMap.get(i));
                }
//                        imageViews.addAll(imageViewMap.values());
                callback.show(imageViews.get(position),imageViews,pics);
            }
        });
        return convertView;
    }

    class ViewHolder{
        ImageView view;
    }

    public interface ImageWatcherClick{
        void show(ImageView view,List<ImageView> imageViews,List<String> urlList);
    }


}
