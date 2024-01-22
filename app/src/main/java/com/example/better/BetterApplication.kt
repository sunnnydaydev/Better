package com.example.better

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Create by SunnyDay /01/07 17:29:31
 */

@HiltAndroidApp
class BetterApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    private fun initTimber() {
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String {
                // format the log tag to have "TAG:Line Number"
                return super.createStackElementTag(element) + ":" + element.lineNumber
            }
        })
    }
}