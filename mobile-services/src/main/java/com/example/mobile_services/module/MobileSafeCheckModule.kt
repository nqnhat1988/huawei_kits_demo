package com.example.mobile_services.module

import android.content.Context
import com.example.mobile_services.common.SafeCheckImpl
import com.nhat.icore_services.common.SafeCheck
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class MobileSafeCheckModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideSafeCheck(context: Context): SafeCheckImpl =
            SafeCheckImpl(context)
    }

    @Binds
    abstract fun bindSafeCheck(safeCheck: SafeCheckImpl): SafeCheck
}