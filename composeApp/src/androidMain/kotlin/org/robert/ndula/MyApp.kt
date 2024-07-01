package org.robert.ndula

import android.app.Application
import di.KoinInitializer

class MyApp:Application() {

    override fun onCreate() {
        super.onCreate()
        KoinInitializer(applicationContext).init()
    }
}