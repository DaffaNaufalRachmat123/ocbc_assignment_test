package com.android.ocbctest.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.common.base.BaseViewModel
import com.android.common.extension.errorMessage
import com.android.common.utils.ViewState
import com.android.common.utils.setError
import com.android.common.utils.setLoading
import com.android.common.utils.setSuccess
import com.android.core.AppDispatchers
import com.android.core.model.home.transaction.TransactionResponse
import com.android.core.model.auth.login.LoginRequest
import com.android.core.model.auth.login.LoginResponse
import com.android.core.model.auth.register.RegisterRequest
import com.android.core.model.auth.register.RegisterResponse
import com.android.ocbctest.auth.api.AuthApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AuthViewModel ( val api : AuthApi , val dispatchers: AppDispatchers) : BaseViewModel() {
    val loginResponse = MutableLiveData<ViewState<LoginResponse>>()
    val registerResponse = MutableLiveData<ViewState<RegisterResponse>>()
    val transactionResponse = MutableLiveData<ViewState<TransactionResponse>>()
    fun login(loginRequest: LoginRequest , isTest : Boolean = false){
        if(isTest){
            runBlocking {
                runCatching {
                    loginResponse.setLoading()
                    api.login(loginRequest)
                }.onSuccess {
                    loginResponse.setSuccess(it)
                }.onFailure {
                    loginResponse.setError(it.errorMessage())
                }
            }
        } else {
            viewModelScope.launch(dispatchers.main){
                runCatching {
                    loginResponse.setLoading()
                    api.login(loginRequest)
                }.onSuccess {
                    loginResponse.setSuccess(it)
                }.onFailure {
                    loginResponse.setError(it.errorMessage())
                }
            }
        }
    }

    fun register(registerRequest: RegisterRequest , isTest : Boolean = false){
        if(isTest){
            runBlocking {
                runCatching {
                    registerResponse.setLoading()
                    api.register(registerRequest)
                }.onSuccess {
                    registerResponse.setSuccess(it)
                }.onFailure {
                    registerResponse.setError(it.errorMessage())
                }
            }
        } else {
            viewModelScope.launch(dispatchers.main){
                runCatching {
                    registerResponse.setLoading()
                    api.register(registerRequest)
                }.onSuccess {
                    registerResponse.setSuccess(it)
                }.onFailure {
                    registerResponse.setError(it.errorMessage())
                }
            }
        }
    }

    fun getTransaction(isTest : Boolean = false){
        if(isTest){
            runBlocking {
                runCatching {
                    transactionResponse.setLoading()
                    api.getTransactions()
                }.onSuccess {
                    transactionResponse.setSuccess(it)
                }.onFailure {
                    transactionResponse.setError(it.errorMessage())
                }
            }
        } else {
            viewModelScope.launch(dispatchers.main){
                runCatching {
                    transactionResponse.setLoading()
                    api.getTransactions()
                }.onSuccess {
                    transactionResponse.setSuccess(it)
                }.onFailure {
                    transactionResponse.setError(it.errorMessage())
                }
            }
        }
    }
}