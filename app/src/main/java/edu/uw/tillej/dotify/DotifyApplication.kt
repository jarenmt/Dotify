package edu.uw.tillej.dotify

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import edu.uw.tillej.dotify.manager.DotifyNotificationManager
import edu.uw.tillej.dotify.manager.DotifyWorkManager
import edu.uw.tillej.dotify.manager.MusicManager
import edu.uw.tillej.dotify.repository.DataRepository

const val PREF_KEY = "PREF_KEY"

class DotifyApplication: Application() {

    lateinit var musicManager: MusicManager
    lateinit var dataRepo: DataRepository
    lateinit var dotifyWorkManager: DotifyWorkManager
    lateinit var notificationManager: DotifyNotificationManager
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate() {
        super.onCreate()

        this.musicManager = MusicManager()
        this.dataRepo = DataRepository()
        this.dotifyWorkManager = DotifyWorkManager(this)
        this.notificationManager = DotifyNotificationManager(this)
        this.sharedPreferences = getSharedPreferences(
            PREF_KEY, Context.MODE_PRIVATE)

    }

}