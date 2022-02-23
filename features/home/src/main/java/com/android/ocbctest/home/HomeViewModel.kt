package com.android.ocbctest.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.common.base.BaseViewModel
import com.android.common.extension.errorMessage
import com.android.common.utils.ViewState
import com.android.common.utils.setError
import com.android.common.utils.setLoading
import com.android.common.utils.setSuccess
import com.android.core.AppDispatchers
import com.android.core.model.home.HomeResponse
import com.android.core.model.home.payees.PayeeResponse
import com.android.core.model.home.transaction.TransactionResponse
import com.android.core.model.home.transfer.TransferRequest
import com.android.core.model.home.transfer.TransferResponse
import com.android.ocbctest.home.api.HomeApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeViewModel ( val api : HomeApi , val dispatchers: AppDispatchers) : BaseViewModel() {
    val homeResponse = MutableLiveData<ViewState<HomeResponse>>()
    val transactionResponse = MutableLiveData<ViewState<TransactionResponse>>()
    val payeeResponse = MutableLiveData<ViewState<PayeeResponse>>()
    val transferResponse = MutableLiveData<ViewState<TransferResponse>>()
    fun getBalance(authorization : String = "" ,isTest : Boolean = false){
        if(isTest){
            runBlocking {
                runCatching {
                    homeResponse.setLoading()
                    api.getBalanceTest(authorization)
                }.onSuccess {
                    homeResponse.setSuccess(it)
                }.onFailure {
                    homeResponse.setError(it.errorMessage())
                }
            }
        } else {
            viewModelScope.launch(dispatchers.main){
                runCatching {
                    homeResponse.setLoading()
                    api.getBalance()
                }.onSuccess {
                    homeResponse.setSuccess(it)
                }.onFailure {
                    homeResponse.setError(it.errorMessage())
                }
            }
        }
    }
    fun getTransactions(authorization : String = "" , isTest : Boolean = false){
        if(isTest){
            runBlocking {
                runCatching {
                    transactionResponse.setLoading()
                    api.getTransactionsTest(authorization)
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
    fun getPayees(authorization: String = "" , isTest: Boolean = false){
        if(isTest){
            runBlocking {
                runCatching {
                    payeeResponse.setLoading()
                    api.getPayeesTest(authorization)
                }.onSuccess {
                    payeeResponse.setSuccess(it)
                }.onFailure {
                    payeeResponse.setError(it.errorMessage())
                }
            }
        } else {
            viewModelScope.launch(dispatchers.main){
                runCatching {
                    payeeResponse.setLoading()
                    api.getPayees()
                }.onSuccess {
                    payeeResponse.setSuccess(it)
                }.onFailure {
                    payeeResponse.setError(it.errorMessage())
                }
            }
        }
    }
    fun requestTransfer(transferRequest: TransferRequest , authorization: String = "" , isTest : Boolean = false){
        if(isTest){
            runBlocking {
                runCatching {
                    transferResponse.setLoading()
                    api.requestTransferTest(transferRequest , authorization)
                }.onSuccess {
                    transferResponse.setSuccess(it)
                }.onFailure {
                    transferResponse.setError(it.errorMessage())
                }
            }
        } else {
            viewModelScope.launch(dispatchers.main){
                runCatching {
                    transferResponse.setLoading()
                    api.requestTransfer(transferRequest)
                }.onSuccess {
                    transferResponse.setSuccess(it)
                }.onFailure {
                    transferResponse.setError(it.errorMessage())
                }
            }
        }
    }
}