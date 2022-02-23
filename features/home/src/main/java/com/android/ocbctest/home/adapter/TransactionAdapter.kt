package com.android.ocbctest.home.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.core.model.home.transaction.TransactionSort
import com.android.ocbctest.home.R
import com.android.ocbctest.home.databinding.ItemTransactionBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class TransactionAdapter : BaseQuickAdapter<TransactionSort, BaseViewHolder>(R.layout.item_transaction) {
    override fun convert(helper: BaseViewHolder, item: TransactionSort?) {
        val itemBinding = ItemTransactionBinding.bind(helper.itemView)
        val subAdapter = SubTransactionAdapter()
        item?.let { model ->
            subAdapter.setNewData(model.transactionList)
            itemBinding.dateText.text = model.date
            itemBinding.recyclerViewSubTransaction.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context , RecyclerView.VERTICAL , false)
                adapter = subAdapter
            }
        }
    }
}