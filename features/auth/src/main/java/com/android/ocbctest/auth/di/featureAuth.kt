package com.android.ocbctest.auth.di

import com.android.core.di.provideApiService
import com.android.ocbctest.auth.AuthViewModel
import com.android.ocbctest.auth.api.AuthApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureAuth = module {
    single { provideApiService<AuthApi>(get()) }
    viewModel { AuthViewModel(get() , get()) }
}