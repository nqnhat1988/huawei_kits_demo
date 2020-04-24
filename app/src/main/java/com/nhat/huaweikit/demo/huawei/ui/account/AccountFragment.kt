package com.nhat.huaweikit.demo.huawei.ui.account

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nhat.huaweikit.demo.huawei.LoginActivity
import com.nhat.huaweikit.demo.huawei.R
import com.nhat.huaweikit.demo.huawei.common.BaseFragment
import com.nhat.huaweikit.demo.huawei.common.visible
import com.nhat.huaweikit.demo.nd_services.Constant.REQUEST_SIGN_IN_LOGIN_ACTIVITY
import com.nhat.huaweikit.demo.presentation.data.Resource
import com.nhat.huaweikit.demo.presentation.data.ResourceState
import com.nhat.huaweikit.demo.presentation.model.UserView
import com.nhat.huaweikit.demo.presentation.user.UserViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class AccountFragment : BaseFragment<UserView>() {
    override val layoutId: Int = R.layout.fragment_home

    private lateinit var userViewModel: UserViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel =
            ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)

        userViewModel.userLiveData.observe(viewLifecycleOwner, Observer {
            handleDataState(it)
        })

        btn_sign_in.setOnClickListener {
            this.startActivityForResult(
                Intent(requireContext(), LoginActivity::class.java),
                REQUEST_SIGN_IN_LOGIN_ACTIVITY
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SIGN_IN_LOGIN_ACTIVITY && resultCode == RESULT_OK) {
            data?.let {
                userViewModel.userLiveData.postValue(
                    Resource(
                        ResourceState.SUCCESS,
                        UserView(it.getStringExtra("USER_NAME") ?: ""),
                        ""
                    )
                )
            }
        }
    }

    override fun setupScreenForLoadingState() {
        text_user_name.setText(R.string.state_loading_current_user)
        btn_sign_in.visibility = false.visible()
    }

    override fun setupScreenForSuccess(t: UserView?) {
        t?.let {
            text_user_name.text = String.format(getString(R.string.format_user_name), it.name)
            btn_sign_in.visibility = false.visible()
        } ?: let {
            btn_sign_in.visibility = true.visible()
        }
    }

    override fun setupScreenForError(message: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}