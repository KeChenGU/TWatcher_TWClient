package com.apps.haitao.twatcher.twclient.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.apps.haitao.twatcher.twclient.activities.twutil.NetWorkUtil;
import com.apps.haitao.twatcher.twclient.activities.twutil.Utility;

public class NetWorkStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            Toast.makeText(context, "网络状态改变!", Toast.LENGTH_LONG).show();
            Utility.isNetWorkConnected = NetWorkUtil.getAPNType(context)>0;
        }
    }
}
