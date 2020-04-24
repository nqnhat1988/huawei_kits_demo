package com.nhat.huaweikit.demo.hwhelper;

import android.util.Log;

import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;

public class MyPushService extends HmsMessageService {
    private static final String TAG = "PushDemoLog";

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.i(TAG, "receive token:" + token);
    }


    /**
     * RNFirebase
     * onMessageReceived
     * onMessageCalled or Opended
     *
     * @param var1
     */
    @Override
    public void onMessageReceived(RemoteMessage var1) {
        //push an message to RN project we receive a message var1

        //create a custom notification with handling function when user tap on that notification

        //
    }
}
