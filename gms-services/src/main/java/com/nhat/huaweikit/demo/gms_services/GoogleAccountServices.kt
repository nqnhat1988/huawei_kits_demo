package com.nhat.huaweikit.demo.gms_services

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.nhat.icore_services.common.Constant
import com.nhat.icore_services.services.AccountServices
import javax.inject.Inject


class GoogleAccountServices @Inject constructor() :
    AccountServices {
    companion object {
        const val TAG = "GoogleAccountServices"
    }

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private var gso: GoogleSignInOptions =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

    override fun signIn(activity: Activity) {
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)
        activity.startActivityForResult(
            mGoogleSignInClient.signInIntent,
            Constant.REQUEST_SIGN_IN_LOGIN
        )
    }

    override fun signIn(fragment: Fragment) {
        mGoogleSignInClient = GoogleSignIn.getClient(fragment.requireActivity(), gso)
        fragment.startActivityForResult(
            mGoogleSignInClient.signInIntent,
            Constant.REQUEST_SIGN_IN_LOGIN
        )
    }

    override fun signInWithCode(activity: Activity) {

    }

    override fun signInWithCode(fragment: Fragment) {

    }

    override fun signOut(action: () -> Unit) {
        //TODO enhance action
        mGoogleSignInClient.signOut().addOnCompleteListener { action.invoke() }
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
        return try {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            task.getResult(ApiException::class.java)?.let {
                buildResponseIntent(it.displayName ?: "N/A", it.email, it.photoUrl.toString())
            }
        } catch (e: ApiException) { // The ApiException status code indicates the detailed failure reason.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            buildResponseIntent()
        }
    }

    override fun processSignInCode(data: Intent?): Intent? {
        TODO("not supported")
    }
}