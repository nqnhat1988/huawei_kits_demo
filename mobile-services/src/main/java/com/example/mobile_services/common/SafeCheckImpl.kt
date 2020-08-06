package com.example.mobile_services.common

import android.content.Context
import com.huawei.hms.api.ConnectionResult
import com.huawei.hms.api.HuaweiApiAvailability
import com.nhat.icore_services.common.MobileServicesEnum
import com.nhat.icore_services.common.SafeCheck
import javax.inject.Inject

class SafeCheckImpl @Inject constructor(
    val context: Context
) : SafeCheck {
    private val isHMSAvailable = HuaweiApiAvailability.getInstance()
        .isHuaweiMobileServicesAvailable(context) == ConnectionResult.SUCCESS

    override val mobileServicesEnum =
        if (isHMSAvailable) MobileServicesEnum.HMS else MobileServicesEnum.GMS
}