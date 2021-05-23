package edu.uw.tillej.dotify

import android.app.Application
import android.util.Log
import androidx.work.WorkManager
import edu.uw.tillej.dotify.manager.DotifyWorkManager
import edu.uw.tillej.dotify.manager.MusicManager
import edu.uw.tillej.dotify.repository.DataRepository

class DotifyApplication: Application() {

    lateinit var musicManager: MusicManager
    lateinit var dataRepo: DataRepository
    lateinit var dotifyWorkManager: DotifyWorkManager
    override fun onCreate() {
        super.onCreate()

        Log.i("dotifyApp", "App has booted")

        this.musicManager = MusicManager()
        this.dataRepo = DataRepository()
        this.dotifyWorkManager = DotifyWorkManager(this)

    }

}