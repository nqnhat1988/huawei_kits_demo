package com.nhat.huaweikit.demo.presentation.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nhat.huaweikit.demo.domain.interactor.user.GetUser
import com.nhat.huaweikit.demo.domain.model.DomainUserModel
import com.nhat.huaweikit.demo.presentation.data.Resource
import com.nhat.huaweikit.demo.presentation.data.ResourceState
import com.nhat.huaweikit.demo.presentation.mapper.PresentationUserMapper
import com.nhat.huaweikit.demo.presentation.model.UserView
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

open class UserViewModel @Inject constructor(
    private val getGetUser: GetUser,
    private val presentationUserMapper: PresentationUserMapper
) : ViewModel() {
    val userLiveData: MutableLiveData<Resource<UserView>> = MutableLiveData()

    fun fetch() {
        userLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        return getGetUser.execute(UserSubscriber())
    }

    override fun onCleared() {
        getGetUser.dispose()
        super.onCleared()
    }

    inner class UserSubscriber : DisposableSubscriber<DomainUserModel>() {

        override fun onComplete() {}

        override fun onNext(domainProductModel: DomainUserModel) {
            userLiveData.postValue(
                Resource(
                    ResourceState.SUCCESS,
                    presentationUserMapper.mapToView(domainProductModel),
                    null
                )
            )
        }

        override fun onError(exception: Throwable) {
            userLiveData.postValue(Resource(ResourceState.ERROR, null, exception.message))
        }
    }
}
