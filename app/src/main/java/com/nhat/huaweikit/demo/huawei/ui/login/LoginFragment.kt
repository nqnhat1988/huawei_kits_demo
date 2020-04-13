package com.nhat.huaweikit.demo.huawei.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.huawei.hms.common.ApiException
import com.huawei.hms.support.hwid.HuaweiIdAuthManager
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService
import com.nhat.huaweikit.demo.huawei.Constant
import com.nhat.huaweikit.demo.huawei.R
import com.nhat.huaweikit.demo.huawei.common.ICallBack
import com.nhat.huaweikit.demo.huawei.common.IDTokenParser
import com.nhat.huaweikit.demo.huawei.ui.health.HealthFragment
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var mAuthParams: HuaweiIdAuthParams
    private lateinit var mAuthManager: HuaweiIdAuthService

    private val TAG = HealthFragment::class.java.simpleName
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel =
            ViewModelProvider(this).get(LoginViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        loginViewModel.text.observe(viewLifecycleOwner, Observer {

        })

        //Initialize the HuaweiIdSignInClient object by calling the getClient method of HuaweiIdSignIn
        mAuthParams = HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
            .setIdToken()
            .setAccessToken()
            .createParams()
        mAuthManager = HuaweiIdAuthManager.getService(requireActivity(), mAuthParams)

        return root
    }

    private fun signIn() {
        startActivityForResult(mAuthManager.signInIntent, Constant.REQUEST_SIGN_IN_LOGIN)
    }

    private fun signInCode() {
        mAuthParams = HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
            .setProfile()
            .setAuthorizationCode()
            .createParams()
        mAuthManager = HuaweiIdAuthManager.getService(requireActivity(), mAuthParams)
        startActivityForResult(mAuthManager.signInIntent, Constant.REQUEST_SIGN_IN_LOGIN_CODE)
    }

    private fun signOut() {
        val signOutTask = mAuthManager.signOut()
        signOutTask.addOnSuccessListener {
            Log.i(TAG, "signOut Success")
            responseActivity(Activity.RESULT_CANCELED)
        }
            .addOnFailureListener {
                Log.i(TAG, "signOut fail")
            }
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_login_with_token.setOnClickListener {
            signIn()
        }

        btn_login_with_auth_code.setOnClickListener {
            signInCode()
        }

        btn_sign_out.setOnClickListener {
            signOut()
        }

        btn_cancel.setOnClickListener {
            responseActivity(Activity.RESULT_CANCELED)
        }
    }

    private fun responseActivity(
        resultCode: Int,
        userName: String = "",
        email: String? = "",
        imageUrl: String? = ""
    ) {
        val intent = Intent()
        intent.putExtra("USER_NAME", userName)
        intent.putExtra("USER_EMAIL", email)
        intent.putExtra("USER_IMAGE", imageUrl)
        requireActivity().setResult(resultCode, intent)
        requireActivity().finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.REQUEST_SIGN_IN_LOGIN) {
            //login success
            //get user message by parseAuthResultFromIntent
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
                responseActivity(
                    Activity.RESULT_OK,
                    huaweiAccount.displayName,
                    huaweiAccount.email,
                    huaweiAccount.avatarUriString
                )
            } else {
                Log.i(
                    TAG,
                    "signIn failed: " + (authHuaweiIdTask.exception as ApiException).statusCode
                )
            }
        }
        if (requestCode == Constant.REQUEST_SIGN_IN_LOGIN_CODE) {
            //login success
            val authHuaweiIdTask =
                HuaweiIdAuthManager.parseAuthResultFromIntent(data)
            if (authHuaweiIdTask.isSuccessful) {
                val huaweiAccount = authHuaweiIdTask.result
                Log.i(TAG, "signIn get code success.")
                Log.i(TAG, "ServerAuthCode: " + huaweiAccount.authorizationCode)
                /**** english doc:For security reasons, the operation of changing the code to an AT must be performed on your server. The code is only an example and cannot be run.  */
                /** */
                responseActivity(
                    Activity.RESULT_OK,
                    huaweiAccount.displayName,
                    huaweiAccount.email,
                    huaweiAccount.avatarUriString
                )
            } else {
                Log.i(
                    TAG,
                    "signIn get code failed: " + (authHuaweiIdTask.exception as ApiException).statusCode
                )
            }
        }
    }
}
