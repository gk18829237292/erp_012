package com.gk.erp012.ui.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.gk.erp012.R;
import com.gk.erp012.ui.adapter.PictureAdapter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import ch.ielse.view.imagewatcher.ImageWatcher;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment {

    private ImageWatcher vImageWatcher;
    private GridView gridView;
    private PictureAdapter adapter;
    private List<String> pics = new ArrayList<>();
    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        vImageWatcher = ImageWatcher.Helper.with(getActivity()) // 一般来讲， ImageWatcher 需要占据全屏的位置
                .setErrorImageRes(R.mipmap.error_picture) // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
                .setLoader(new ImageWatcher.Loader() {
                    @Override
                    public void load(Context context, String url, final ImageWatcher.LoadCallback lc) {
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
                    }
                })
                .create();
        gridView = view.findViewById(R.id.grid_view_pics);
        pics.add("http://imgsrc.baidu.com/imgad/pic/item/267f9e2f07082838b5168c32b299a9014c08f1f9.jpg");
        pics.add("http://pic62.nipic.com/file/20150319/12632424_132215178296_2.jpg");
        pics.add("http://pic49.nipic.com/file/20140927/19617624_230415502002_2.jpg");
        pics.add("http://img06.tooopen.com/images/20160712/tooopen_sy_170083325566.jpg");
        pics.add("http://img2.imgtn.bdimg.com/it/u=2036358338,4084347706&fm=26&gp=0.jpg");
        adapter = new PictureAdapter(getContext(), pics, new PictureAdapter.ImageWatcherClick() {
            @Override
            public void show(ImageView view, List<ImageView> imageViews, List<String> urlList) {
                vImageWatcher.show(view,imageViews,urlList);
            }
        });
        gridView.setAdapter(adapter);
        return view;
    }

}
