package com.nhat.huaweikit.demo.huawei.ui.push

import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.aaid.HmsInstanceId

class PushViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is push Fragment"
    }
    val text: LiveData<String> = _text
    lateinit var context: Context

    fun getPushToken() {
        object : Thread() {
            override fun run() {
                try {
                    val appId =
                        AGConnectServicesConfig.fromContext(context)
                            .getString("client/app_id")
                    val pushToken = HmsInstanceId.getInstance(context).getToken(appId, "HCM")
                    if (!TextUtils.isEmpty(pushToken)) {
                        _text.postValue(pushToken)
                        Log.i(PushFragment.TAG, "get token:$pushToken")
                    }
                } catch (e: Exception) {
                    Log.i(PushFragment.TAG, "getToken failed, $e")
                }
            }
        }.start()
    }
}