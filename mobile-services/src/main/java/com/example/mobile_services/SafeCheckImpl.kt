package com.example.mobile_services

import android.content.Context
import com.huawei.hms.api.ConnectionResult
import com.huawei.hms.api.HuaweiApiAvailability
import com.nhat.huaweikit.demo.nd_services.MobileServicesEnum
import com.nhat.huaweikit.demo.nd_services.SafeCheck
import javax.inject.Inject

class SafeCheckImpl @Inject constructor(val context: Context) : SafeCheck {
    override fun getMobileServices(): MobileServicesEnum {
        return if (HuaweiApiAvailability.getInstance()
                .isHuaweiMobileServicesAvailable(context) == ConnectionResult.SUCCESS
        ) {
            MobileServicesEnum.HMS
        } else MobileServicesEnum.GMS
    }
}