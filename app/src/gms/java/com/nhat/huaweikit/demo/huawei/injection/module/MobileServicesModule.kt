package com.nhat.huaweikit.demo.huawei.injection.module

import com.nhat.huaweikit.demo.gms_services.GoogleAccountServices
import com.nhat.huaweikit.demo.gms_services.GoogleLocationServices
import com.nhat.huaweikit.demo.gms_services.GoogleMapServices
import com.nhat.huaweikit.demo.nd_services.AccountServices
import com.nhat.huaweikit.demo.nd_services.LocationServices
import com.nhat.huaweikit.demo.nd_services.MapServices
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
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