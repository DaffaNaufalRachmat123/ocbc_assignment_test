package com.android.ocbctest.di

import com.android.core.di.coreModule
import com.android.ocbctest.auth.di.featureAuth
import com.android.ocbctest.home.di.featureHome


val appComponent = listOf(
    coreModule,
    featureAuth,
    featureHome
)