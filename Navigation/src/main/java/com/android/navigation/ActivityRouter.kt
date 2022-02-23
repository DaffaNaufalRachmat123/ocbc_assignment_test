package com.android.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.AnimRes
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment

private const val PACKAGE_NAME = "com.android.ocbctest"
private const val FEATURES = "com.android.ocbctest"

/**
 * Create an Intent with [Intent.ACTION_VIEW] to an [AddressableActivity].
 */
fun Context.intentTo(addressableActivity: AddressableActivity): Intent {
    return Intent(Intent.ACTION_VIEW)
        .setClassName(this, addressableActivity.className)
}
fun Context.startFeature(
    addressableActivity: AddressableActivity,
    @AnimRes enterResId: Int = android.R.anim.fade_in,
    @AnimRes exitResId: Int = android.R.anim.fade_out,
    options: Bundle? = null,
    body: Intent.() -> Unit) {

    val intent = intentTo(addressableActivity)
    intent.body()

    if (options == null) {
        val optionsCompat = ActivityOptionsCompat.makeCustomAnimation(this, enterResId, exitResId)
        ActivityCompat.startActivity(this, intent, optionsCompat.toBundle())
    } else {
        ActivityCompat.startActivity(this, intent, options)
    }
}

fun Context.startFeatureForResult(
    addressableActivity: AddressableActivity,
    activity : Activity,
    requestCode : Int = 0,
    @AnimRes enterResId: Int = android.R.anim.fade_in,
    @AnimRes exitResId: Int = android.R.anim.fade_out,
    options: Bundle? = null,
    body: Intent.() -> Unit) {

    val intent = intentTo(addressableActivity)
    intent.body()

    if (options == null) {
        val optionsCompat = ActivityOptionsCompat.makeCustomAnimation(this, enterResId, exitResId)
        ActivityCompat.startActivityForResult(activity , intent , requestCode , optionsCompat.toBundle())
    } else {
        ActivityCompat.startActivityForResult(activity , intent , requestCode,null)
    }
}

interface AddressableActivity {
    val className: String
}
object Activities {
    object ActivityRegister : AddressableActivity {
        override val className = "com.android.ocbctest.auth.ActivityRegister"
    }
    object ActivityLogin : AddressableActivity {
        override val className = "com.android.ocbctest.auth.ActivityLogin"
    }
    object ActivityHome : AddressableActivity {
        override val className = "com.android.ocbctest.home.ActivityHome"
    }
    object ActivityTransfer : AddressableActivity {
        override val className = "com.android.ocbctest.home.ActivityTransfer"
    }
}