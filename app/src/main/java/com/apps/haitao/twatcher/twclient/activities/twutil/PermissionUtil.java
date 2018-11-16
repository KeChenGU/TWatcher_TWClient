package com.apps.haitao.twatcher.twclient.activities.twutil;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import kr.co.namee.permissiongen.PermissionGen;


public final class PermissionUtil {

    public static class AppInfoPermission {

        public static final int defaultRequestCode = 0x16;

        @TargetApi(23)
        private static final String defaultPermission = Manifest.permission.PACKAGE_USAGE_STATS;

        public static void getPermission(Activity activity) {
            PermissionGen.with(activity)
                    .addRequestCode(defaultRequestCode)
                    .permissions(defaultPermission)
                    .request();
        }

        public static void getPermission(Fragment fragment) {
            PermissionGen.with(fragment)
                    .addRequestCode(defaultRequestCode)
                    .permissions(defaultPermission)
                    .request();
        }

        public static void overrideOnRequestPermissionResult(Activity activity,
                                                             int requestCode,
                                                             String[] permissions,
                                                             int[] grantResults) {
            PermissionGen.onRequestPermissionsResult(activity, requestCode, permissions, grantResults);
        }

        public static <T> List<T> onRequestPermissionSuccess(){
            return new ArrayList<>();
        }


        public static void onRequestPermissionFail() {

        }
    }

    public static void getAccessPermission(Activity activity,
                                           int requestCode,
                                           String permission){
        PermissionGen.with(activity)
                .addRequestCode(requestCode)
                .permissions(permission)
                .request();
    }

}
