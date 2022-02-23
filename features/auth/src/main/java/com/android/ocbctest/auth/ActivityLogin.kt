package com.android.ocbctest.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.android.common.base.BaseActivity
import com.android.common.extension.*
import com.android.common.utils.ViewState
import com.android.core.model.auth.login.LoginRequest
import com.android.core.prefs.UserSession
import com.android.navigation.Activities
import com.android.navigation.startFeature
import com.android.navigation.startFeatureForResult
import com.android.ocbctest.auth.databinding.ActivityLoginBinding
import java.util.*

class ActivityLogin : BaseActivity<AuthViewModel>(R.layout.activity_login) {
    private val binding by binding<ActivityLoginBinding>()
    override fun getViewModel() = AuthViewModel::class
    override fun observerViewModel() {
        viewModel.loginResponse.onResult { state ->
            when (state) {
                is ViewState.Loading -> {
                    binding.progressBarLogin.show()
                    binding.btnLogin.hide()
                }
                is ViewState.Success -> {
                    binding.progressBarLogin.hide()
                    binding.btnLogin.show()
                    UserSession.doLogin(state.data.token , state.data.username)
                    startFeature(Activities.ActivityHome){
                        clearTask()
                        newTask()
                    }
                }
                is ViewState.Failed -> {
                    binding.progressBarLogin.show()
                    binding.btnLogin.hide()
                    SweetAlertDialog(this)
                        .setTitleText("Error When Login")
                        .setContentText(state.message)
                        .setConfirmText("OK")
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(UserSession.isLoggedIn){
            startFeature(Activities.ActivityHome){
                clearTask()
                newTask()
            }
        }
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        binding.btnLogin.setOnClickListener {
            if(binding.usernameText.text.toString() != null &&
                    binding.passwordText.text.toString() != null){
                viewModel.login(
                    LoginRequest(
                        binding.usernameText.text!!.toString(),
                        binding.passwordText.text!!.toString()
                    )
                )
            } else {
                SweetAlertDialog(this , SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Attention")
                    .setContentText("Please fill up username & password")
                    .setConfirmText("OK")
            }
        }
        binding.registerText.setOnClickListener {
            startFeatureForResult(Activities.ActivityRegister , this , requestCode = 100){}
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            100 -> {
                if(resultCode == RESULT_OK){
                    Toast.makeText(this@ActivityLogin , "Account registered" , Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}