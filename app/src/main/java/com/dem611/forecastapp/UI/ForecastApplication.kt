package com.dem611.forecastapp.UI

import android.app.Application
import android.content.Context

class ForecastApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object{
        private lateinit var instance: ForecastApplication
        val context: Context
            get() = instance.applicationContext
    }
}