package com.android.core.model.auth.register

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @Expose
    @SerializedName("username")
    var username : String ,
    @Expose
    @SerializedName("password")
    var password : String
)