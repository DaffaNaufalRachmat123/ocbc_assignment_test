package com.android.core.model.home.payees

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PayeeResponse(
    @Expose
    @SerializedName("status")
    var status : String ,
    @Expose
    @SerializedName("data")
    var data : MutableList<Payee>
)