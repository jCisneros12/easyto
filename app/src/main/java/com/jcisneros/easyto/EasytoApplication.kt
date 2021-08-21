package com.jcisneros.easyto

import android.app.Application
import android.content.Context

open class EasytoApplication: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: EasytoApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

//    override fun onCreate() {
//        super.onCreate()
//        val context = applicationContext()
//    }

}