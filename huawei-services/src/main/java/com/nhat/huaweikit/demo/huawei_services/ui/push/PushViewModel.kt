package com.nhat.huaweikit.demo.huawei_services.ui.push

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nhat.huaweikit.demo.huawei_services.NDPush

class PushViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is push Fragment"
    }
    val text: LiveData<String> = _text

    fun getPushToken(context: Context) {
        NDPush.getPushToken(context, _text)
    }
}