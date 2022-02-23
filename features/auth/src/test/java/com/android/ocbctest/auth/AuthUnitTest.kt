package com.android.ocbctest.auth

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.common.utils.ViewState
import com.android.core.AppDispatchers
import com.android.core.di.*
import com.android.core.model.auth.login.LoginRequest
import com.android.core.model.auth.register.RegisterRequest
import com.android.ocbctest.auth.api.AuthApi
import kotlinx.coroutines.Dispatchers
import org.junit.*
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import io.reactivex.rxjava3.schedulers.Schedulers

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Scheduler
import org.junit.rules.TestRule
import org.koin.core.context.stopKoin
import org.koin.test.get
import java.lang.StringBuilder
import java.util.*
import java.util.concurrent.Callable


@RunWith(MockitoJUnitRunner::class)
class AuthUnitTest : KoinTest {
    private lateinit var context: Context
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private var authViewModel : AuthViewModel? = null
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
                            factory { AppDispatchers(Dispatchers.Main , Dispatchers.IO) }
                            viewModel { AuthViewModel(get() , get()) }
                        }
                    )
                )
            }
        }
        authViewModel = get<AuthViewModel>()
    }
    @After
    fun tearDown(){
        stopKoin()
    }
    @Test
    fun `Login Test`(){
        Assert.assertNotNull(authViewModel)
        authViewModel?.loginResponse?.observeForever(Observer { state ->
            when (state) {
                is ViewState.Loading -> {}
                is ViewState.Success -> {
                    Assert.assertNotNull(state.data.accountNo)
                    print("Login Succeed")
                }
                is ViewState.Failed -> {
                    Assert.fail("Failed to login : ${state.message}")
                }
            }
        })
        authViewModel?.login(LoginRequest("daffa" , "daffa") , isTest = true)
    }
    @Test
    fun `Register Test`(){
        authViewModel?.registerResponse?.observeForever(Observer {state ->
            when (state) {
                is ViewState.Loading -> {}
                is ViewState.Success -> {
                    Assert.assertNotNull(state.data.token)
                    print("Register Succeed")
                }
                is ViewState.Failed -> {
                    Assert.fail("Failed to register : ${state.message}")
                }
            }
        })
        val generateRandomUserAndPassword = generateRandomString()
        authViewModel?.register(RegisterRequest(generateRandomUserAndPassword , generateRandomUserAndPassword) , isTest = true)
    }
    @Test
    fun `Register And Login Test`(){
        val generateRandomUserAndPassword = generateRandomString()
        authViewModel?.registerResponse?.observeForever(Observer {state ->
            when (state) {
                is ViewState.Loading -> {}
                is ViewState.Success -> {
                    Assert.assertNotNull(state.data.token)
                    authViewModel?.login(LoginRequest(generateRandomUserAndPassword , generateRandomUserAndPassword) , isTest = true)
                }
                is ViewState.Failed -> {
                    Assert.fail("Failed to do register & login test : ${state.message}")
                }
            }
        })
        authViewModel?.loginResponse?.observeForever(Observer {state ->
            when (state) {
                is ViewState.Loading -> {}
                is ViewState.Success -> {
                    Assert.assertNotNull(state.data.token)
                    print("Register and Login Test Succeeed")
                }
                is ViewState.Failed -> {
                    Assert.fail("Failed to do Register & Login Test")
                }
            }
        })
        authViewModel?.register(RegisterRequest(generateRandomUserAndPassword , generateRandomUserAndPassword) , isTest = true)
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