package com.gk.erp012.ui.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gk.erp012.ErpApplication;
import com.gk.erp012.R;
import com.gk.erp012.entry.SuperEntry;
import com.gk.erp012.entry.TaskReportEntry;
import com.gk.erp012.ui.adapter.PictureAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import ch.ielse.view.imagewatcher.ImageWatcher;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuperFragment extends BaseFragment {

    private ImageWatcher vImageWatcher;
    private GridView gridView;
    private PictureAdapter adapter;
    private TextView tv_star;
    private EditText tv_super;
    private List<String> pics = new ArrayList<>();
    private SuperEntry superEntry;
    private boolean updateFlag;
    public SuperFragment() {
    }



    @Override
    public void update(TaskReportEntry taskReportEntry) {
        superEntry = taskReportEntry.getSuperEntry();
        updateFlag = true;
        try {
            pics.clear();

            if(superEntry!=null) {
                pics.addAll(superEntry.getPicture());
                tv_super.setText(superEntry.getComment());
                tv_star.setText(superEntry.getStar()+"");
            }else{
                tv_super.setText("");
                tv_star.setText("");
            }
            adapter.notifyDataSetChanged();
        }catch (Exception e){

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_super, container, false);
//        vImageWatcher = ImageWatcher.Helper.with(getActivity()) // 一般来讲， ImageWatcher 需要占据全屏的位置
//                .setErrorImageRes(R.mipmap.error_picture) // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
//                .setLoader(new ImageWatcher.Loader() {
//                    @Override
//                    public void load(Context context, String url, final ImageWatcher.LoadCallback lc) {
//                        Picasso.with(context).load(url).into(new Target() {
//                            @Override
//                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                                lc.onResourceReady(bitmap);
//                            }
//
//                            @Override
//                            public void onBitmapFailed(Drawable errorDrawable) {
//                                lc.onLoadFailed(errorDrawable);
//                            }
//
//                            @Override
//                            public void onPrepareLoad(Drawable placeHolderDrawable) {
//                                lc.onLoadStarted(placeHolderDrawable);
//                            }
//                        });
//                    }
//                })
//                .create();
        gridView = view.findViewById(R.id.grid_view_pics);
        tv_super = view.findViewById(R.id.tv_super);
        tv_star = view.findViewById(R.id.tv_star);
        adapter = new PictureAdapter(getContext(), pics);
        gridView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(updateFlag){
            pics.clear();

            if(superEntry!=null) {
                pics.addAll(superEntry.getPicture());
                tv_super.setText(superEntry.getComment());
                tv_star.setText(superEntry.getStar()+"");
            }else{
                tv_super.setText("");
                tv_star.setText("");
            }
            adapter.notifyDataSetChanged();
            updateFlag = false;
        }
    }


}
