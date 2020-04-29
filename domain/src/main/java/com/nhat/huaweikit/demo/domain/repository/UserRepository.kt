package com.nhat.huaweikit.demo.domain.repository

import com.nhat.huaweikit.demo.domain.model.DomainUserModel
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Interface defining methods for how the data layer can pass data to and from the Domain layer.
 * This is to be implemented by the data layer, setting the requirements for the
 * operations that need to be implemented
 */
interface UserRepository {
    fun clearUser(): Completable
    fun getCurrentUser(): Flowable<DomainUserModel>
}