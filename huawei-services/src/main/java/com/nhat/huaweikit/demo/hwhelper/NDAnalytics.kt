package com.nhat.huaweikit.demo.hwhelper

import android.content.Context
import android.os.Bundle
import com.huawei.hms.analytics.HiAnalytics
import com.huawei.hms.analytics.HiAnalyticsInstance
import com.huawei.hms.analytics.HiAnalyticsTools
import java.text.SimpleDateFormat
import java.util.*

class NDAnalytics(context: Context) {
    private var instance: HiAnalyticsInstance

    init {
        HiAnalyticsTools.enableLog()
        instance = HiAnalytics.getInstance(context).apply {
            setAnalyticsEnabled(true)
            regHmsSvcEvent()

            // Initialize parameters.
            val bundle = Bundle()
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            bundle.putString("APP_START", sdf.format(Date()))
            onEvent("APP_START", bundle)
        }
    }

    fun start() {
        with(instance) {
            setAnalyticsEnabled(true)
            regHmsSvcEvent()

            // Initialize parameters.
            val bundle = Bundle()
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
            bundle.putString("APP_START", sdf.format(Date()))
            onEvent("APP_START", bundle)
        }
    }

    fun stop() {
        instance.unRegHmsSvcEvent()
    }
}