package com.huawei.apac.dtse.hcoreexample.injection.module

import com.example.mobile_services.module.MobileServicesModule
import dagger.Module

@Module(
    includes = [
        MobileServicesModule::class
    ]
)
abstract class MobileServicesModule