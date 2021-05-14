package edu.uw.tillej.dotify

import android.app.Application
import android.util.Log

class DotifyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Log.i("dotifyApp", "App has booted")
    }

}