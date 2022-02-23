package com.android.core.model.auth.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @Expose
    @SerializedName("username")
    var username : String ,
    @Expose
    @SerializedName("password")
    var password : String
)