package com.apps.haitao.twatcher.twclient.activities.tasks;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import java.util.TimerTask;

public class NetWorkTimerTask extends TimerTask {

    private static final int REQUEST_TIME_EXCEED = 5000;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_TIME_EXCEED:

            }
        }
    };

    @Override
    public void run() {
        handler.sendEmptyMessage(REQUEST_TIME_EXCEED);
    }


}
