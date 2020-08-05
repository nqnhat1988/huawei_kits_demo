package com.nhat.huaweikit.demo.huawei.ui.login

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mobile_services.MobileAccountServices
import com.nhat.huaweikit.demo.huawei.BuildConfig
import com.nhat.huaweikit.demo.huawei.R
import com.nhat.huaweikit.demo.huawei.common.BaseFragment
import com.nhat.huaweikit.demo.huawei.common.finishWithResult
import com.nhat.huaweikit.demo.huawei.common.visible
import com.nhat.huaweikit.demo.nd_services.Constant
import com.nhat.huaweikit.demo.presentation.user.UserViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject


@Suppress("PLUGIN_WARNING")
class LoginFragment : BaseFragment<UserViewModel>() {

    companion object {
        const val TAG = "LoginFragment"
    }

    private lateinit var userViewModel: UserViewModel

//    @Inject
//    lateinit var accountServices: AccountServices

    @Inject
    lateinit var accountServices: MobileAccountServices

    private fun signIn() {
        accountServices.signIn(this)
    }

    private fun signInCode() {
        accountServices.signInWithCode(this)
    }

    private fun signOut() {
        accountServices.signOut {
            requireActivity().finishWithResult(
                RESULT_CANCELED,
                accountServices.buildResponseIntent()
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel =
            ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)
        userViewModel.userLiveData.observe(viewLifecycleOwner, Observer {

        })
        val isGMS = BuildConfig.FLAVOR == "gms"

        btn_google_sign_in.visibility = isGMS.visible()
        btn_login_with_token.visibility = isGMS.not().visible()
        btn_login_with_auth_code.visibility = isGMS.not().visible()

        btn_google_sign_in.setOnClickListener {
            signIn()
        }

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
            requireActivity().finishWithResult(
                RESULT_CANCELED,
                accountServices.buildResponseIntent()
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.REQUEST_SIGN_IN_LOGIN) {
            //login success
            accountServices.processSignIn(data)?.let {
                requireActivity().finishWithResult(RESULT_OK, it)
            } ?: let {
                requireActivity().finishWithResult(
                    RESULT_CANCELED,
                    accountServices.buildResponseIntent()
                )
            }
        }
        if (requestCode == Constant.REQUEST_SIGN_IN_LOGIN_CODE) {
            //login success
            accountServices.processSignInCode(data)?.let {
                requireActivity().finishWithResult(RESULT_OK, it)
            } ?: let {
                requireActivity().finishWithResult(
                    RESULT_CANCELED,
                    accountServices.buildResponseIntent()
                )
            }
        }
    }

    override val layoutId: Int = R.layout.fragment_login

    override fun setupScreenForLoadingState() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setupScreenForSuccess(t: UserViewModel?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setupScreenForError(message: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}