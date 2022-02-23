package com.android.core.model.auth.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @Expose
    @SerializedName("status")
    var status : String ,
    @Expose
    @SerializedName("token")
    var token : String ,
    @Expose
    @SerializedName("username")
    var username : String ,
    @Expose
    @SerializedName("accountNo")
    var accountNo : String
)