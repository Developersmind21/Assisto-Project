package com.example.assisto

import android.app.Application
import com.example.assisto.di.AppContainer
import com.example.assisto.util.NotificationHelper

class AssistoApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer()
        NotificationHelper.createChannel(this)
    }
}
