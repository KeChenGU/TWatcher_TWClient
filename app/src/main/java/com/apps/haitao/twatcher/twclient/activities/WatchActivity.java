package com.apps.haitao.twatcher.twclient.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.haitao.twatcher.twclient.R;
import com.apps.haitao.twatcher.twclient.activities.adapters.AppInfoAdapter;
import com.apps.haitao.twatcher.twclient.activities.adapters.AppTimeInfoAdapter;
import com.apps.haitao.twatcher.twclient.activities.gsons.AppTimeInfo;
import com.apps.haitao.twatcher.twclient.activities.gsons.ChildMsg;
import com.apps.haitao.twatcher.twclient.activities.gsons.TWUser;
import com.apps.haitao.twatcher.twclient.activities.tables.App_Time_Infos;
import com.apps.haitao.twatcher.twclient.activities.tables.TW_Users;
import com.apps.haitao.twatcher.twclient.activities.twutil.DbUtil;
import com.apps.haitao.twatcher.twclient.activities.twutil.HttpUtil;
import com.apps.haitao.twatcher.twclient.activities.twutil.JsonUtil;
import com.apps.haitao.twatcher.twclient.activities.twutil.LogUtil;
import com.apps.haitao.twatcher.twclient.activities.twutil.NetWorkUtil;
import com.apps.haitao.twatcher.twclient.activities.twutil.PermissionUtil;
import com.apps.haitao.twatcher.twclient.activities.twutil.SocketUtil;
import com.apps.haitao.twatcher.twclient.activities.twutil.Utility;
import com.apps.haitao.twatcher.twclient.activities.twutil.ViewUtil;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionSuccess;

public class WatchActivity extends AppCompatActivity {

    private List<App_Time_Infos> appTimeInfosList;

    private AppInfoAdapter appInfoAdapter;

    private AppTimeInfoAdapter appTimeInfoAdapter;

    private boolean registerFlag = false;

    private boolean netWorkFlag = false;

    private String backNews = "";

    //private Socket defaultSocket;

    //
    private ProgressBar netConnBar;

    private RecyclerView appTimeInfoView;

    //private ListView appTimeInfoListView;

    //private TextView swipeText;

    private RelativeLayout childToolBar;

    private RelativeLayout parentToolBar;

    private CircleImageView childUserIcon;

    private CircleImageView parentUserIcon;

    private ImageView parentSettingView;

    private TextView childUserNameView;

    private TextView parentUserNameView;

    private TextView aboutChildView;

    private TextView aboutParentView;

    private TextView parentNotifyView;

    private SwipeRefreshLayout swipeRefreshLayout;

    private PopupMenu parentSettingMenu;
    //
    final int START_HANDLING = 0x00;

    final int STOP_HANDING = 0x01;

    final int REQUEST_FOR_CHILD = 0x02;

    final int REQUEST_FOR_PARENT = 0x03;

    final int NETWORK_ERROR = 0x10;

    final int GET_CHILD_SUCCESS = 0x11;

    final int NO_SUCH_CHILD = 0x12;

