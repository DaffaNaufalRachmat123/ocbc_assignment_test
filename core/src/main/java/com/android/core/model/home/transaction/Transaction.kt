package com.android.core.model.home.transaction

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Transaction(
    @Expose
    @SerializedName("transactionId")
    var transactionId : String ,
    @Expose
    @SerializedName("amount")
    var amount : Int ,
    @Expose
    @SerializedName("transactionDate")
    var transactionDate : String ,
    @Expose
    @SerializedName("description")
    var description : String ,
    @Expose
    @SerializedName("transactionType")
    var transactionType : String ,
    @Expose
    @SerializedName("receipient")
    var recipient : Recipient
)

data class Recipient(
    @Expose
    @SerializedName("accountNo")
    var accountNo : String,
    @Expose
    @SerializedName("accountHolder")
    var accountHolder : String
)