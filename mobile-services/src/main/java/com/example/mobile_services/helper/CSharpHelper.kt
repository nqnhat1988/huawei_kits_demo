package com.example.mobile_services.helper

import android.content.Context
import com.example.mobile_services.common.SafeCheckImpl
import com.nhat.huaweikit.demo.gms_services.GoogleMapServices
import com.nhat.huaweikit.demo.huawei_services.HuaweiMapServices
import com.nhat.icore_services.common.MobileServicesEnum
import com.nhat.icore_services.services.MapServices

object CSharpHelper {
    fun isSupportHMS(context: Context): MobileServicesEnum {
        val safeCheck = SafeCheckImpl(context)
        return safeCheck.mobileServicesEnum
    }

    fun provideMapServices(enum: MobileServicesEnum): MapServices {
        return when (enum) {
            MobileServicesEnum.HMS -> HuaweiMapServices()
            else -> GoogleMapServices()
        }
    }
}