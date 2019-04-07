package com.apps.haitao.twatcher.twclient.activities.twutil;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Telephony;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public final class SMSUtil {

    private static EventHandler eventHandler;

    public interface SmsDealer {
        void sendSuccess(); //验证码发送成功

        void sendFail();    //验证码发送失败

        void passRight();   //验证成功且正确

        void passWrong();   //验证错误或失败
    }

//    public static void initSMSSDK(Context context, String appKey, String appSecret) {
//
//    }

    public static void assignEventHandler(final SmsDealer dealer) {
        eventHandler =  new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                new Handler(Looper.getMainLooper(), new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        int event = msg.arg1;
                        int result = msg.arg2;
                        Object data = msg.obj;
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                // TODO 处理成功得到验证码的结果
                                // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                                dealer.sendSuccess();
                            } else {
                                // TODO 处理错误的结果
                                dealer.sendFail();
                                ((Throwable) data).printStackTrace();
                            }
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                // TODO 处理验证码验证通过的结果
                                dealer.passRight();
                            } else {
                                // TODO 处理错误的结果
                                dealer.passWrong();
                                ((Throwable) data).printStackTrace();
                            }
                        }
                        // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                        return false;
                    }
                }).sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }

    public static void requestVerificationCode(String countryCode,
                                               String phoneNum) {
        SMSSDK.getVerificationCode(countryCode, phoneNum);
    }

    public static void submitVerificationCode(String countryCode,
                                              String phoneNum,
                                              String inputCode) {
        SMSSDK.submitVerificationCode(countryCode, phoneNum, inputCode);
    }

    //此方法必须在活动的Destroy方法调用
    public static void resignEventHandler() {
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
