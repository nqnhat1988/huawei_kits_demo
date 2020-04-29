package com.nhat.huaweikit.demo.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nhat.huaweikit.demo.domain.interactor.user.GetUser
import com.nhat.huaweikit.demo.presentation.mapper.PresentationUserMapper

@Suppress("UNCHECKED_CAST")
open class UserViewModelFactory(
    private val getUser: GetUser,
    private val presentationUserMapper: PresentationUserMapper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(getUser, presentationUserMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}