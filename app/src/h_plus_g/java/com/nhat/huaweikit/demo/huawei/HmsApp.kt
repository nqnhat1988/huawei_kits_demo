package com.nhat.huaweikit.demo.huawei

import com.nhat.huaweikit.demo.huawei.injection.DaggerApplicationComponent

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