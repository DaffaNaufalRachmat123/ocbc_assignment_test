package com.android.core.model.home.transfer

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TransferRequest(
    @Expose
    @SerializedName("receipientAccountNo")
    var receipientAccountNo : String ,
    @Expose
    @SerializedName("amount")
    var amount : Int ,
    @Expose
    @SerializedName("description")
    var description : String
)