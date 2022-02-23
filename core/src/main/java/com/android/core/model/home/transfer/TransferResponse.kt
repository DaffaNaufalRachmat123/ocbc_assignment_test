package com.android.core.model.home.transfer

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TransferResponse(
    @Expose
    @SerializedName("status")
    var status : String ,
    @Expose
    @SerializedName("transactionId")
    var transactionId : String ,
    @Expose
    @SerializedName("amount")
    var amount : Int ,
    @Expose
    @SerializedName("description")
    var description : String ,
    @Expose
    @SerializedName("recipientAccount")
    var recipientAccount : String
)