package com.nhat.huaweikit.demo.huawei.injection

import android.app.Application
import com.nhat.huaweikit.demo.huawei.BaseApp
import com.nhat.huaweikit.demo.huawei.injection.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        AndroidSupportInjectionModule::class,
        DataModule::class,
//        CacheModule::class,
        DomainModule::class,
        PresentationModule::class,
//        RemoteModule::class,
        MobileServicesModule::class,
        com.example.mobile_services.module.MobileServicesModule::class,
        UiModule::class
    ]
)
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: BaseApp)
}
