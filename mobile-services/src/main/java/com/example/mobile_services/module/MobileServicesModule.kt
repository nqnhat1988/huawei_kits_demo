package com.example.mobile_services.module

import android.content.Context
import com.example.mobile_services.MobileAccountServices
import com.example.mobile_services.SafeCheckImpl
import com.nhat.huaweikit.demo.gms_services.GoogleAccountServices
import com.nhat.huaweikit.demo.hwhelper.HuaweiAccountServices
import com.nhat.huaweikit.demo.nd_services.SafeCheck
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        MobileAccountServicesModule::class
    ]
)
abstract class MobileServicesModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideSafeCheck(context: Context): SafeCheckImpl = SafeCheckImpl(context)

        @Provides
        @JvmStatic
        fun provideMobileAccountService(
            huaweiAccountServices: HuaweiAccountServices,
            googleAccountServices: GoogleAccountServices,
            safeCheck: SafeCheck
        ): MobileAccountServices = MobileAccountServices(
            huaweiAccountServices,
            googleAccountServices,
            safeCheck
        )
    }

    @Binds
    abstract fun bindSafeCheck(safeCheck: SafeCheckImpl): SafeCheck
}