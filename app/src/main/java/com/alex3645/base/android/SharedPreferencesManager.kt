package com.alex3645.base.android

import android.content.Context
import android.content.SharedPreferences
import com.alex3645.eventdiploma_mvvm.R

class SharedPreferencesManager (context: Context) {

    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val LOGIN = "login"
        const val ORG_FLAG = "org_flag"
    }

    fun setOrgFlag(orgFlag: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean(ORG_FLAG, orgFlag)
        editor.apply()
    }

    fun saveUserData(login: String, token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.putString(LOGIN, login)
        editor.apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun fetchLogin(): String? {
        return prefs.getString(LOGIN, null)
    }

    fun fetchOrgFlag(): Boolean {
        return prefs.getBoolean(ORG_FLAG, false)
    }

    fun removeUserData(){
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, null)
        editor.putString(LOGIN, null)
        editor.apply()
    }
}