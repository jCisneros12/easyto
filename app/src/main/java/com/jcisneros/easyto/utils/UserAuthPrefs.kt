package com.jcisneros.easyto.utils

import android.content.Context
import android.content.SharedPreferences
import com.jcisneros.easyto.utils.Const.SHARED_PREFS
import com.jcisneros.easyto.utils.Const.SHARED_USER_ID

class UserAuthPrefs {
    //create SharedPrefs instance
    fun getSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFS, 0)
    }

    //save User ID
    fun saveUserId(userId: String, sharedInstance: SharedPreferences) {
        sharedInstance.edit().putString(SHARED_USER_ID, userId).apply()
    }

    // get User ID
    fun getUserId(sharedInstance: SharedPreferences): String {
        return sharedInstance.getString(SHARED_USER_ID, "")!!
    }

    //remove User ID (for logout)
    fun removeUserId(sharedInstance: SharedPreferences) {
        sharedInstance.edit().putString(SHARED_USER_ID, "").apply()
    }
}