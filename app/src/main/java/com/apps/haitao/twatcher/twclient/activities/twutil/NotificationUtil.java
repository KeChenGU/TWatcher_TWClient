package com.apps.haitao.twatcher.twclient.activities.twutil;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.apps.haitao.twatcher.twclient.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static android.content.Context.NOTIFICATION_SERVICE;

public final class NotificationUtil {

    public static final int DEFAULT_REQUEST_CODE = 0;

    public static final int DEFAULT_FLAG = 0;

    private static NotificationManager manager;//(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);

    private static final String CHANEL_ID = "notify_one";

    private static final String CHANEL_NAME = "notify_message";


    public static void initNotificationManager(Context context) {
        manager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
    }

    public static void showCreatedNotification(Context context,
                                               Class<?> cls,
                                               String title,
                                               String content,
                                               int smallIconId, //int largeIconId
                                               int showId) {
        Intent intent = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, DEFAULT_REQUEST_CODE, intent, DEFAULT_FLAG);
        ///*NotificationManager*/ manager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        if (!NotificationManagerCompat.from(context).areNotificationsEnabled()) {
            Toast.makeText(context, "请先打开应用通知权限！", Toast.LENGTH_SHORT).show();
            toNotificationSetting(context);
            return;
        }
        //安卓8.0 Android 8.0 Ou 版本及以上需要设置通知频道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel
                    = new NotificationChannel(CHANEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(notificationChannel);
        }
        Notification notification = new NotificationCompat.Builder(context, CHANEL_ID) //android O 版本使用的Builder方法 老方法(只有一个Context参数的)已过时
                .setContentTitle(title)
                .setContentText(content)
                //.setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL)  //设置通知方式
                .setSmallIcon(smallIconId)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), smallIconId))
                //在点击通知后自动取消系统显示图标
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                //.setFullScreenIntent(pendingIntent, true)
                .setShowWhen(true)  //显示时间
                .setPriority(NotificationCompat.PRIORITY_MAX)   //设置最高优先级
                .build();
        manager.notify(showId, notification);
        //return notification;
    }

    public static void cancelShowedNotification(int showId) {
        manager.cancel(showId);
    }

    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";

    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    /**
     * 通过反射获取通知的开关状态
     * @param context 上下文
     * @return boolean
     */
    @TargetApi(19)
    public static boolean isNotificationEnabled(Context context){

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;
        Class appOpsClass = null; /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            int value = (int)opPostNotificationValue.get(Integer.class);
            return ((int)checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 跳转设置页面 去设置通知权限
     * @param context
     */
    public static void toNotificationSetting(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = new Intent();
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", context.getApplicationContext().getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
            context.startActivity(intent);
        } else if (android.os.Build.VERSION.SDK_INT == android.os.Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + context.getApplicationContext().getPackageName()));
            context.startActivity(intent);
        }
    }



    class WindowHeadToast implements View.OnTouchListener {

        private Context mContext;
        private View headToastView;
        private LinearLayout linearLayout;
        private final static int ANIM_DURATION = 600;
        private final static int ANIM_DISMISSS_DURATION = 2000;
        private final static int ANIM_CLOSE = 20;
        private android.os.Handler mHander = new android.os.Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == ANIM_CLOSE) {
                    animDismiss();
                }
            }
        };
        private WindowManager wm;
        private int downX;
        private int downY;

        public WindowHeadToast(Context context) {
            mContext = context;
        }

        public void showCustomToast() {
            initHeadToastView();
            setHeadToastViewAnim();
            // 延迟2s后关闭
            mHander.sendEmptyMessageDelayed(ANIM_CLOSE, ANIM_DISMISSS_DURATION);
        }

        private void setHeadToastViewAnim() {
            ObjectAnimator animator = ObjectAnimator.ofFloat(linearLayout, "translationY", -700, 0);
            animator.setDuration(ANIM_DURATION);
            animator.start();
        }

        private void animDismiss() {
            if (linearLayout == null || linearLayout.getParent() == null) {
                return;
            }
            ObjectAnimator animator = ObjectAnimator.ofFloat(linearLayout, "translationY", 0, -700);
            animator.setDuration(ANIM_DURATION);
            animator.start();
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    super.onAnimationCancel(animation);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dismiss();
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    super.onAnimationRepeat(animation);
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                }

                @Override
                public void onAnimationPause(Animator animation) {
                    super.onAnimationPause(animation);
                }

                @Override
                public void onAnimationResume(Animator animation) {
                    super.onAnimationResume(animation);
                }
            });
        }

        /**
         * 移除HeaderToast  (一定要在动画结束的时候移除,不然下次进来的时候由于wm里边已经有控件了，所以会导致卡死)
         */
        private void dismiss() {
            if (null != linearLayout && null != linearLayout.getParent()) {
                wm.removeView(linearLayout);
            }
        }

        public void initHeadToastView() {
            //准备Window要添加的View
            linearLayout = new LinearLayout(mContext);
            final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(layoutParams);
            //headToastView = View.inflate(mContext, R.layout.header_toast, null);
            // 为headToastView设置Touch事件
            headToastView.setOnTouchListener(this);
            linearLayout.addView(headToastView);
            // 定义WindowManager 并且将View添加到WindowManagar中去
            wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            WindowManager.LayoutParams wm_params = new WindowManager.LayoutParams();
            wm_params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_FULLSCREEN
                    | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
            wm_params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
            wm_params.x = 0;
            wm_params.y = 100;
            wm_params.format = -3;  // 会影响Toast中的布局消失的时候父控件和子控件消失的时机不一致，比如设置为-1之后就会不同步
            wm_params.alpha = 1f;
            wm.addView(linearLayout, wm_params);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = (int) event.getRawX();
                    downY = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int currentX = (int) event.getRawX();
                    int currentY = (int) event.getRawY();
                    if (Math.abs(currentX - downX) > 50 || Math.abs(currentY - downY) > 40) {
                        animDismiss();
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    }


}
