//package com.nhat.tekoassignment.injection.module
//
//import com.nhat.data.repository.IProductRemote
//import com.nhat.remote.ProductRemoteImpl
//import com.nhat.remote.ProductService
//import com.nhat.remote.ProductServiceFactory
//import com.nhat.tekoassignment.BuildConfig
//import dagger.Binds
//import dagger.Module
//import dagger.Provides
//
///**
// * Module that provides all dependencies from the repository package/layer.
// */
//@Module
//abstract class RemoteModule {
//
//    /**
//     * This companion object annotated as a module is necessary in order to provide dependencies
//     * statically in case the wrapping module is an abstract class (to use binding)
//     */
//    @Module
//    companion object {
//        @Provides
//        @JvmStatic
//        fun provideMovieService(): ProductService {
//            return ProductServiceFactory.makeService(BuildConfig.DEBUG)
//        }
//    }
//
//    @Binds
//    abstract fun bindMovieRemote(iProductRemote: ProductRemoteImpl): IProductRemote
//}