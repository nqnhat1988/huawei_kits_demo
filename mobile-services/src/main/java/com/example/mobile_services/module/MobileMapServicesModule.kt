package com.example.mobile_services.module

import com.nhat.huaweikit.demo.gms_services.GoogleMapServices
import com.nhat.huaweikit.demo.huawei_services.HuaweiMapServices
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
abstract class MobileMapServicesModule {
    @Module
    companion object {
        @Provides
        @Named("HuaweiMapService")
        @JvmStatic
        fun provideHuaweiMapService(): HuaweiMapServices = HuaweiMapServices()

        @Provides
        @Named("GoogleMapService")
        @JvmStatic
        fun provideGoogleMapService(): GoogleMapServices = GoogleMapServices()
    }
}