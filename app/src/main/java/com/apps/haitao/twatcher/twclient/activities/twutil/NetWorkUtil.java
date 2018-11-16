package com.apps.haitao.twatcher.twclient.activities.twutil;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import static android.content.Context.*;

public final class NetWorkUtil {

    /**
     * 判断是否有网络连接
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * 判断WIFI网络是否可用
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * 判断MOBILE网络是否可用
     * @param context
     * @return
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * 获取当前网络连接的类型信息
     * @param context
     * @return
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }

    /**
     * 获取当前的网络状态 ：没有网络0：WIFI网络1：3G网络2：2G网络3
     *
     * @param context
     * @return
     */
    public static int getAPNType(Context context) {
        int netType = 0;
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = 1;// wifi
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            int nSubType = networkInfo.getSubtype();
            TelephonyManager mTelephony = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                    && !mTelephony.isNetworkRoaming()) {
                netType = 2;// 3G
            } else {
                netType = 3;// 2G
            }
        }
        return netType;
    }

    /**
     * 判断当前的网络连接状态是否能用
     * return ture  可用   flase不可用
     */
    public static boolean ping() {

        String result = null;
        try {
            String ip = "www.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
            Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);// ping网址3次
            // 读取ping的内容，可以不加
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }
            LogUtil.d("------ping------", "result content : " + stringBuffer.toString());
            // ping的状态
            int status = p.waitFor();
            if (status == 0) {
                result = "success";
                return true;
            } else {
                result = "failed";
            }
        } catch (IOException e) {
            result = "IOException";
        } catch (InterruptedException e) {
            result = "InterruptedException";
        } finally {
            LogUtil.d("------result------", "result = " + result);
        }
        return false;

    }

    /**
     * 判断当前的网络连接状态是否能用
     * return 0可用   其他值不可用
     */
    public static int _ping() {

        Runtime runtime = Runtime.getRuntime();
        try {
            Process p = runtime.exec("ping -c 3 www.baidu.com");
            int ret = p.waitFor();
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }


    /*
     * 打开设置网络界面
     */
    public static void showSetNetworkDialog(final Context context) {
        // 提示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("网络设置提示")
                .setMessage("网络连接不可用,是否进行设置?")
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        Intent intent = null;
                        // 判断手机系统的版本 即API大于10 就是3.0或以上版本
                        if (android.os.Build.VERSION.SDK_INT > 10) {
                            intent = new Intent(
                                    android.provider.Settings.ACTION_WIFI_SETTINGS);
                        } else {
                            intent = new Intent();
                            ComponentName component = new ComponentName(
                                    "com.android.settings",
                                    "com.android.settings.WirelessSettings");
                            intent.setComponent(component);
                            intent.setAction("android.intent.action.VIEW");
                        }
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                }).show();

    }


//    @TargetApi(21-28)
//    public static boolean isNetWorkAvailableMoreThanLWay(Context context) {
//        ConnectivityManager connMgr = (ConnectivityManager) context
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        //ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback();
//        //networkCallback.onAvailable();
//        //connMgr.registerDefaultNetworkCallback();
//        return false;
//    }

    /**
     * 判断网络是否可用
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {

        } else {
            //如果仅仅是用来判断网络连接
            //则可以使用 cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断GPS是否可用
     * @param context
     * @return
     */
    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = ((LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE));
        List<String> accessibleProviders = lm.getProviders(true);
        return accessibleProviders != null && accessibleProviders.size() > 0;
    }

    /**
     * 判断WIFI是否可用
     * @param context
     * @return
     */
    public static boolean isWifiEnabled(Context context) {
        ConnectivityManager mgrConn = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mgrTel = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return ((mgrConn.getActiveNetworkInfo() != null && mgrConn
                .getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel
                .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
    }

    /**
     * 判断是否3G
     * @param context
     * @return
     */
    public static boolean is3rd(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        if (networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }


    /**
     * 判断是否WIFI
     * @param context
     * @return
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        if (networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    public static String getPhoneIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        //if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet6Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }




}

