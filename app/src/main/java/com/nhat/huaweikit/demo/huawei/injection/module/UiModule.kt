package com.nhat.huaweikit.demo.huawei.injection.module

import com.nhat.huaweikit.demo.domain.executor.PostExecutionThread
import com.nhat.huaweikit.demo.huawei.LoginActivity
import com.nhat.huaweikit.demo.huawei.MainActivity
import com.nhat.huaweikit.demo.huawei.UiThread
import com.nhat.huaweikit.demo.huawei.injection.scopes.PerActivity
import com.nhat.huaweikit.demo.huawei.injection.scopes.PerFragment
import com.nhat.huaweikit.demo.huawei.ui.account.AccountFragment
import com.nhat.huaweikit.demo.huawei.ui.login.LoginFragment
import com.nhat.huaweikit.demo.huawei.ui.map.MapFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Module that provides all dependencies from the mobile-ui package/layer and injects all activities.
 */
@Module
abstract class UiModule {
    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @PerActivity
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @PerActivity
    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity

    @PerFragment
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeAccountFragment(): AccountFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeLoginFragment(): LoginFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeMapFragment(): MapFragment
}

@Module
abstract class FragmentModule {
    @Binds
    internal abstract fun bindAccountFragment(fragment: AccountFragment): AccountFragment

    @Binds
    internal abstract fun bindLoginFragment(fragment: LoginFragment): LoginFragment

    @Binds
    internal abstract fun bindMapFragment(fragment: MapFragment): MapFragment
}