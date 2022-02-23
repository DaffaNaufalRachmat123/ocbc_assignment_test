package com.android.core.model.home.payees

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Payee(
    @Expose
    @SerializedName("id")
    var id : String ,
    @Expose
    @SerializedName("name")
    var name : String ,
    @Expose
    @SerializedName("accountNo")
    var accountNo : String
)