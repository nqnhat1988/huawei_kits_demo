package com.huawei.apac.dtse.hcoreexample.injection.module

import com.example.mobile_services.module.MobileSafeCheckModule
import com.nhat.huaweikit.demo.huawei_services.HuaweiAccountServices
import com.nhat.huaweikit.demo.huawei_services.HuaweiLocationServices
import com.nhat.huaweikit.demo.huawei_services.HuaweiMapServices
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