package com.android.ocbctest.home.adapter

import com.android.core.model.home.transaction.Transaction
import com.android.ocbctest.home.R
import com.android.ocbctest.home.databinding.ItemSubTransactionBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class SubTransactionAdapter : BaseQuickAdapter<Transaction, BaseViewHolder>(R.layout.item_sub_transaction) {
    override fun convert(helper: BaseViewHolder, item: Transaction?) {
        val itemBinding = ItemSubTransactionBinding.bind(helper.itemView)
        item?.let { model ->
            itemBinding.accountNoText.text = if(model.recipient == null) "" else model.recipient.accountNo
            itemBinding.accountHolderText.text = if(model.recipient == null) "" else model.recipient.accountHolder
            itemBinding.amountText.text = model.amount.toString()
        }
    }
}