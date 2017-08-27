package com.gk.erp012;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.gk.erp012.entry.UserEntry;
import com.gk.erp012.ui.TaskDetailsActivity;
import com.gk.erp012.utils.SprefUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import ch.ielse.view.imagewatcher.ImageWatcher;

/**
 * Created by pc_home on 2017/7/29.
 */

public class ErpApplication extends Application {
    private static final String TAG = "ErpApplication";
    private static ErpApplication mInstance;
    private UserEntry userEntry;
    private RequestQueue mRequestQueue;
    private ImageWatcher mImageWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

    }

    public ImageWatcher getImageWatcher() {
        return mImageWatcher;
    }

    public void setImageWatcher(ImageWatcher mImageWatcher) {
        this.mImageWatcher = mImageWatcher;
    }

    public static ErpApplication getInstance() {
        return mInstance;
    }

    public UserEntry getUserEntry(SprefUtils sprefUtils) {
        if(userEntry == null){
            userEntry = UserEntry.getFromSpref(sprefUtils);
        }
        return userEntry;
    }

    public UserEntry getUserEntry() {
        return userEntry;
    }

    public void setUserEntry(UserEntry userEntry) {
        this.userEntry = userEntry;
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req){
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

}
