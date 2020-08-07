package com.example.mobile_services.module

import com.example.mobile_services.services.MobileAccountServices
import com.example.mobile_services.services.MobileLocationServices
import com.example.mobile_services.services.MobileMapServices
import com.nhat.huaweikit.demo.gms_services.GoogleAccountServices
import com.nhat.huaweikit.demo.gms_services.GoogleLocationServices
import com.nhat.huaweikit.demo.gms_services.GoogleMapServices
import com.nhat.huaweikit.demo.huawei_services.HuaweiAccountServices
import com.nhat.huaweikit.demo.huawei_services.HuaweiLocationServices
import com.nhat.huaweikit.demo.huawei_services.HuaweiMapServices
import com.nhat.icore_services.common.SafeCheck
import com.nhat.icore_services.services.AccountServices
import com.nhat.icore_services.services.LocationServices
import com.nhat.icore_services.services.MapServices
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        MobileSafeCheckModule::class,
        MobileAccountServicesModule::class,
        MobileLocationServicesModule::class,
        MobileMapServicesModule::class
    ]
)
abstract class MobileServicesModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideMobileAccountService(
            huaweiAccountServices: HuaweiAccountServices,
            googleAccountServices: GoogleAccountServices,
            safeCheck: SafeCheck
        ) = MobileAccountServices(
            huaweiAccountServices,
            googleAccountServices,
            safeCheck
        )

        @Provides
        @JvmStatic
        fun provideMobileLocationService(
            huaweiLocationServices: HuaweiLocationServices,
            googleLocationServices: GoogleLocationServices,
            safeCheck: SafeCheck
        ) = MobileLocationServices(
            huaweiLocationServices,
            googleLocationServices,
            safeCheck
        )

        @Provides
        @JvmStatic
        fun provideMobileMapService(
            huaweiMapServices: HuaweiMapServices,
            googleMapServices: GoogleMapServices,
            safeCheck: SafeCheck
        ) = MobileMapServices(
            huaweiMapServices,
            googleMapServices,
            safeCheck
        )
    }

    @Binds
    abstract fun bindAccountService(accountServices: MobileAccountServices): AccountServices

    @Binds
    abstract fun bindLocationService(locationServices: MobileLocationServices): LocationServices

    @Binds
    abstract fun bindMapService(mapServices: MobileMapServices): MapServices
}