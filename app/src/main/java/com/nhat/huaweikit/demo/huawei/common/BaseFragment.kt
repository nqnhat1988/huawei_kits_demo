package com.nhat.huaweikit.demo.huawei.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.nhat.huaweikit.demo.presentation.ViewModelFactory
import com.nhat.huaweikit.demo.presentation.data.Resource
import com.nhat.huaweikit.demo.presentation.data.ResourceState
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseFragment<T> : DaggerFragment(), HandleState<T> {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    abstract val layoutId: Int

    protected val disposable = CompositeDisposable()

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(layoutId, container, false)

    protected fun handleDataState(
        resource: Resource<T>
    ) {
        when (resource.status) {
            ResourceState.LOADING -> setupScreenForLoadingState()
            ResourceState.SUCCESS -> setupScreenForSuccess(resource.data)
            ResourceState.ERROR -> setupScreenForError(resource.message)
            else -> {
            }
        }
    }

    protected fun getSupportToolbar(): ActionBar? =
        activity?.let {
            if (it is AppCompatActivity) it.supportActionBar else null
        }
}

interface HandleState<T> {
    fun setupScreenForLoadingState()
    fun setupScreenForSuccess(t: T?)
    fun setupScreenForError(message: String?)
}