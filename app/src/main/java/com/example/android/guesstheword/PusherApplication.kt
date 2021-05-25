package com.example.android.guesstheword

import android.app.Application
import timber.log.Timber


// Application class that will run before anything else gets instantiated
class PusherApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Timber to use throughout the entire application
        Timber.plant(Timber.DebugTree())
    }
}