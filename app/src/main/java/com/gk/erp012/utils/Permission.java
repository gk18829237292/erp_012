package com.gk.erp012.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态权限管理
 */
public class Permission {
    private static final String TAG = "PERMISSION";

    private static final int REQUEST_PERMISSION_CODE = 0x293;

    public static boolean hasPermission(Context context, String perm) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.getApplicationContext().checkSelfPermission(perm) != PackageManager.PERMISSION_DENIED) {
                return true;
            }
            return false;
        }
        return true;
    }

    public static void requestPermissionsIfNeed(Activity activity, String[] perms){
        if((perms.length==0)) return;
         List<String> needPerms = new ArrayList<>();
        for (String perm: perms){
            if(!hasPermission(activity,perm)){
                needPerms.add(perm);
            }
        }
        if(needPerms.size()==0)
            return;
        else {
            Log.e(TAG, "App is requesting for key " + needPerms.toString());
            if (Build.VERSION.SDK_INT >= 23)
                activity.requestPermissions(needPerms.toArray(new String[0]), REQUEST_PERMISSION_CODE);
        }
    }
}
