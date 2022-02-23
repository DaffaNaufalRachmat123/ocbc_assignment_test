package com.android.ocbctest.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.android.common.base.BaseActivity
import com.android.common.extension.*
import com.android.common.utils.ViewState
import com.android.core.model.home.transaction.Recipient
import com.android.core.model.home.transaction.Transaction
import com.android.core.model.home.transaction.TransactionSort
import com.android.core.prefs.UserSession
import com.android.navigation.Activities
import com.android.navigation.startFeature
import com.android.navigation.startFeatureForResult
import com.android.ocbctest.home.adapter.TransactionAdapter
import com.android.ocbctest.home.databinding.ActivityHomeBinding
import com.github.ajalt.timberkt.Timber.d
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ActivityHome : BaseActivity<HomeViewModel>(R.layout.activity_home) {
    private val binding by binding<ActivityHomeBinding>()
    override fun getViewModel() = HomeViewModel::class
    override fun observerViewModel() {
        viewModel.homeResponse.onResult { state ->
            when (state) {
                is ViewState.Loading -> {
                    binding.shimmerCard.show()
                    binding.dataCardContainer.invisible()
                }
                is ViewState.Success -> {
                    binding.shimmerCard.hide()
                    binding.dataCardContainer.show()
                    binding.accountNoText.text = state.data.accountNo
                    binding.balanceText.text = "SGD ${state.data.balance.toString()}"
                    viewModel.getTransactions()
                }
                is ViewState.Failed -> {
                    binding.shimmerCard.hide()
                    binding.dataCardContainer.show()
                    state.message?.let {
                        try {
                            val objs = JSONObject(it)
                            if(objs.getString("message").equals("jwt expired")){
                                Toast.makeText(applicationContext , "Session expired , you need to login again" , Toast.LENGTH_SHORT).show()
                                startFeature(Activities.ActivityLogin){
                                    clearTask()
                                    newTask()
                                }
                                UserSession.logout()
                            }
                        } catch (e:JSONException){
                            Toast.makeText(applicationContext , it , Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        viewModel.transactionResponse.onResult { state ->
            when (state) {
                is ViewState.Loading -> {
                    binding.shimmerTransaction.show()
                    binding.emptyText.hide()
                    binding.recyclerViewTransaction.hide()
                }
                is ViewState.Success -> {
                    binding.shimmerTransaction.hide()
                    binding.recyclerViewTransaction.show()
                    if(state.data.data.size > 0){
                        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'")
                        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(Calendar.getInstance().timeZone.id))
                        val groups = state.data.data.groupBy { "${simpleDateFormat.parse(it.transactionDate).date} ${simpleDateFormat.parse(it.transactionDate).month.toString().convertToMonthName()} ${simpleDateFormat.parse(it.transactionDate).year}" }
                            .entries.map { ( name , group) -> TransactionSort(name.toString() , group.toMutableList())}
                        initializeTransactionList(groups.toMutableList())
                    } else {
                        binding.emptyText.show()
                    }
                }
                is ViewState.Failed -> {
                    binding.shimmerTransaction.hide()
                    binding.recyclerViewTransaction.show()
                    binding.emptyText.hide()
                    state.message?.let {
                        try {
                            val objs = JSONObject(it)
                            if(objs.getString("message").equals("jwt expired")){
                                Toast.makeText(applicationContext , "Session expired , you need to login again" , Toast.LENGTH_SHORT).show()
                                startFeature(Activities.ActivityLogin){
                                    clearTask()
                                    newTask()
                                }
                                UserSession.logout()
                            }
                        } catch (e:JSONException){
                            Toast.makeText(applicationContext , it , Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        binding.btnLogout.setOnClickListener {
            UserSession.logout()
            startFeature(Activities.ActivityLogin){
                clearTask()
                newTask()
            }
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getBalance()
            binding.swipeRefresh.isRefreshing = false
        }
        binding.btnMakeTransfer.setOnClickListener {
            startFeatureForResult(Activities.ActivityTransfer, this , 200){}
        }

        binding.accountHolderText.text = UserSession.userName
        viewModel.getBalance()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            200 -> {
                if(resultCode == RESULT_OK){
                    viewModel.getBalance()
                    Toast.makeText(this@ActivityHome , "Transfer created" , Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    private fun initializeTransactionList(transactionSortList : MutableList<TransactionSort>){
        val transAdapter = TransactionAdapter()
        transAdapter.setNewData(transactionSortList)
        binding.recyclerViewTransaction.apply {
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(this@ActivityHome , RecyclerView.VERTICAL , false)
            adapter = transAdapter
        }
    }
}