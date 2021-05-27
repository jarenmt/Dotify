package edu.uw.tillej.dotify

import android.app.Application
import android.app.NotificationManager
import android.util.Log
import androidx.work.WorkManager
import edu.uw.tillej.dotify.manager.DotifyNotificationManager
import edu.uw.tillej.dotify.manager.DotifyWorkManager
import edu.uw.tillej.dotify.manager.MusicManager
import edu.uw.tillej.dotify.repository.DataRepository

class DotifyApplication: Application() {

    lateinit var musicManager: MusicManager
    lateinit var dataRepo: DataRepository
    lateinit var dotifyWorkManager: DotifyWorkManager
    lateinit var notificationManager: DotifyNotificationManager
    override fun onCreate() {
        super.onCreate()

        this.musicManager = MusicManager()
        this.dataRepo = DataRepository()
        this.dotifyWorkManager = DotifyWorkManager(this)
        this.notificationManager = DotifyNotificationManager(this)
    }

}