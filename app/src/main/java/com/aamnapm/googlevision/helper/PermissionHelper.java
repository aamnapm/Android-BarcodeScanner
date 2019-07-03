package com.aamnapm.googlevision.helper;

import android.app.Activity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

/**
 * permission helper
 */
public class PermissionHelper {

    /**
     * permission method
     *
     * @param activity
     * @param listener
     * @param permission
     */
    public static void permission(Activity activity, PermissionListener listener, String permission) {
        Dexter.withActivity(activity)
                .withPermission(permission)
                .withListener(listener)
                .check();
    }

    /**
     * permissions method
     *
     * @param activity
     * @param listener
     * @param permissions
     */
    public static void permissions(Activity activity, MultiplePermissionsListener listener, String... permissions) {
        Dexter.withActivity(activity)
                .withPermissions(permissions)
                .withListener(listener)
                .check();
    }

}
