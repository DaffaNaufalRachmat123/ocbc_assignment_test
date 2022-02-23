package com.android.ocbctest.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.android.common.base.BaseActivity
import com.android.common.extension.binding
import com.android.common.extension.hide
import com.android.common.extension.invisible
import com.android.common.extension.show
import com.android.common.utils.ViewState
import com.android.common.widget.CustomSpinnerWidget
import com.android.common.widget.model.CustomSpinner
import com.android.core.model.home.transfer.TransferRequest
import com.android.ocbctest.home.databinding.ActivityTransferBinding

class ActivityTransfer : BaseActivity<HomeViewModel>(R.layout.activity_transfer) {
    private val binding by binding<ActivityTransferBinding>()
    private var selectedAccountNo = ""
    override fun getViewModel() = HomeViewModel::class
    override fun observerViewModel() {
        viewModel.payeeResponse.onResult { state ->
            when (state) {
                is ViewState.Loading -> {
                    binding.progressBarPayee.show()
                    binding.payeSpinner.invisible()
                }
                is ViewState.Success -> {
                    binding.progressBarPayee.hide()
                    binding.payeSpinner.show()
                    val customSpinnerList = ArrayList<CustomSpinner>()
                    state.data.data.forEach {
                        customSpinnerList.add(CustomSpinner(it.id , it.name , it.accountNo))
                    }
                    binding.payeSpinner.setCustomSpinnerList(customSpinnerList)
                    binding.payeSpinner.setAdapter(this)
                }
                is ViewState.Failed -> {
                    binding.progressBarPayee.hide()
                    binding.payeSpinner.show()
                    state.message?.let {
                        Toast.makeText(this@ActivityTransfer , it , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        viewModel.transferResponse.onResult { state ->
            when (state) {
                is ViewState.Loading -> {
                    binding.progressBarMake.show()
                    binding.btnMakeTransfer.hide()
                }
                is ViewState.Success -> {
                    binding.progressBarMake.hide()
                    binding.btnMakeTransfer.show()
                    val intent = Intent()
                    setResult(RESULT_OK , intent)
                    finish()
                }
                is ViewState.Failed -> {
                    binding.progressBarMake.hide()
                    binding.btnMakeTransfer.show()
                    state.message?.let {
                        Toast.makeText(this@ActivityTransfer , it , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        binding.icBack.setOnClickListener {
            onBackPressed()
        }
        binding.payeSpinner.setOnItemClickedListener(object : CustomSpinnerWidget.OnItemClickedListener {
            override fun onItemClicked(data: CustomSpinner) {
                selectedAccountNo = data.optional_text
            }
        })
        binding.btnMakeTransfer.setOnClickListener {
            if(selectedAccountNo != "" && !binding.amountText.text.toString().isNullOrEmpty() &&
                    !binding.descriptionText.text.toString().isNullOrEmpty()){
                viewModel.requestTransfer(
                    TransferRequest(
                        receipientAccountNo = selectedAccountNo,
                        amount = binding.amountText.text.toString().toInt(),
                        description = binding.descriptionText.text.toString()
                    )
                )
            } else {
                Toast.makeText(this@ActivityTransfer , "Please fill up all the form" , Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.getPayees()
    }
}