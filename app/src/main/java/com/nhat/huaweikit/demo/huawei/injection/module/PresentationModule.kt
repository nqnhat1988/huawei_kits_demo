package com.nhat.huaweikit.demo.huawei.injection.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nhat.huaweikit.demo.presentation.ViewModelFactory
import com.nhat.huaweikit.demo.presentation.user.UserViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

/**
 * Annotation class to identify view models by classname.
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

/**
 * Module that provides all dependencies from the presentation package/layer.
 */
@Module
abstract class PresentationModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindUserViewModel(viewModel: UserViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

