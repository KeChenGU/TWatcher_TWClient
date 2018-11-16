package com.apps.haitao.twatcher.twclient.activities.interfaces;

public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
