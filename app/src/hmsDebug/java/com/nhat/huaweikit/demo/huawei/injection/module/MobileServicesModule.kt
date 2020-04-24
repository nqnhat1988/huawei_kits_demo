package com.nhat.huaweikit.demo.huawei.injection.module

import com.nhat.huaweikit.demo.gms_services.GoogleAccountServices
import com.nhat.huaweikit.demo.hwhelper.HuaweiLocationServices
import com.nhat.huaweikit.demo.nd_services.AccountServices
import com.nhat.huaweikit.demo.nd_services.LocationServices
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class MobileServicesModule {
    //    @Module
//    companion object {
//        @Provides
//        @JvmStatic
//        fun provideAccountService() : HuaweiAccountServices = HuaweiAccountServices()
//
//        @Provides
//        @JvmStatic
//        fun provideLocationService() : HuaweiLocationServices = HuaweiLocationServices()
//    }
//    @Binds
//    abstract fun bindAccountService(accountServices: HuaweiAccountServices): AccountServices
//    @Binds
//    abstract fun bindLocationService(locationServices: HuaweiLocationServices): LocationServices
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideAccountService(): GoogleAccountServices = GoogleAccountServices()

        @Provides
        @JvmStatic
        fun provideLocationService(): HuaweiLocationServices = HuaweiLocationServices()
    }

    @Binds
    abstract fun bindAccountService(accountServices: GoogleAccountServices): AccountServices

    @Binds
    abstract fun bindLocationService(locationServices: HuaweiLocationServices): LocationServices
}