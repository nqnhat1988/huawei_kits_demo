package com.example.mobile_services.module

import com.nhat.huaweikit.demo.gms_services.GoogleLocationServices
import com.nhat.huaweikit.demo.huawei_services.HuaweiLocationServices
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
abstract class MobileLocationServicesModule {
    @Module
    companion object {
        @Provides
        @Named("HuaweiLocationService")
        @JvmStatic
        fun provideHuaweiLocationService(): HuaweiLocationServices = HuaweiLocationServices()

        @Provides
        @Named("GoogleLocationService")
        @JvmStatic
        fun provideGoogleLocationService(): GoogleLocationServices = GoogleLocationServices()
    }
}