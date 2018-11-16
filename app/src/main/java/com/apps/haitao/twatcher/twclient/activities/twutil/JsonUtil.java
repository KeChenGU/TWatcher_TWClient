package com.apps.haitao.twatcher.twclient.activities.twutil;

import com.apps.haitao.twatcher.twclient.activities.gsons.TWUser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public final class JsonUtil {

    public final static boolean isUserJSONValidByGson(String jsonInString) {
        try {
            new Gson().fromJson(jsonInString, TWUser.class);
            return true;
        } catch(JsonSyntaxException ex) {
            return false;
        }
    }
}
