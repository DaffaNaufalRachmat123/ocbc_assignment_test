package com.android.core.model.home.transaction

import com.android.core.model.home.transaction.Transaction

data class TransactionSort(
    var date : String,
    var transactionList : MutableList<Transaction>
)