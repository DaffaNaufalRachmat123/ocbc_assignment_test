package com.android.ocbctest.auth.api

import com.android.core.model.home.transaction.TransactionResponse
import com.android.core.model.auth.login.LoginRequest
import com.android.core.model.auth.login.LoginResponse
import com.android.core.model.auth.register.RegisterRequest
import com.android.core.model.auth.register.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {
    @POST("/login")
    suspend fun login(
        @Body
        loginRequest: LoginRequest
    ) : LoginResponse

    @POST("/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ) : RegisterResponse

    @GET("/transactions")
    suspend fun getTransactions() : TransactionResponse


}