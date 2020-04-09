package com.nhat.huaweikit.demo.huawei.ui.health

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class HealthViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is login Fragment"
    }
    val text: LiveData<String> = _text
    val isStart: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = false
    }
}
