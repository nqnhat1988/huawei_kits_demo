//package com.nhat.tekoassignment.injection.module
//
//import android.app.Application
//import androidx.room.Room
//import com.nhat.cache.IProductCacheImpl
//import com.nhat.cache.db.ProductsDatabase
//import com.nhat.data.repository.IProductCache
//import dagger.Binds
//import dagger.Module
//import dagger.Provides
//
///**
// * Module that provides all dependencies from the cache package/layer.
// */
//@Module
//abstract class CacheModule {
//
//    /**
//     * This companion object annotated as a module is necessary in order to provide dependencies
//     * statically in case the wrapping module is an abstract class (to use binding)
//     */
//    @Module
//    companion object {
//
//        @Provides
//        @JvmStatic
//        fun provideMoviesDatabase(application: Application): ProductsDatabase {
//            return Room.databaseBuilder(
//                application.applicationContext,
//                ProductsDatabase::class.java, "products.db"
//            )
//                .build()
//        }
//    }
//
//    @Binds
//    abstract fun bindMovieCache(iMovieCacheImpl: IProductCacheImpl): IProductCache
//}
