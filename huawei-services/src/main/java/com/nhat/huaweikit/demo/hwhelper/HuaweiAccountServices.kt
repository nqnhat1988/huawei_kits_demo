package com.nhat.huaweikit.demo.hwhelper

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import com.huawei.hms.common.ApiException
import com.huawei.hms.support.hwid.HuaweiIdAuthManager
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService
import com.nhat.huaweikit.demo.huawei.common.ICallBack
import com.nhat.huaweikit.demo.hwhelper.common.IDTokenParser
import com.nhat.huaweikit.demo.nd_services.AccountServices

class HuaweiAccountServices : AccountServices {
    companion object {
        //login
        const val REQUEST_SIGN_IN_LOGIN = 1002

        //login by code
        const val REQUEST_SIGN_IN_LOGIN_CODE = 1003

        const val TAG = "HuaweiAccountServices"
    }

    private lateinit var mAuthParams: HuaweiIdAuthParams
    private lateinit var mAuthManager: HuaweiIdAuthService

    override fun signIn(activity: Activity) {
        mAuthParams = HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
            .setIdToken()
            .setAccessToken()
            .createParams()
        mAuthManager = HuaweiIdAuthManager.getService(activity, mAuthParams)
        activity.startActivityForResult(mAuthManager.signInIntent, REQUEST_SIGN_IN_LOGIN)
    }

    override fun signIn(fragment: Fragment) {
        mAuthParams = HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
            .setIdToken()
            .setAccessToken()
            .createParams()
        mAuthManager = HuaweiIdAuthManager.getService(fragment.requireActivity(), mAuthParams)
        fragment.startActivityForResult(mAuthManager.signInIntent, REQUEST_SIGN_IN_LOGIN)
    }

    override fun signInWithCode(activity: Activity) {
        mAuthParams = HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
            .setProfile()
            .setAuthorizationCode()
            .createParams()
        mAuthManager = HuaweiIdAuthManager.getService(activity, mAuthParams)
        activity.startActivityForResult(mAuthManager.signInIntent, REQUEST_SIGN_IN_LOGIN_CODE)
    }

    override fun signInWithCode(fragment: Fragment) {
        mAuthParams = HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
            .setProfile()
            .setAuthorizationCode()
            .createParams()
        mAuthManager = HuaweiIdAuthManager.getService(fragment.requireContext(), mAuthParams)
        fragment.startActivityForResult(mAuthManager.signInIntent, REQUEST_SIGN_IN_LOGIN_CODE)
    }

    override fun signOut(action: () -> Unit) {
        mAuthManager.signOut()
            .addOnSuccessListener { Log.i(TAG, "signOut Success") }
            .addOnSuccessListener { action.invoke() }
            .addOnFailureListener { Log.i(TAG, "signOut fail") }
    }

    private fun validateIdToken(idToken: String) {
        if (TextUtils.isEmpty(idToken)) {
            Log.i(TAG, "ID Token is empty")
        } else {
            val idTokenParser = IDTokenParser()
            try {
                idTokenParser.verify(idToken, object : ICallBack {
                    override fun onSuccess() {

                    }

                    override fun onSuccess(result: String?) {
                        if (!TextUtils.isEmpty(result)) {
                            Log.i(
                                TAG,
                                "id Token Validate Success, verify signature: $result"
                            )
                        } else {
                            Log.i(
                                TAG,
                                "Id token validate failed."
                            )
                        }
                    }

                    override fun onFailed() {
                        Log.i(
                            TAG,
                            "Id token validate failed."
                        )
                    }
                })
            } catch (e: Exception) {
                Log.i(
                    TAG,
                    "id Token validate failed." + e.javaClass.simpleName
                )
            } catch (e: Error) {
                Log.i(
                    TAG,
                    "id Token validate failed." + e.javaClass.simpleName
                )
                if (Build.VERSION.SDK_INT < 23) {
                    Log.i(
                        TAG,
                        "android SDK Version is not support. Current version is: " + Build.VERSION.SDK_INT
                    )
                }
            }
        }
    }

    override fun buildResponseIntent(
        userName: String,
        email: String?,
        imageUrl: String?
    ): Intent = Intent().apply {
        putExtra("USER_NAME", userName)
        putExtra("USER_EMAIL", email)
        putExtra("USER_IMAGE", imageUrl)
    }


    override fun processSignIn(data: Intent?): Intent? {
        val authHuaweiIdTask =
            HuaweiIdAuthManager.parseAuthResultFromIntent(data)
        if (authHuaweiIdTask.isSuccessful) {
            val huaweiAccount = authHuaweiIdTask.result
            Log.i(
                TAG,
                huaweiAccount.displayName + " signIn success "
            )
            Log.i(
                TAG,
                "AccessToken: " + huaweiAccount.accessToken
            )
            validateIdToken(huaweiAccount.idToken)
            return buildResponseIntent(
                huaweiAccount.displayName,
                huaweiAccount.email,
                huaweiAccount.avatarUriString
            )
        } else {
            Log.i(
                TAG,
                "signIn failed: " + (authHuaweiIdTask.exception as ApiException).statusCode
            )
            return null
        }
    }

    override fun processSignInCode(data: Intent?): Intent? {
        val authHuaweiIdTask =
            HuaweiIdAuthManager.parseAuthResultFromIntent(data)
        if (authHuaweiIdTask.isSuccessful) {
            val huaweiAccount = authHuaweiIdTask.result
            Log.i(TAG, "signIn get code success.")
            Log.i(TAG, "ServerAuthCode: " + huaweiAccount.authorizationCode)
            /**** english doc:For security reasons, the operation of changing the code to an AT must be performed on your server. The code is only an example and cannot be run.  */
            /** */
            return buildResponseIntent(
                huaweiAccount.displayName,
                huaweiAccount.email,
                huaweiAccount.avatarUriString
            )
        } else {
            Log.i(
                TAG,
                "signIn get code failed: " + (authHuaweiIdTask.exception as ApiException).statusCode
            )
            return null
        }
    }
}