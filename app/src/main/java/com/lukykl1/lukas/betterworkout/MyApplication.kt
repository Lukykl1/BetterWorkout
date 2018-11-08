package com.lukykl1.lukas.betterworkout

import android.app.Application
import org.koin.android.ext.android.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // start Koin!
        startKoin(this, listOf(IOCModule))
    }
}