package com.nhat.huaweikit.demo.huawei_services.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Finding your location"
    }
    val text: LiveData<String> = _text
}