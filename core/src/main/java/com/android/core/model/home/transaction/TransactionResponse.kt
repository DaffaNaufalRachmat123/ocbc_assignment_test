package com.android.core.model.home.transaction

import com.android.core.model.home.transaction.Transaction
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TransactionResponse(
    @Expose
    @SerializedName("status")
    var status : String ,
    @Expose
    @SerializedName("data")
    var data : MutableList<Transaction>
)