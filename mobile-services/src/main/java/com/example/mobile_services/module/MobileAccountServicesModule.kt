package com.example.mobile_services.module

import com.nhat.huaweikit.demo.gms_services.GoogleAccountServices
import com.nhat.huaweikit.demo.hwhelper.HuaweiAccountServices
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
abstract class MobileAccountServicesModule {
    @Module
    companion object {
        @Provides
        @Named("HuaweiAccountService")
        @JvmStatic
        fun provideHuaweiAccountService(): HuaweiAccountServices = HuaweiAccountServices()

        @Provides
        @Named("GoogleAccountService")
        @JvmStatic
        fun provideGoogleAccountService(): GoogleAccountServices = GoogleAccountServices()
    }
}