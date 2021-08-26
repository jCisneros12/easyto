package com.jcisneros.easyto

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.jcisneros.easyto.utils.UserAuthPrefs
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class EasytoApplication: Application() {

    //Shared Prefs
    companion object{
        //for get instance from auth prefs
        lateinit var authPrefs: UserAuthPrefs
        lateinit var prefsInstance: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        authPrefs = UserAuthPrefs()
        prefsInstance = UserAuthPrefs().getSharedPreference(applicationContext)
    }

}