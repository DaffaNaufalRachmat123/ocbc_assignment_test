package com.android.ocbctest.home

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.common.utils.ViewState
import com.android.core.AppDispatchers
import com.android.core.di.provideApiService
import com.android.core.di.provideOkHttpClientTest
import com.android.core.di.provideRetrofit
import com.android.core.model.auth.login.LoginRequest
import com.android.core.model.auth.register.RegisterRequest
import com.android.core.model.home.transfer.TransferRequest
import com.android.ocbctest.auth.AuthViewModel
import com.android.ocbctest.auth.api.AuthApi
import com.android.ocbctest.home.api.HomeApi
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.StringBuilder
import java.util.*
import java.util.concurrent.Callable

@RunWith(MockitoJUnitRunner::class)
class HomeUnitTest : KoinTest {
    private lateinit var context: Context
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private var authViewModel : AuthViewModel? = null
    private var homeViewModel : HomeViewModel? = null
    private var username = "daffa"
    private var password = "daffa"
    private var accountNo = ""
    private var authorizationToken : String? = null
    @Before
    fun setup(){
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler?>? -> Schedulers.trampoline() }
        context = mock(Context::class.java)
        startKoin {
            koinApplication {
                androidContext(context)
                loadKoinModules(
                    listOf(
                        module{
                            single { provideOkHttpClientTest() }
                            single { provideRetrofit(get()) }
                            single { provideApiService<AuthApi>(get()) }
                            single { provideApiService<HomeApi>(get()) }
                            factory { AppDispatchers(Dispatchers.Main , Dispatchers.IO) }
                            viewModel { AuthViewModel(get() , get()) }
                            viewModel { HomeViewModel(get() , get()) }
                        }
                    )
                )
            }
        }
        authViewModel = get<AuthViewModel>()
        homeViewModel = get<HomeViewModel>()
        userLoginAndGetToken()
    }

    @Test
    fun `Get Balance`(){
        Assert.assertNotNull(homeViewModel)
        homeViewModel?.homeResponse?.observeForever(Observer { state ->
            when (state) {
                is ViewState.Loading -> {}
                is ViewState.Success -> {
                    Assert.assertNotNull(state.data)
                    print("Get Balance Suceeed")
                }
                is ViewState.Failed -> {
                    Assert.fail("Failed to get balance")
                }
            }
        })
        authorizationToken?.let {
            homeViewModel?.getBalance(it , isTest = true)
        }
    }

    @Test
    fun `Get Payees`(){
        Assert.assertNotNull(homeViewModel)
        homeViewModel?.payeeResponse?.observeForever(Observer { state ->
            when (state) {
                is ViewState.Loading -> {}
                is ViewState.Success -> {
                    Assert.assertNotNull(state.data)
                    print("Get Payees Succeed")
                }
                is ViewState.Failed -> {
                    Assert.fail("Failed to get payees")
                }
            }
        })
        authorizationToken?.let {
            homeViewModel?.getPayees(it , isTest = true)
        }
    }

    @Test
    fun `Get Transactions`(){
        Assert.assertNotNull(homeViewModel)
        homeViewModel?.transactionResponse?.observeForever(Observer { state ->
            when (state) {
                is ViewState.Loading -> {}
                is ViewState.Success -> {
                    Assert.assertNotNull(state.data)
                    print("Get Transactions Succeed")
                }
                is ViewState.Failed -> {
                    Assert.fail("Failed to get transactions")
                }
            }
        })
        authorizationToken?.let {
            homeViewModel?.getTransactions(it , isTest = true)
        }
    }

    @Test
    fun `Get Payees And Request Transfer`(){
        Assert.assertNotNull(homeViewModel)
        homeViewModel?.transferResponse?.observeForever(Observer { state ->
            when (state) {
                is ViewState.Loading -> {}
                is ViewState.Success -> {
                    Assert.assertNotNull(state.data)
                    print("Get Payees And Request Transfer Succeed")
                }
                is ViewState.Failed -> {
                    Assert.fail("Failed to do Get Payees And Request Transfer")
                }
            }
        })
        homeViewModel?.payeeResponse?.observeForever(Observer {state ->
            when (state) {
                is ViewState.Loading -> {}
                is ViewState.Success -> {
                    if(state.data.data.size > 0){
                        authorizationToken?.let {
                            homeViewModel?.requestTransfer(
                                TransferRequest(
                                    receipientAccountNo = state.data.data[0].accountNo,
                                    amount = 100,
                                    description = "Unit Test Get Payees And Request Transfer"
                                ),authorization = it,isTest = true
                            )
                        }
                    } else {
                        Assert.fail("Failed to do Get Payees And Request transfer cause payee payload is empty")
                    }
                }
                is ViewState.Failed -> {
                    Assert.fail("Failed to do Get Payees And Request Transfer")
                }
            }
        })
    }

    @Test
    fun `Register And Then Login And Then Get Balance , Get Payees And Make Transfer`(){
        Assert.assertNotNull(homeViewModel)
        Assert.assertNotNull(authViewModel)
        val generateRandomUserPassword = generateRandomString()
        var authorizationToken = ""

        authViewModel?.loginResponse?.observeForever(Observer { state ->
            when (state) {
                is ViewState.Loading -> {}
                is ViewState.Success -> {
                    Assert.assertNotNull(state.data.token)
                    authorizationToken = state.data.token
                    print("Login with random user and password succeed after registered")
                    homeViewModel?.getBalance(authorizationToken , isTest = true)
                    homeViewModel?.getPayees(authorizationToken , isTest = true)
                }
                is ViewState.Failed -> {
                    failedDoAllAction()
                }
            }
        })

        authViewModel?.registerResponse?.observeForever(Observer { state ->
            when (state) {
                is ViewState.Loading -> {}
                is ViewState.Success -> {
                    Assert.assertNotNull(state.data.token)
                    print("Register random user and password succeed")
                    authViewModel?.login(LoginRequest(generateRandomUserPassword , generateRandomUserPassword) , isTest = true)
                }
                is ViewState.Failed -> {
                    failedDoAllAction()
                }
            }
        })

        homeViewModel?.homeResponse?.observeForever(Observer { state ->
            when (state) {
                is ViewState.Loading -> {}
                is ViewState.Success -> {
                    Assert.assertNotNull(state.data.accountNo)
                    print("Get Balance Succeed")
                }
                is ViewState.Failed -> {
                    failedDoAllAction()
                }
            }
        })
        homeViewModel?.payeeResponse?.observeForever(Observer { state ->
            when (state) {
                is ViewState.Loading -> {}
                is ViewState.Success -> {
                    Assert.assertNotNull(state.data)
                    if(state.data.data.size > 0){
                        homeViewModel?.requestTransfer(
                            TransferRequest(
                                receipientAccountNo = state.data.data[0].accountNo,
                                amount = 100,
                                description = "Unit Testing Register And Then Login And Then Get Balance , Get Payees And Make Transfer"
                            ) , authorizationToken , isTest = true
                        )
                    } else {
                        Assert.fail("Failed to do this action")
                    }
                }
                is ViewState.Failed -> {
                    failedDoAllAction()
                }
            }
        })
        authViewModel?.register(RegisterRequest(generateRandomUserPassword , generateRandomUserPassword) , isTest = true)
    }

    private fun failedDoAllAction(){
        print("Register And Then Login And Then Get Balance , Get Payees And Make Transfer")
    }

    @After
    fun tearDown(){
        stopKoin()
    }
    private fun userLoginAndGetToken(){
        if(authorizationToken == null){
            authViewModel?.loginResponse?.observeForever(Observer { state ->
                when (state) {
                    is ViewState.Loading -> {}
                    is ViewState.Success -> {
                        accountNo = state.data.accountNo
                        authorizationToken = state.data.token
                    }
                    is ViewState.Failed -> {
                        Assert.fail("Failed to login")
                    }
                }
            })
            authViewModel?.login(LoginRequest(username , password) , isTest = true)
        }
    }
    private fun generateRandomString(): String {
        val SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
        val salt = StringBuilder()
        val rnd = Random()
        while (salt.length < 5) { // length of the random string.
            val index = (rnd.nextFloat() * SALTCHARS.length).toInt()
            salt.append(SALTCHARS[index])
        }
        return salt.toString()
    }
}