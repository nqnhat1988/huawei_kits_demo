package com.nhat.huaweikit.demo.huawei

import android.app.Application
import android.os.Bundle
import com.huawei.hms.analytics.HiAnalytics
import com.huawei.hms.analytics.HiAnalyticsInstance
import com.huawei.hms.analytics.HiAnalyticsTools
import java.text.SimpleDateFormat
import java.util.*


class App : Application() {
    private lateinit var instance: HiAnalyticsInstance
    override fun onCreate() {
        super.onCreate()
        HiAnalyticsTools.enableLog();
        instance = HiAnalytics.getInstance(this)
        instance.setAnalyticsEnabled(true)
        instance.regHmsSvcEvent()

        // Initialize parameters.
        val bundle = Bundle()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        bundle.putString("APP_START", sdf.format(Date()))
        instance.onEvent("APP_START", bundle);
    }

    override fun onTerminate() {
        super.onTerminate()
        instance.unRegHmsSvcEvent();
    }
}