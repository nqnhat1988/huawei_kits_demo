package com.huawei.apac.dtse.hcoreexample.injection.module

import com.huawei.apac.dtse.hcoreexample.common.JobExecutor
import com.nhat.huaweikit.demo.domain.executor.ThreadExecutor
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

//    @Binds
//    abstract fun bindMovieRepository(productDataRepository: ProductDataRepository): ProductRepository

    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor
}