package com.example.mobile_services.services

import android.os.Bundle
import android.view.ViewGroup
import com.nhat.huaweikit.demo.gms_services.GoogleMapServices
import com.nhat.huaweikit.demo.huawei_services.HuaweiMapServices
import com.nhat.icore_services.common.MobileServicesEnum
import com.nhat.icore_services.common.SafeCheck
import com.nhat.icore_services.services.BaseServices
import com.nhat.icore_services.services.MapServices
import javax.inject.Inject

class MobileMapServices @Inject constructor(
    huaweiMapServices: HuaweiMapServices,
    googleMapServices: GoogleMapServices,
    safeCheck: SafeCheck
) : MapServices, BaseServices {

    override val mobileServicesEnum = safeCheck.mobileServicesEnum

    private val mapServices = when (mobileServicesEnum) {
        MobileServicesEnum.HMS -> huaweiMapServices
        MobileServicesEnum.GMS -> googleMapServices
    }

    override val mapBundleId: String
        get() = mapServices.mapBundleId

    override fun init(container: ViewGroup, savedInstanceState: Bundle?) {
        mapServices.init(container, savedInstanceState)
    }

    override fun onStart() {
        mapServices.onStart()
    }

    override fun onStop() {
        mapServices.onStop()
    }

    override fun onResume() {
        mapServices.onResume()
    }

    override fun onPause() {
        mapServices.onPause()
    }

    override fun onDestroy() {
        mapServices.onDestroy()
    }

}