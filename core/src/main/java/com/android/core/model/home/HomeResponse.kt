package com.android.core.model.home

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @Expose
    @SerializedName("status")
    var status : String ,
    @Expose
    @SerializedName("accountNo")
    var accountNo : String ,
    @Expose
    @SerializedName("balance")
    var balance : Int
)