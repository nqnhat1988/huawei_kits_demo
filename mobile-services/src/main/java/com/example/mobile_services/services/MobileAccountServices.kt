package com.example.mobile_services.services

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import com.nhat.huaweikit.demo.gms_services.GoogleAccountServices
import com.nhat.huaweikit.demo.huawei_services.HuaweiAccountServices
import com.nhat.icore_services.common.MobileServicesEnum
import com.nhat.icore_services.common.SafeCheck
import com.nhat.icore_services.services.AccountServices
import com.nhat.icore_services.services.BaseServices
import javax.inject.Inject

class MobileAccountServices @Inject constructor(
    huaweiAccountServices: HuaweiAccountServices,
    googleAccountServices: GoogleAccountServices,
    safeCheck: SafeCheck
) : AccountServices, BaseServices {

    override val mobileServicesEnum = safeCheck.mobileServicesEnum

    private val accountServices: AccountServices by lazy {
        when (mobileServicesEnum) {
            MobileServicesEnum.HMS -> huaweiAccountServices
            MobileServicesEnum.GMS -> googleAccountServices
        }
    }

    override fun signIn(activity: Activity) {
        accountServices.signIn(activity)
    }

    override fun signIn(fragment: Fragment) {
        accountServices.signIn(fragment)
    }

    override fun signInWithCode(activity: Activity) {
        accountServices.signInWithCode(activity)
    }

    override fun signInWithCode(fragment: Fragment) {
        accountServices.signInWithCode(fragment)
    }

    override fun signOut(action: () -> Unit) {
        accountServices.signOut(action)
    }

    override fun buildResponseIntent(userName: String, email: String?, imageUrl: String?): Intent =
        accountServices.buildResponseIntent(userName, email, imageUrl)


    override fun processSignIn(data: Intent?): Intent? = accountServices.processSignIn(data)

    override fun processSignInCode(data: Intent?): Intent? = accountServices.processSignInCode(data)
}