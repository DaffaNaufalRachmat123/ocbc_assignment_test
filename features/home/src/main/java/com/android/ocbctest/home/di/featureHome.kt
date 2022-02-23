package com.android.ocbctest.home.di

import com.android.core.di.provideApiService
import com.android.ocbctest.home.HomeViewModel
import com.android.ocbctest.home.api.HomeApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureHome = module {
    single { provideApiService<HomeApi>(get()) }
    viewModel { HomeViewModel(get() , get()) }
}