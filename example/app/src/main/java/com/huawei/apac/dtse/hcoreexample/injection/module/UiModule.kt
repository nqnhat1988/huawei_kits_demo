package com.huawei.apac.dtse.hcoreexample.injection.module

import com.huawei.apac.dtse.hcoreexample.LoginActivity
import com.huawei.apac.dtse.hcoreexample.MainActivity
import com.huawei.apac.dtse.hcoreexample.UiThread
import com.huawei.apac.dtse.hcoreexample.injection.scopes.PerActivity
import com.huawei.apac.dtse.hcoreexample.injection.scopes.PerFragment
import com.huawei.apac.dtse.hcoreexample.ui.account.AccountFragment
import com.huawei.apac.dtse.hcoreexample.ui.login.LoginFragment
import com.huawei.apac.dtse.hcoreexample.ui.map.MapFragment
import com.nhat.huaweikit.demo.domain.executor.PostExecutionThread
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