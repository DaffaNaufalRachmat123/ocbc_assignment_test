
object Libraries {

    // Timber for logging
    val timberLog                   = "com.github.ajalt:timberkt:1.5.1"

    // Koin for dependency injection
    val koin                        = "org.koin:koin-android:2.2.2"
    val koinViewModel               = "org.koin:koin-androidx-viewmodel:2.2.2"
    val koinScope                   = "org.koin:koin-androidx-scope:2.2.2"
    val koinExt                     = "org.koin:koin-androidx-ext:2.2.2"

    // View Binding
    val viewBindingKtx              = "com.github.DylanCaiCoding.ViewBindingKTX:viewbinding-ktx:1.2.4"

    // Shimmer
    val facebookShimmer             = "com.facebook.shimmer:shimmer:0.5.0"

    // Retrofit and OkHttp for networking
    val gson                        = "com.google.code.gson:gson:2.8.6"
    val retrofit                    = "com.squareup.retrofit2:retrofit:2.9.0"
    val retrofitGsonConverter       = "com.squareup.retrofit2:converter-gson:2.9.0"
    val okhttp                      = "com.squareup.okhttp3:okhttp:4.9.0"
    val httpLoggingInterceptor      = "com.squareup.okhttp3:logging-interceptor:4.9.0"

    val chuckerDebug                = "com.github.ChuckerTeam.Chucker:library:3.4.0"
    val chuckerRelease              = "com.github.ChuckerTeam.Chucker:library-no-op:3.4.0"

    // ReactiveX for Reactive Programming
    val rxJava                      = "io.reactivex.rxjava3:rxjava:3.0.1"
    val rxKotlin                    = "io.reactivex.rxjava3:rxkotlin:3.0.1"
    val rxAndroid                   = "io.reactivex.rxjava3:rxandroid:3.0.0"

    val deeplinkdispatch            = "com.airbnb:deeplinkdispatch:5.2.0"
    val deeplinkdispatchProcessor   = "com.airbnb:deeplinkdispatch-processor:5.2.0"

    val kotPrefCore                 = "com.chibatching.kotpref:kotpref:2.13.0"
    val kotPrefInit                 = "com.chibatching.kotpref:initializer:2.13.0"
    val kotPrefGson                 = "com.chibatching.kotpref:gson-support:2.13.0"
    val BRVAH                       = "com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.50"
    val threeTenAbp                 = "com.jakewharton.threetenabp:threetenabp:1.3.0"
    val sweetAlert                  = "cn.pedant.sweetalert:library:1.3"
}

object KotlinLibraries {
    val kotlin                      = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.20"
    val kotlinCoroutineCore         = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2"
    val kotlinCoroutineAndroid      = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2"
    val kotlinCoroutinePlayService  = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.4.2"
}

object AndroidLibraries {
    // ANDROID
    val appCompat                   = "androidx.appcompat:appcompat:1.4.1"
    val activityKtx                 = "androidx.activity:activity-ktx:1.2.0"
    val fragmentKtx                 = "androidx.fragment:fragment-ktx:1.3.0"
    val material                    = "com.google.android.material:material:1.3.0-alpha04"
    val recyclerView                = "androidx.recyclerview:recyclerview:1.2.0-beta01"
    val swipeRefreshLayout          = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
    val cardView                    = "androidx.cardview:cardview:1.2.0-beta01"
    val legacySupport               = "androidx.legacy:legacy-support-v4:1.0.0"
    val coreKtx                     = "androidx.core:core-ktx:1.5.0-alpha05"
    val constraintLayout            = "androidx.constraintlayout:constraintlayout:2.1.0"

    val lifecycleViewModel          = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.0-beta01"
    val lifecycleRuntimeKtx         = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.0-beta01"
    val lifecycleCommon             = "androidx.lifecycle:lifecycle-common-java8:2.3.0-beta01"
    val lifecycleLiveData           = "androidx.lifecycle:lifecycle-livedata-ktx:2.3.0-beta01"
}

object TestLibraries {

    // Junit
    val junit = "androidx.test.ext:junit:1.1.2"

    // Android test
    val androidTestRunner = "androidx.test:runner:2.2.2"
    val fragmentNav = "androidx.fragment:fragment-testing:1.3.0-beta02"
    val archCoreTest = "androidx.arch.core:core-testing:2.1.0"

    // Kotlin Coroutine
    val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.2"

    // Koin
    val koin = "org.koin:koin-test:2.2.2"

    // Mockk
    val mockkAndroid = "io.mockk:mockk-android:1.10.5"
    val mockk = "io.mockk:mockk:1.10.5"
    val roboelectric = "org.robolectric:robolectric:4.5"

    //PowerMock
    val powerMock = "org.powermock:powermock-api-mockito:1.4.12"
    val powerMockModule = "org.powermock:powermock-module-junit4:1.6.2"

    // Espresso
    val espresso = "androidx.test.espresso:espresso-core:3.3.0"
    val espressoContrib = "androidx.test.espresso:espresso-contrib:3.3.0"

    // LiveData
    val liveData = "com.jraska.livedata:testing-ktx:1.1.0"
}