package com.android.common.extension

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.core.AppDispatchers
import com.github.ajalt.timberkt.d
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.net.SocketTimeoutException

inline fun ViewModel.runOnUi(crossinline block: suspend (CoroutineScope.() -> Unit)): Job {
    return viewModelScope.launch(Dispatchers.Main) {
        block()
    }
}

inline fun ViewModel.runOnUi(dispatcher: AppDispatchers, crossinline block: suspend (CoroutineScope.() -> Unit)): Job {
    return viewModelScope.launch(dispatcher.main) {
        block()
    }
}
fun Boolean?.orFalse(): Boolean = this ?: false
fun Throwable.errorMessage(): String {
    val msg: String

    when (this) {
        is HttpException -> {
            d { "Error Type : HttpException" }
            val responseBody = this.response()?.errorBody()
            val code = response()?.code()
            msg = when (code) {
                500 -> {
                    "Terjadi masalah dengan server kami, coba lagi nanti"
                }
                404 -> {
                    "Gagal memuat data dari server"
                }
                else -> {
                    responseBody.getErrorMessage()
                }
            }
            println("HttpException checkApiError onError Code : $code : $msg")

        }
        is SocketTimeoutException -> {
            msg = "Timeout, Coba lagi"
        }
        else -> {
            d { "Error Type : $message"}
            msg = if (message == null || message?.startsWith("Unable to resolve host").orFalse() || message == "null" || message?.startsWith("Failed to connect to").orFalse()
                || message?.startsWith("Write error: ssl").orFalse() || message?.startsWith("Value <!DOCTYPE ").orFalse()
            ) {
                "Tidak ada koneksi jaringan"
            } else {
                message ?: "Terjadi kesalahan, silahkan coba lagi"
            }


        }
    }
    println("ApiOnError : $msg")
    return msg
}

fun ResponseBody?.getErrorMessage(): String {
    return try {
        val jsonObject = JSONObject(this?.string() ?: "")
        println("jsonObjectError : $jsonObject")
        val errorMsg: String
        errorMsg = when {
            jsonObject.has("message") -> jsonObject.getString("message")
            jsonObject.has("errors") -> jsonObject.getString("errors")
            jsonObject.has("error") -> jsonObject.getString("error")
            jsonObject.has("info") -> jsonObject.getString("info")
            else -> "Terjadi kesalahan, Coba lagi"
        }
        return errorMsg
    } catch (e: JSONException) {
        e.message.toString()
    }
}

fun Intent.clearTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) }
fun Intent.newTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }

fun View.invisible(){
    visibility = View.INVISIBLE
}

fun String.convertToMonthName() : String {
    return when(this){
        "0" -> "January"
        "1" -> "February"
        "2" -> "March"
        "3" -> "April"
        "4" -> "May"
        "5" -> "June"
        "6" -> "July"
        "7" -> "August"
        "8" -> "September"
        "9" -> "October"
        "10" -> "November"
        "11" -> "December"
        else -> ""
    }
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}