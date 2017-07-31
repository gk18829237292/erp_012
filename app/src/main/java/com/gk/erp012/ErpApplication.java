package com.gk.erp012;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.gk.erp012.entry.UserEntry;
import com.gk.erp012.utils.SprefUtils;

/**
 * Created by pc_home on 2017/7/29.
 */

public class ErpApplication extends Application {
    private static final String TAG = "ErpApplication";
    private static ErpApplication mInstance;
    private UserEntry userEntry;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
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
