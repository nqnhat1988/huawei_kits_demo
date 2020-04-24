package com.nhat.huaweikit.demo.huawei.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nhat.huaweikit.demo.hwhelper.Constant
import com.nhat.huaweikit.demo.hwhelper.HuaweiAccountServices
import com.nhat.huaweikit.demo.hwhelper.R
import com.nhat.huaweikit.demo.presentation.user.UserViewModel
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var huaweiAccountServices: HuaweiAccountServices

    private val TAG = LoginFragment::class.java.simpleName
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userViewModel =
            ViewModelProvider(this).get(UserViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        userViewModel.userLiveData.observe(viewLifecycleOwner, Observer {

        })

//        huaweiAccountServices = HuaweiAccountServices(requireActivity(), this, TAG)

        return root
    }

    private fun signIn() {
        huaweiAccountServices.signIn(this)
    }

    private fun signInCode() {
        huaweiAccountServices.signInWithCode(this)
    }

    private fun signOut() {
        huaweiAccountServices.signOut()
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
            //            val i = huaweiAccountServices.buildResponseIntent(Activity.RESULT_CANCELED)
//            requireActivity().setResult(Activity.RESULT_CANCELED, i)
//            requireActivity().finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.REQUEST_SIGN_IN_LOGIN) {
            //login success
            //get user message by parseAuthResultFromIntent
            huaweiAccountServices.processSignIn(data)
        }
        if (requestCode == Constant.REQUEST_SIGN_IN_LOGIN_CODE) {
            //login success
            huaweiAccountServices.processSignInCode(data)
        }
    }
}
