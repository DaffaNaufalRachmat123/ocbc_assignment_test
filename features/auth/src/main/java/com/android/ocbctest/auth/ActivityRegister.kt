package com.android.ocbctest.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.android.common.base.BaseActivity
import com.android.common.extension.binding
import com.android.common.extension.hide
import com.android.common.extension.show
import com.android.common.utils.ViewState
import com.android.core.model.auth.register.RegisterRequest
import com.android.ocbctest.auth.databinding.ActivityRegisterBinding

class ActivityRegister : BaseActivity<AuthViewModel>(R.layout.activity_register) {
    private val binding by binding<ActivityRegisterBinding>()
    override fun getViewModel() = AuthViewModel::class
    override fun observerViewModel() {
        viewModel.registerResponse.onResult { state ->
            when (state) {
                is ViewState.Loading -> {
                    binding.progressBarRegister.show()
                    binding.btnRegister.hide()
                }
                is ViewState.Success -> {
                    binding.btnRegister.show()
                    binding.progressBarRegister.hide()
                    val intent = Intent()
                    setResult(RESULT_OK , intent)
                    finish()
                }
                is ViewState.Failed -> {
                    binding.progressBarRegister.show()
                    binding.btnRegister.hide()
                    state.message?.let {
                        Toast.makeText(this@ActivityRegister , "Unable to register" , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        binding.icBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnRegister.setOnClickListener {
            if(binding.usernameText.text.toString() != null &&
                    binding.passwordText.text.toString() != null){
                viewModel.register(
                    RegisterRequest(
                        binding.usernameText.text.toString(),
                        binding.passwordText.text.toString()
                    )
                )
            } else {
                SweetAlertDialog(this , SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Attention")
                    .setContentText("Please fill up username & password")
                    .setConfirmText("OK")
            }
        }
    }
}