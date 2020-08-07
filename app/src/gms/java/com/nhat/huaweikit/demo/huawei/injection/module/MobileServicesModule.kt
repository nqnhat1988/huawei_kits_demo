package com.nhat.huaweikit.demo.huawei.injection.module

import android.content.Context
import com.example.mobile_services.common.SafeCheckImpl
import com.example.mobile_services.module.MobileSafeCheckModule
import com.nhat.huaweikit.demo.gms_services.GoogleAccountServices
import com.nhat.huaweikit.demo.gms_services.GoogleLocationServices
import com.nhat.huaweikit.demo.gms_services.GoogleMapServices
import com.nhat.icore_services.services.AccountServices
import com.nhat.icore_services.services.LocationServices
import com.nhat.icore_services.services.MapServices
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        MobileSafeCheckModule::class
    ]
)
abstract class MobileServicesModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideAccountService(): GoogleAccountServices = GoogleAccountServices()

        @Provides
        @JvmStatic
        fun provideLocationService(): GoogleLocationServices = GoogleLocationServices()

        @Provides
        @JvmStatic
        fun provideMapService(): GoogleMapServices = GoogleMapServices()
    }

    @Binds
    abstract fun bindAccountService(accountServices: GoogleAccountServices): AccountServices

    @Binds
    abstract fun bindLocationService(locationServices: GoogleLocationServices): LocationServices

    @Binds
    abstract fun bindMapService(mapServices: GoogleMapServices): MapServices
}