    private Handler clientHandler = SocketUtil.createHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case NETWORK_ERROR:
                    clearAppTimeInfoListView();
                    parentNotifyView.setText("网络连接错误!当前网络不可用!");
                    break;
                case GET_CHILD_SUCCESS:
                    if (msg.obj instanceof ChildMsg) {
                        appTimeInfosList.clear();
                        List<AppTimeInfo> appTimeInfos = ((ChildMsg) msg.obj).getAppTimeInfoList();
                        transferAppTimeInfoList(appTimeInfos);
                        appTimeInfoAdapter.notifyDataSetChanged();
                    }
                    break;
                case NO_SUCH_CHILD:
                    clearAppTimeInfoListView();
                    parentNotifyView.setText("找不到对应孩子信息!");
                    break;
                default:
                    break;
            }
            return false;
        }
    });

    @TargetApi(21-28)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PermissionUtil.AppInfoPermission.getPermission(this);
        Intent preIntent = getIntent();
        if (preIntent == null || preIntent.getExtras() == null) {
            preIntent.setClass(this, LoginActivity.class);
            Toast.makeText(this, "状态异常!", Toast.LENGTH_SHORT).show();
            startActivity(preIntent);
        }
        switch (preIntent.getExtras().getString("Action", "login")) {
            case "login":
                loginMode();
                break;
            case "handleRegister":
                registerMode();
                break;
        }


        //swipeText = (TextView)findViewById(R.id.swipe_text);
        //appTimeListView = (ListView)findViewById(R.id.receive_list_view);
        //

        //
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.AppInfoPermission.overrideOnRequestPermissionResult(this,
                requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Message msg = new Message();
        msg.what = STOP_HANDING;
        clientHandler.sendMessage(msg);
    }

    @PermissionSuccess(requestCode = PermissionUtil.AppInfoPermission.defaultRequestCode)
    private void doAfterRequestSuccess() {
        try {
            List<App_Time_Infos> app_time_infos = Utility.getTimeInfoListAfterRequestSuccess(this);
            LogUtil.d(Utility.WATCH_TAG, "正在执行!");
            //Iterator<App_Time_Infos> infosIterator = app_time_infos.iterator();
            //while (infosIterator.hasNext()) {
            //    appTimeInfosList.add(infosIterator.next());
            //}
            appTimeInfosList.addAll(app_time_infos);
        }catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (appTimeInfosList != null) {
            appTimeInfoAdapter.notifyDataSetChanged();
        }
        Toast.makeText(this, "获取成功!", Toast.LENGTH_SHORT).show();
    }


    @PermissionFail(requestCode = PermissionUtil.AppInfoPermission.defaultRequestCode)
    private void doAfterRequestFail() {
        //Toast.makeText(this, "获取失败!请检查是否设置好访问权限!", Toast.LENGTH_SHORT).show();
        //startActivity(new Intent(Child_Settings.ACTION_USAGE_ACCESS_SETTINGS));
    }

    private void loginMode() {
        Intent intent = getIntent();
        Bundle userData = intent.getExtras();
        TW_Users twUser = (TW_Users) userData.getSerializable("loginUser");
        if (twUser == null) {
            Toast.makeText(this, "登录异常!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(WatchActivity.this, LoginActivity.class));
            return;
        }
        boolean childFlag = twUser.isIs_child();
        if(childFlag) {
            setContentView(R.layout.activity_child);
            configChildControls();
            initialChildControls(twUser);
        } else {
            setContentView(R.layout.activity_parent);
            configParentControls();
            initialParentControls(twUser);
        }
    }

    private void registerMode() {
        Intent intent = getIntent();
        final Bundle userData = intent.getExtras();
        String identity = intent.getStringExtra("Identity");
        String gender = intent.getStringExtra("Gender");
        switch (identity) {
            case "child": {
                setContentView(R.layout.activity_child);
                configChildControls();
                initialChildControls(intent);
                userData.putString("gender", gender == null ? "male" : gender);
                userData.putBoolean("is_child", true);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(!NetWorkUtil.isNetworkAvailable(WatchActivity.this)) {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(WatchActivity.this, "网络异常!", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                            netWorkFlag = false;
//                        }
//                    }
//                }).start();
//                if (netWorkFlag) {
//
//
//                }
//                netWorkFlag = true;
//                dealRegisterNetWork(Utility.createTWUser(userData));
//                DbUtil.insertNewUser(userData);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        dealRegisterNetWork(Utility.createTWUser(userData));
                    }
                }).start();
//                if (netWorkFlag && registerFlag) {
//                    DbUtil.insertNewUser(userData);
//                }
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    LogUtil.e(Utility.WATCH_TAG, "异常!");
                }
                dealRegisterAfters(userData);
            }
            break;
            default:
            case "parent": {
                setContentView(R.layout.activity_parent);
                configParentControls();
                initialParentControls(intent);
                userData.putString("gender", gender == null ? "male" : gender);
                userData.putBoolean("is_child", false);
//                if(NetWorkUtil._ping() != 0) {
//                    Toast.makeText(this, "网络异常!", Toast.LENGTH_SHORT).show();
//                    break;
//                }
//                //DbUtil.insertNewUser(userData);
////                new Thread(new Runnable() {
////                    @Override
////                    public void run() {
//                        dealAfters(Utility.handleRegister(DbUtil.insertNewUser(userData)));
////                    }
////                }).start();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(!NetWorkUtil.isNetworkAvailable(WatchActivity.this)) {
//                            Toast.makeText(WatchActivity.this, "网络异常!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }).start();
//                dealAfters(Utility.handleRegister(DbUtil.insertNewUser(userData)));
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(!NetWorkUtil.isNetworkAvailable(WatchActivity.this)) {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(WatchActivity.this, "网络异常!", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                            netWorkFlag = false;
//                        }
//                    }
//                }).start();
//                if (netWorkFlag) {
//
//                }
//                netWorkFlag = true
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        dealRegisterNetWork(Utility.createTWUser(userData));
                    }
                }).start();

//                if (netWorkFlag && registerFlag) {
//                    DbUtil.insertNewUser(userData);
//                }
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    LogUtil.e(Utility.WATCH_TAG, "异常!");
                }

                dealRegisterAfters(userData);
            }
            break;
        }
    }

    private void configChildControls(){
        childToolBar = (RelativeLayout)findViewById(R.id.child_toolbar);
        childUserIcon = (CircleImageView) findViewById(R.id.child_user_icon);
        childUserNameView = (TextView)findViewById(R.id.child_user_name);
        appTimeInfoView = (RecyclerView)findViewById(R.id.use_info_list);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh_app_time_info_child);
        aboutChildView = (TextView)findViewById(R.id.about_child_user);
        netConnBar = (ProgressBar)findViewById(R.id.child_net_work_progressbar);
    }

    private void configParentControls(){
        parentToolBar = (RelativeLayout)findViewById(R.id.parent_toolbar);
        parentUserIcon = (CircleImageView)findViewById(R.id.parent_user_icon);
        parentSettingView = (ImageView)findViewById(R.id.parent_setting);
        parentUserNameView = (TextView)findViewById(R.id.parent_user_name);
        appTimeInfoView = (RecyclerView)findViewById(R.id.receive_info_list);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh_app_time_info_parent);
        aboutParentView = (TextView)findViewById(R.id.about_parent_user);
        netConnBar = (ProgressBar)findViewById(R.id.parent_net_work_progressbar);
        parentNotifyView = (TextView)findViewById(R.id.parent_notify);
    }

    private void initialChildControls(final Intent intent) {
        String gender = intent.getStringExtra("Gender");
        childUserIcon.setImageResource(gender.equals("male") ? R.drawable.child_boy : R.drawable.child_girl);
        childToolBar.setBackgroundColor(
                gender.equals("male") ? getResources().getColor(R.color.childBoyToolBar) :
                        getResources().getColor(R.color.childGirlToolBar)
        );
        String childUserName = intent.getExtras().getString("account");
        childUserNameView.setText(childUserName == null ? "" : childUserName);
        //
        appTimeInfosList = new ArrayList<>();
        appTimeInfoAdapter = new AppTimeInfoAdapter(appTimeInfosList);
        ViewUtil.configRecyclerViewAdapter(this, appTimeInfoView, appTimeInfoAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        aboutChildView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent specificIntent = new Intent(WatchActivity.this, SpecificInfoActivity.class);
                //Bundle bundle = new Bundle();
                //TW_Users twUser = new TW_Users();
                specificIntent.putExtra("account", intent.getStringExtra("account"));
                startActivity(specificIntent);
            }
        });
    }

    private void initialChildControls(final TW_Users twUser) {
        String gender = twUser.getGender();
        childUserIcon.setImageResource(gender.equals("male") ? R.drawable.child_boy : R.drawable.child_girl);
        childToolBar.setBackgroundColor(
                gender.equals("male") ? getResources().getColor(R.color.childBoyToolBar) :
                        getResources().getColor(R.color.childGirlToolBar)
        );
        String childUserName = twUser.getName();//intent.getExtras().getString("account");
        childUserNameView.setText(childUserName == null ? "" : childUserName);
        //
        appTimeInfosList = new ArrayList<>();
        appTimeInfoAdapter = new AppTimeInfoAdapter(appTimeInfosList);
        ViewUtil.configRecyclerViewAdapter(this, appTimeInfoView, appTimeInfoAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        aboutChildView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent specificIntent = new Intent(WatchActivity.this, SpecificInfoActivity.class);
                //Bundle bundle = new Bundle();
                //TW_Users twUser = new TW_Users();
                specificIntent.putExtra("account", twUser.getName());
                startActivity(specificIntent);
            }
        });
    }

