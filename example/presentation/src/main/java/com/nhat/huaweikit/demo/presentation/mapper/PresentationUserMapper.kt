package com.nhat.huaweikit.demo.presentation.mapper

import com.nhat.huaweikit.demo.domain.model.DomainUserModel
import com.nhat.huaweikit.demo.presentation.model.UserView
import javax.inject.Inject

/**
 * Map a [UserView] to and from a [DomainUserModel] instance when data is moving between
 * this layer and the Domain layer
 */
open class PresentationUserMapper @Inject constructor() :
    Mapper<UserView, DomainUserModel> {
    /**
     * Map a [DomainUserModel] instance to a [UserView] instance
     */
    override fun mapToView(type: DomainUserModel): UserView {
        return UserView(
            name = type.name
        )
    }
}