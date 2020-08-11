package com.huawei.apac.dtse.hcoreexample

import com.huawei.apac.dtse.hcoreexample.BaseApp
import com.huawei.apac.dtse.hcoreexample.injection.DaggerApplicationComponent

class GmsApp : BaseApp() {
    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }
}