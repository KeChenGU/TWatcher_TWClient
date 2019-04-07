package com.apps.haitao.twatcher.twclient.activities.twutil;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;
import android.widget.Toast;

import com.apps.haitao.twatcher.twclient.activities.gsons.TWUser;
import com.apps.haitao.twatcher.twclient.activities.tables.App_Time_Infos;
import com.apps.haitao.twatcher.twclient.activities.tables.TW_Users;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static android.content.Context.USAGE_STATS_SERVICE;
import static com.apps.haitao.twatcher.twclient.activities.twutil.DbUtil.md5;

public final class Utility {

    public static final String WATCH_TAG = "WatchActivity";

    public static boolean isNetWorkConnected = false;

    @TargetApi(23)
    public static List<App_Time_Infos> getTimeInfoListAfterRequestSuccess(Activity activity)
            throws NullPointerException, PackageManager.NameNotFoundException {
        AppCompatActivity compatActivity = null;
        if (activity instanceof AppCompatActivity) {
            compatActivity = (AppCompatActivity)activity;
        }
        List<App_Time_Infos> appTimeInfosList = new ArrayList<>();
        if (compatActivity != null) {

            Calendar beginCal = Calendar.getInstance();

            beginCal.add(Calendar.HOUR_OF_DAY, -1); //倒退1小时

            Calendar endCal = Calendar.getInstance();

            //int backHours = endCal.get(Calendar.HOUR_OF_DAY);

            //LogUtil.d(Utility.WATCH_TAG, "倒退了: " + backHours + "小时");

            //beginCal.add(Calendar.HOUR_OF_DAY, backHours);

            UsageStatsManager usageStatsManager = (UsageStatsManager)compatActivity.getApplicationContext()
                    .getSystemService(USAGE_STATS_SERVICE);

            List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
                    beginCal.getTimeInMillis(), endCal.getTimeInMillis());

            for (UsageStats usageStats: usageStatsList) {
                PackageManager packageManager = compatActivity.getApplicationContext().getPackageManager();
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(usageStats.getPackageName(),
                        PackageManager.GET_META_DATA);
                PackageInfo packageInfo = packageManager.getPackageInfo(usageStats.getPackageName(), PackageManager.GET_META_DATA);
                //if ((applicationInfo.flags & applicationInfo.FLAG_SYSTEM) <= 0) { //该句判断是否是系统内置应用

                    Date whichDay = endCal.getTime();
                    LogUtil.d(Utility.WATCH_TAG, "WhichDay: " + whichDay);
                    //String firstOpenTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    //        .format(new Date(usageStats.getFirstTimeStamp()));
                    //String firstOpenTime = SimpleDateFormat.getTimeInstance()
                    //        .format(new Date(usageStats.getFirstTimeStamp()));
                    //String appName = compatActivity.getApplicationContext().getResources()
                    //        .getString(packageInfo.applicationInfo.labelRes);
                    String appName = packageManager.getApplicationLabel(applicationInfo).toString();
                    LogUtil.d(Utility.WATCH_TAG, "AppName: " + appName);
                    //String appInfo = packageInfo.versionName;

                    String firstOpenTime = DateUtil.getTimeFormat(new Date(usageStats.getFirstTimeStamp()));
                    LogUtil.d(Utility.WATCH_TAG, "FirstOpenTime: " + firstOpenTime);

                    String lastUsedTime = DateUtil.getTimeFormat(new Date(usageStats.getLastTimeStamp()));
                    LogUtil.d(Utility.WATCH_TAG, "LastCloseTime: " + lastUsedTime);

                    int usingMinutes = (int)(usageStats.getTotalTimeInForeground() % (1000 * 60 * 60))/(1000 * 60);
                    LogUtil.d(Utility.WATCH_TAG, "UsingMinutes: " + usingMinutes);

                    Drawable drawable = packageManager.getApplicationIcon(applicationInfo);
                    //BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
                    //Bitmap appIcon = bitmapDrawable.getBitmap();
                    //LogUtil.d(Utility.WATCH_TAG, "App_icon(String): " + appIcon.toString());

                    App_Time_Infos appTimeInfos = new App_Time_Infos();
                    appTimeInfos.setWhich_day(whichDay);
                    //appTimeInfos.setApp_icon(appIcon);
                    appTimeInfos.setApp_icon_draw(drawable);
                    appTimeInfos.setApp_name(appName);
                    appTimeInfos.setFirst_open_time(firstOpenTime);
                    appTimeInfos.setLast_close_time(lastUsedTime);
                    appTimeInfos.setUsing_minutes(usingMinutes);
                    LogUtil.d(Utility.WATCH_TAG, "添加成功!" + appTimeInfos.toString());
                    appTimeInfosList.add(appTimeInfos);
                //}
            }
        }
        return appTimeInfosList;
    }

    public static TWUser createTWUser(Bundle userData) {
        TWUser twUser = new TWUser();
        twUser.setUserId(UUID.randomUUID().toString());
        twUser.setName(userData.getString("name", userData.getString("account", "")));
        twUser.setPassword(md5(userData.getString("password", "123456")));
        twUser.setGender(userData.getString("gender", "male"));
        twUser.setPhoneNum(Long.parseLong(userData.getString("phone_num", userData.getString("account", "110"))));
        twUser.setEmail(userData.getString("email", "123456@gmail.com"));
        twUser.setIdCardNum(userData.getString("user_id_card_num", ""));
        twUser.setIsChild(userData.getBoolean("is_child", false));
        twUser.setRegisterTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        //new java.sql.Date(System.currentTimeMillis())
        return twUser;
    }

    public static String handleRegister(String userJson) throws IOException{
//        if (NetWorkUtil._ping() != 0) {
//            LogUtil.d(Utility.WATCH_TAG, "网络连接出错! ");
//            return null;
//        }
        String backNews = "";
        backNews = HttpUtil.sendRegisterJSONRequest(userJson);
        return backNews;
    }

    public static String handleLogin(String account, String password) {
        String backNews = null;

        return backNews;
    }


}
