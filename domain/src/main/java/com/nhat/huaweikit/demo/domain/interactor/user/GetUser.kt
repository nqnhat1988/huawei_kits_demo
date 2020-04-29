package com.nhat.huaweikit.demo.domain.interactor.user

import com.nhat.huaweikit.demo.domain.executor.PostExecutionThread
import com.nhat.huaweikit.demo.domain.executor.ThreadExecutor
import com.nhat.huaweikit.demo.domain.interactor.FlowableUseCase
import com.nhat.huaweikit.demo.domain.model.DomainUserModel
import com.nhat.huaweikit.demo.domain.repository.UserRepository
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Use case used for retrieving a [List] of [DomainUserModel] instances from the [UserRepository]
 */
open class GetUser @Inject constructor(
//    private val userRepository: UserRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<DomainUserModel, Void?>(threadExecutor, postExecutionThread) {
    public override fun buildUseCaseObservable(params: Void?): Flowable<DomainUserModel> {
        //TODO impl getCurrentUser
//        return userRepository.getCurrentUser()
        return Flowable.just(DomainUserModel("sample user"))
    }
}