package com.huawei.apac.dtse.hcoreexample

import com.huawei.apac.dtse.hcoreexample.injection.DaggerApplicationComponent

class HmsApp : BaseApp() {
    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }
}