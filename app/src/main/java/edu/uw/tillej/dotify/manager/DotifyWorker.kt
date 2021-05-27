package edu.uw.tillej.dotify.manager

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ericchee.songdataprovider.SongDataProvider
import edu.uw.tillej.dotify.DotifyApplication

class DotifyWorker(context: Context, workerParams: WorkerParameters): CoroutineWorker(context, workerParams) {
    private val application by lazy { context.applicationContext as DotifyApplication }
    private val notificationManager by lazy { application.notificationManager }

    override suspend fun doWork(): Result {
        return try {
            val newRandomSong = SongDataProvider.getAllSongs().random()

            // NotificationManager, notify new emails have arrived
            Log.i("LEBRON", "LEBRON")
            notificationManager.publishRandomSongNotification(newRandomSong)

            Result.success()
        } catch (ex: Exception) {
            Result.failure()
        }
    }
}