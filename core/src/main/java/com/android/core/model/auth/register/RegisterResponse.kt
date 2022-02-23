package com.android.core.model.auth.register

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @Expose
    @SerializedName("status")
    var status : String ,
    @Expose
    @SerializedName("token")
    var token : String
)