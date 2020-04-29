package com.nhat.huaweikit.demo.nd_services

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment

interface AccountServices {
    fun signIn(activity: Activity)
    fun signIn(fragment: Fragment)
    fun signInWithCode(activity: Activity)
    fun signInWithCode(fragment: Fragment)
    fun signOut(action: () -> Unit)
    fun buildResponseIntent(
        userName: String = "",
        email: String? = "",
        imageUrl: String? = ""
    ): Intent

    fun processSignIn(data: Intent?): Intent?
    fun processSignInCode(data: Intent?): Intent?
}