//    private void initialChildControls(String gender) {
//        childUserIcon.setImageResource(gender.equals("male") ? R.drawable.child_boy : R.drawable.child_girl);
//        childToolBar.setBackgroundColor(
//                gender.equals("male") ? getResources().getColor(R.color.childBoyToolBar) :
//                        getResources().getColor(R.color.childGirlToolBar)
//        );
//        String childUserName = intent.getExtras().getString("account");
//        childUserNameView.setText(childUserName == null ? "" : childUserName);
//        //
//        appTimeInfosList = new ArrayList<>();
//        appTimeInfoAdapter = new AppTimeInfoAdapter(appTimeInfosList);
//        ViewUtil.configRecyclerViewAdapter(this, appTimeInfoView, appTimeInfoAdapter);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshData();
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
//    }

    private void initialParentControls(final Intent intent) {
        String gender = intent.getStringExtra("Gender");
        parentUserIcon.setImageResource(gender.equals("male") ? R.drawable.parent_father : R.drawable.parent_mother);
        parentToolBar.setBackgroundColor(
                gender.equals("male") ? getResources().getColor(R.color.parentFatherToolBar) :
                        getResources().getColor(R.color.parentMotherToolBar)
        );
        String parentUserName = intent.getExtras().getString("account");
        parentUserNameView.setText(parentUserName == null ? "" : parentUserName);
        //
        appTimeInfosList = new ArrayList<>();
        appTimeInfoAdapter = new AppTimeInfoAdapter(appTimeInfosList);
        ViewUtil.configRecyclerViewAdapter(this, appTimeInfoView, appTimeInfoAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        aboutParentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent specificIntent = new Intent(WatchActivity.this, SpecificInfoActivity.class);
                //Bundle bundle = new Bundle();
                //TW_Users twUser = new TW_Users();
                specificIntent.putExtra("account", intent.getStringExtra("account"));
                startActivity(specificIntent);
            }
        });
        parentSettingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tranIntent = new Intent(WatchActivity.this, AssociationActivity.class);
                Bundle userData = intent.getExtras();
                TW_Users twUser = (TW_Users) userData.getSerializable("loginUser");
                tranIntent.putExtra("parentId", twUser.getUser_id());
                startActivity(tranIntent);
            }
        });
    }

    private void initialParentControls(final TW_Users twUser) {
        String gender = twUser.getGender();//intent.getStringExtra("Gender");
        parentUserIcon.setImageResource(gender.equals("male") ? R.drawable.parent_father : R.drawable.parent_mother);
        parentToolBar.setBackgroundColor(
                gender.equals("male") ? getResources().getColor(R.color.parentFatherToolBar) :
                        getResources().getColor(R.color.parentMotherToolBar)
        );
        String parentUserName = twUser.getName();//intent.getExtras().getString("password");
        parentUserNameView.setText(parentUserName == null ? "" : parentUserName);
        //
        appTimeInfosList = new ArrayList<>();
        appTimeInfoAdapter = new AppTimeInfoAdapter(appTimeInfosList);
        ViewUtil.configRecyclerViewAdapter(this, appTimeInfoView, appTimeInfoAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //refreshData();
                requestData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        aboutParentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent specificIntent = new Intent(WatchActivity.this, SpecificInfoActivity.class);
                //Bundle bundle = new Bundle();
                //TW_Users twUser = new TW_Users();
                specificIntent.putExtra("account", twUser.getName());
                startActivity(specificIntent);
            }
        });
        parentSettingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tranIntent = new Intent(WatchActivity.this, AssociationActivity.class);
                tranIntent.putExtra("parentId", twUser.getUser_id());
                LogUtil.d(Utility.WATCH_TAG, twUser.getUser_id());
                startActivity(tranIntent);
            }
        });
    }

