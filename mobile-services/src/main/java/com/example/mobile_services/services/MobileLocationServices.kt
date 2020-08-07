package com.example.mobile_services.services

import android.app.Activity
import com.nhat.huaweikit.demo.gms_services.GoogleLocationServices
import com.nhat.huaweikit.demo.huawei_services.HuaweiLocationServices
import com.nhat.icore_services.common.LocationData
import com.nhat.icore_services.common.MobileServicesEnum
import com.nhat.icore_services.common.SafeCheck
import com.nhat.icore_services.services.BaseServices
import com.nhat.icore_services.services.LocationServices
import javax.inject.Inject

class MobileLocationServices @Inject constructor(
    huaweiLocationServices: HuaweiLocationServices,
    googleLocationServices: GoogleLocationServices,
    safeCheck: SafeCheck
) : LocationServices, BaseServices {

    override val mobileServicesEnum: MobileServicesEnum = safeCheck.mobileServicesEnum

    private val locationServices = when (mobileServicesEnum) {
        MobileServicesEnum.HMS -> huaweiLocationServices
        MobileServicesEnum.GMS -> googleLocationServices
    }

    override fun init(activity: Activity) {
        locationServices.init(activity)
    }

    override fun requestLocationUpdatesWithCallback(callback: (location: LocationData) -> Unit) {
        locationServices.requestLocationUpdatesWithCallback(callback)
    }

    override fun removeLocationUpdatesWithCallback() {
        locationServices.removeLocationUpdatesWithCallback()
    }

    override fun requestGeoFenceCallback() {
        locationServices.requestGeoFenceCallback()
    }

    override fun removeWithID() {
        locationServices.removeWithID()
    }
}