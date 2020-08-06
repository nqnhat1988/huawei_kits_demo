package com.nhat.huaweikit.demo.huawei_services

import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.aaid.HmsInstanceId

object NDPush {
    val TAG = NDPush::class.simpleName
    fun getPushToken(
        context: Context,
        _text: MutableLiveData<String>
    ) {
        object : Thread() {
            override fun run() {
                try {
                    val appId =
                        AGConnectServicesConfig.fromContext(context)
                            .getString("client/app_id")
                    val pushToken = HmsInstanceId.getInstance(context).getToken(appId, "HCM")
                    if (!TextUtils.isEmpty(pushToken)) {
                        _text.postValue(pushToken)
                        Log.i(TAG, "get token:$pushToken")
                    }
                } catch (e: Exception) {
                    Log.i(TAG, "getToken failed, $e")
                }
            }
        }.start()
    }
}