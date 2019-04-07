package com.apps.haitao.twatcher.twclient.activities.twutil;

import android.widget.Toast;

import com.apps.haitao.twatcher.twclient.activities.WatchActivity;
import com.apps.haitao.twatcher.twclient.activities.tables.TW_Users;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http2.ConnectionShutdownException;

public final class HttpUtil {

    //demo
//    public static String sendHttpRequest(String addressUrl){
//        HttpURLConnection connection = null;
//        try {
//            URL url = new URL(addressUrl);
//            connection = (HttpURLConnection)url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setConnectTimeout(8000);
//            connection.setReadTimeout(8000);
//            connection.setDoInput(true);
//            connection.setDoOutput(true);
//            InputStream in = connection.getInputStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//            StringBuilder response = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null){
//                response.append(line);
//            }
//            return response.toString();
//        }catch (Exception e){
//            e.printStackTrace();
//            return e.getMessage();
//        }finally {
//            if (connection != null){
//                connection.disconnect();
//            }
//        }
//    }
//
//    public static void sendHttpRequestInChildThread(final String addressUrl, final HttpCallbackListener listener) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                HttpURLConnection connection = null;
//                try {
//                    URL url = new URL(addressUrl);
//                    connection = (HttpURLConnection)url.openConnection();
//                    connection.setRequestMethod("GET");
//                    connection.setConnectTimeout(8000);
//                    connection.setReadTimeout(8000);
//                    connection.setDoInput(true);
//                    connection.setDoOutput(true);
//                    InputStream in = connection.getInputStream();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//                    StringBuilder response = new StringBuilder();
//                    String line;
//                    while ((line = reader.readLine()) != null){
//                        response.append(line);
//                    }
//                    //return response.toString();
//                    if (listener != null){
//                        listener.onFinish(response.toString());
//                    }
//                }catch (Exception e){
//                    //e.printStackTrace();
//                    //return e.getMessage();
//                    if (listener != null){
//                        listener.onError(e);
//                    }
//                }finally {
//                    if (connection != null){
//                        connection.disconnect();
//                    }
//                }
//            }
//        }).start();
//    }
//
//    public static void sendOkHttpRequest(String addressUrl, okhttp3.Callback callback){
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url(addressUrl).build();
//        client.newCall(request).enqueue(callback);
//    }

    private static final String DEFAULT_SERVER_ADDRESS = "http://192.168.31.98/TWServer/";//"http://192.168.1.103/TWServer/";

    //"http://192.168.43.201/TWServer/"; //10.0.2.2

    private static final String LOGIN_ADDRESS = "LoginCheck";

    private static final String REGISTER_ADDRESS = "RegisterSave";

    private static final String CHILD_MSG_ADDRESS = "HandleChildMsg";

    private static final String PARENT_MSG_ADDRESS = "HandleParentMsg";

    private static final String ASSOCIATION_ADDRESS = "CreateAssociation";

    private static final String CHANGE_PASS_ADDRESS = "ChangePassword";

    public static final String MODE_GET = "GET";

    public static final String MODE_POST = "POST";

//    public static final int LOGIN_MODE_GET_CODE = 0;
//
//    public static final int LOGIN_MODE_POST_CODE = 1;
//
//    public static final int REGISTER_MODE_GET_CODE = 2;
//
//    public static final int REGISTER_MODE_POST_CODE = 3;

    public static void parseJSONWithGSON() {

    }

    public static void parseJSONWithJSONObject() {

    }

    public static String sendLoginRequest(String account, String password) throws IOException{
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("account", account).add("password", password)
                //.add("action", "LOGIN")
                .build();
        Request request = new Request.Builder()
                .url(DEFAULT_SERVER_ADDRESS + LOGIN_ADDRESS)
                .post(requestBody).build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

    public static String sendLoginRequest(String account, String password, String mode) throws IOException{
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = DEFAULT_SERVER_ADDRESS + LOGIN_ADDRESS;
        Request request = null;
        if (mode.equals(MODE_GET)) {
            url += (new StringBuilder().append("?")
                    .append("account=").append(account).append("&")
                    .append("password=").append(password).append("&")
                    //.append("action=").append("LOGIN")
                    ).toString();
            request = new Request.Builder().url(url).build();
        } else {
            RequestBody requestBody = new FormBody.Builder()
                    .add("account", account).add("password", password)
                    //.add("action", "LOGIN")
                    .build();
            request = new Request.Builder().url(url).post(requestBody).build();
        }
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

    public static String sendRegisterRequest(TW_Users twUser) throws IOException{
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("user_id", twUser.getUser_id()).add("name", twUser.getName())
                .add("password", twUser.getPassword())//.add("user_name", twUser.getUser_name())
                .add("gender", twUser.getGender())//.add("id_card_num", twUser.getId_card_num())
                .add("phone_num", String.valueOf(twUser.getPhone_num()))
                .add("email", twUser.getEmail())//.add("identity", twUser.getIdentity())
                .add("is_child", String.valueOf(twUser.isIs_child()))
                .add("register_time", twUser.getRegister_time().toString()).build();
        Request request = new Request.Builder()
                .url(DEFAULT_SERVER_ADDRESS + REGISTER_ADDRESS)
                .post(requestBody).build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

    public static String sendRegisterJSONRequest(String userJson) throws IOException{
        LogUtil.d(Utility.WATCH_TAG, userJson);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //.connectTimeout(10, TimeUnit.SECONDS)
                //.readTimeout(15, TimeUnit.SECONDS)
                .build();
        //String responseData = "";
        RequestBody requestBody = new FormBody.Builder()
                .add("userJson", userJson).build();
        Request request = new Request.Builder()
                .url(DEFAULT_SERVER_ADDRESS + REGISTER_ADDRESS)
                .post(requestBody).build();
        //Call dealCall = okHttpClient.newCall(request);
//        dealCall.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                //请求超时
//                if (e instanceof SocketTimeoutException) {
//                    LogUtil.e(Utility.WATCH_TAG, "连接超时!");
//                    //Toast.makeText(, "", Toast.LENGTH_SHORT).show();
//                }
//                //
//                if (e instanceof ConnectException) {
//                    LogUtil.e(Utility.WATCH_TAG, "连接出错!");
//                }
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//            }
//        });
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

    public static String sendReceiveChildMsgRequest(TW_Users child) throws IOException{
        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.newBuilder()
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .readTimeout(15, TimeUnit.SECONDS);
        String url = DEFAULT_SERVER_ADDRESS + CHILD_MSG_ADDRESS;
        String condition = "?user_id=" + child.getUser_id() + "&phone_num" + child.getPhone_num();
        Request request = new Request.Builder().url(url + condition).build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

    public static String sendCreateAssociationRequest(String parentId, String childPhoneNum) throws IOException{
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = DEFAULT_SERVER_ADDRESS + ASSOCIATION_ADDRESS;
        String condition = "?parentId=" + parentId + "&childPhoneNum=" + childPhoneNum;
        Request request = new Request.Builder().url(url + condition).build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }

    public static String sendChangePasswordRequest(String phoneNum, String password) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = DEFAULT_SERVER_ADDRESS + CHANGE_PASS_ADDRESS;
        String condition = "?phoneNum=" + phoneNum + "&password=" + password;
        Request request = new Request.Builder().url(url + condition).build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }





}
