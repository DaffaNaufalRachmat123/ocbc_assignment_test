package com.android.core.prefs

import com.chibatching.kotpref.KotprefModel


object UserSession : KotprefModel() {
    override val commitAllPropertiesByDefault: Boolean = true

    const val PREF_NAME = "session_login"
    const val IS_LOGGED_IN = "is_logged_in"
    const val USER_TOKEN = "user_token"
    const val USER_NAME = "user_name"
    const val USER_BALANCE = "user_balance"
    const val USER_HOLDER = "user_holder"
    const val USER_ACCOUNT = "user_account"

    var isLoggedIn by booleanPref(default = false, key = IS_LOGGED_IN)
    var userToken by stringPref(default = "" , key = USER_TOKEN)
    var userName by stringPref(default = "" , key = USER_NAME)
    var userBalance by intPref(default = 0 , key = USER_BALANCE)
    var userAccount by stringPref(default = "" , key = USER_ACCOUNT)

    fun doLogin(
        token : String,
        userNames : String
    ) {
        UserSession.apply {
            isLoggedIn = true
            userToken = token
            userName = userNames
        }
    }
    fun doStoreBalance(balance : Int , account : String){
        UserSession.apply {
            userBalance = balance
            userAccount = account
        }
    }
    fun logout(){
        UserSession.apply {
            isLoggedIn = false
            userToken = ""
        }
    }

    override fun toString(): String {
        return "isLoggedIn: $isLoggedIn"
    }

}