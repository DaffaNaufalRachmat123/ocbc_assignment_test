package com.android.ocbctest.home.api

import com.android.core.model.home.HomeResponse
import com.android.core.model.home.payees.PayeeResponse
import com.android.core.model.home.transaction.TransactionResponse
import com.android.core.model.home.transfer.TransferRequest
import com.android.core.model.home.transfer.TransferResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface HomeApi {
    @GET("/balance")
    suspend fun getBalance() : HomeResponse
    @GET("/transactions")
    suspend fun getTransactions() : TransactionResponse
    @GET("/payees")
    suspend fun getPayees() : PayeeResponse
    @POST("/transfer")
    suspend fun requestTransfer(
        @Body transferRequest: TransferRequest
    ) : TransferResponse

    @GET("/balance")
    suspend fun getBalanceTest(
        @Header("Authorization") token : String
    ) : HomeResponse
    @GET("/transactions")
    suspend fun getTransactionsTest(
        @Header("Authorization") token : String
    ) : TransactionResponse
    @GET("/payees")
    suspend fun getPayeesTest(
        @Header("Authorization") token : String
    ) : PayeeResponse
    @POST("/transfer")
    suspend fun requestTransferTest(
        @Body transferRequest: TransferRequest,
        @Header("Authorization") token : String
    ) : TransferResponse
}