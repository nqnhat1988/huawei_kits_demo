package com.nhat.huaweikit.demo.huawei.injection.module

import com.nhat.huaweikit.demo.domain.executor.ThreadExecutor
import com.nhat.huaweikit.demo.huawei.common.JobExecutor
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

//    @Binds
//    abstract fun bindMovieRepository(productDataRepository: ProductDataRepository): ProductRepository

    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor
}