//    private void initialParentControls(String gender) {
//        parentUserIcon.setImageResource(gender.equals("male") ? R.drawable.parent_father : R.drawable.parent_mother);
//        parentToolBar.setBackgroundColor(
//                gender.equals("male") ? getResources().getColor(R.color.parentFatherToolBar) :
//                        getResources().getColor(R.color.parentMotherToolBar)
//        );
//        String parentUserName = intent.getExtras().getString("account");
//        parentUserNameView.setText(parentUserName == null ? "" : parentUserName);
//        //
//        appTimeInfosList = new ArrayList<>();
//        appTimeInfoAdapter = new AppTimeInfoAdapter(appTimeInfosList);
//        ViewUtil.configRecyclerViewAdapter(this, appTimeInfoView, appTimeInfoAdapter);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshData();
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
//    }

    private void refreshData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (appTimeInfosList != null) {
                            appTimeInfosList.clear();
                        }
                        doAfterRequestSuccess();
                    }
                });
            }
        }).start();
    }

    private void requestData() {
        new Thread(createClientParentRunnable()).start();
    }

    private void dealRegisterAfters(Bundle userData) {
//        switch (backNews) {
//            case "注册成功!":
//                Toast.makeText(this, backNews, Toast.LENGTH_SHORT).show();
//                break;
//            case "注册失败!":
//                Toast.makeText(this, backNews, Toast.LENGTH_SHORT).show();
//            default:
//                Toast.makeText(this, "异常返回!", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(this, RegisterActivity.class));
//                break;
//        }
        if (registerFlag && netWorkFlag) {
            Toast.makeText(this, backNews, Toast.LENGTH_SHORT).show();
            DbUtil.insertNewUser(userData);
        } else if (!netWorkFlag) {
            Toast.makeText(this, "当前网络无连接!", Toast.LENGTH_SHORT).show();
        } else {
            LogUtil.d(Utility.WATCH_TAG, "注册异常!!!");
            Toast.makeText(this, backNews, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, RegisterActivity.class));
        }

    }

    private void dealRegisterNetWork(final TWUser twUser) {
        if (NetWorkUtil.isNetworkAvailable(this)) {
            //netConnBar.setProgress(100);
//        if (netConnBar.getVisibility() == View.GONE) {
//            netConnBar.setVisibility(View.VISIBLE);
//        }
            netWorkFlag = true;
            String userJson = new Gson().toJson(twUser);
            LogUtil.d(Utility.WATCH_TAG, userJson);
////        new Thread(new Runnable() {
////            @Override
////            public void run() {
//                try {
//                    //backNews = Utility.handleRegister(userJson);
//                    handleRegister(userJson);
////                    runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
//                            if (netConnBar.getVisibility() == View.VISIBLE) {
//                                netConnBar.setVisibility(View.GONE);
//                            }
//                            dealRegisterAfters();
////                        }
////                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    LogUtil.e(Utility.WATCH_TAG, "注册出错!!!");
//                }
////            }
////        }).start();
            handleRegister(userJson);
//        if (netConnBar.getVisibility() == View.VISIBLE) {
//            netConnBar.setVisibility(View.GONE);
//        }
        } else {
            netWorkFlag = false;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(WatchActivity.this, "网络不可用!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void handleRegister(String userJson) {
        try {
            backNews = HttpUtil.sendRegisterJSONRequest(userJson);
            LogUtil.d(Utility.WATCH_TAG, backNews);
            registerFlag = backNews != null && backNews.equals("注册成功!");
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.e(Utility.WATCH_TAG, "注册错误!");
            //return false;
            //netWorkFlag = false;
            registerFlag = false;
        }
        //netWorkFlag = true;
        //registerFlag = true;
        //return true;
    }

    private void clearAppTimeInfoListView() {
        if (appTimeInfosList != null && appTimeInfosList.size() > 0) {
            appTimeInfosList.clear();
        }
        appInfoAdapter.notifyDataSetChanged();
    }

    private SocketUtil.DemoClientRunnable createClientParentRunnable() {
//        Socket socket = null;
//        try {
//            socket = SocketUtil.createClientSocket(10000, 30000);
//        } catch (IOException e) {
//            e.printStackTrace();
//            LogUtil.e(Utility.WATCH_TAG, "获取套接字出错!");
//        }
        return new SocketUtil.DemoClientRunnable(new SocketUtil.DemoClientRunnable.DealMethods() {
            @Override
            public boolean connect() throws IOException {
                socket.connect(SocketUtil.getInetSocketAddress(), 10000);
                socket.setSoTimeout(20000);
                return true;
            }

            @Override
            public boolean receive() throws IOException {
                InputStream is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = "";
                StringBuilder builder = new StringBuilder();
                builder.append(line);
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }
                backNews = builder.toString();
                dealChildMsgNetWork();
                br.close();
                //is.close();
                return true;
            }

            @Override
            public boolean send() throws IOException {
                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(os)));
                Intent intent = getIntent();
                String data = null;
                if (intent != null) {
                    Bundle userData = intent.getExtras();
                    data = userData.getString("phone_num");
                    if (data == null || data.equals("")) {
                        TW_Users tw_users = (TW_Users) userData.getSerializable("loginUser");
                        data = String.valueOf(tw_users.getPhone_num());
                    }
                }
                pw.write(data);
                pw.flush();
                os.flush();
                pw.close();
                //os.close();
                return true;
            }

            @Override
            public boolean close() throws IOException {
                socket.shutdownInput();
                socket.shutdownOutput();
                socket.getInputStream().close();
                socket.getOutputStream().close();
                socket.close();
                return true;
            }
        });
    }

    private void dealChildMsgNetWork() {
        if (NetWorkUtil.isNetworkAvailable(this)) {
            //netConnBar.setProgress(100);
//        if (netConnBar.getVisibility() == View.GONE) {
//            netConnBar.setVisibility(View.VISIBLE);
//        }
            netWorkFlag = true;
            if (!JsonUtil.isUserJSONValidByGson(backNews)) {
                //Toast.makeText(this, "解析异常!", Toast.LENGTH_SHORT).show();
                LogUtil.d(Utility.WATCH_TAG, "解析异常!");
                clientHandler.sendEmptyMessage(NO_SUCH_CHILD);
                return;
            }
            ChildMsg childMsg = new Gson().fromJson(backNews, ChildMsg.class);
            LogUtil.d(Utility.WATCH_TAG, childMsg.toString());
            Message msg = new Message();
            msg.what = GET_CHILD_SUCCESS;
            msg.obj = childMsg;
            clientHandler.sendMessage(msg);
        } else {
            netWorkFlag = false;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(WatchActivity.this, "网络不可用!", Toast.LENGTH_SHORT).show();
                }
            });
            Message msg = new Message();
            msg.what = NETWORK_ERROR;
            clientHandler.sendMessage(msg);
        }
    }

    private void transferAppTimeInfoList(List<AppTimeInfo> appTimeInfos) {
        Iterator<AppTimeInfo> iterator = appTimeInfos.iterator();
        while (iterator.hasNext()) {
            AppTimeInfo appTimeInfo = iterator.next();
            App_Time_Infos app_time_info = new App_Time_Infos();
            app_time_info.setApp_name(appTimeInfo.getAppName());
            app_time_info.setFirst_open_time(appTimeInfo.getFirstOpenTime());
            app_time_info.setLast_close_time(appTimeInfo.getLastCloseTime());
            app_time_info.setWhich_day(appTimeInfo.getWhichDay());
            app_time_info.setUsing_minutes(appTimeInfo.getUsingMinutes());
            app_time_info.setOpen_times(appTimeInfo.getOpenTimes());
            //app_time_info.setApp_infos();
            appTimeInfosList.add(app_time_info);
        }
    }
}
