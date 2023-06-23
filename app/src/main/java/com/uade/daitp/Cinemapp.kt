package com.uade.daitp

import android.app.Application
import android.content.Context

class Cinemapp : Application() {

    init {
        instance = this
    }

    companion object {
        private lateinit var instance: Cinemapp

        fun getAppContext(): Context {
            return instance.applicationContext
        }
    }

}