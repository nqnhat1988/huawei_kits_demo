package com.nhat.huaweikit.demo.huawei.injection.module

import com.nhat.huaweikit.demo.hwhelper.HuaweiAccountServices
import com.nhat.huaweikit.demo.hwhelper.HuaweiLocationServices
import com.nhat.huaweikit.demo.hwhelper.HuaweiMapServices
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
        fun provideAccountService(): HuaweiAccountServices = HuaweiAccountServices()

        @Provides
        @JvmStatic
        fun provideLocationService(): HuaweiLocationServices = HuaweiLocationServices()

        @Provides
        @JvmStatic
        fun provideMapService(): HuaweiMapServices = HuaweiMapServices()
    }

    @Binds
    abstract fun bindAccountService(accountServices: HuaweiAccountServices): AccountServices

    @Binds
    abstract fun bindLocationService(locationServices: HuaweiLocationServices): LocationServices

    @Binds
    abstract fun bindMapService(mapServices: HuaweiMapServices): MapServices
}