package edu.uw.tillej.dotify.manager

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

const val SONG_TAG = "DOTIFY_RANDOM_SONG_TAG"

class DotifyWorkManager(context: Context) {
   private val workManager : WorkManager = WorkManager.getInstance(context)

    fun dotifySync(){
        val request = OneTimeWorkRequestBuilder<DotifyWorker>()
            .setInitialDelay(
                5,
                TimeUnit.SECONDS
            )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        workManager.enqueue(request)
    }

    fun randomSongPeriodically() {
        if (isRandomSongNotifWorking()) {
            return
        }

        val request = PeriodicWorkRequestBuilder<DotifyWorker>(20, TimeUnit.MINUTES)
            .setInitialDelay(5, TimeUnit.SECONDS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiresCharging(true)
                    .build()
            )
            .addTag(SONG_TAG)
            .build()

        workManager.enqueue(request)

    }

    fun startRefreshEmailsPeriodically() {
        // returns and doesn't start another work function if one is already currently running
        if (isRandomSongNotifWorking()) {
            return
        }

        val request = PeriodicWorkRequestBuilder<DotifyWorker>(15, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiresCharging(true)
                    .build()
            )
            .addTag(SONG_TAG)
            .build()

        workManager.enqueue(request)

    }

    fun stopPeriodicallyRefreshing() {
        workManager.cancelAllWorkByTag(SONG_TAG)
    }

    private fun isRandomSongNotifWorking(): Boolean {
        return workManager.getWorkInfosByTag(SONG_TAG).get().any {
            when(it.state) {
                WorkInfo.State.RUNNING,
                WorkInfo.State.ENQUEUED -> true
                else -> false
            }
        }
    }
}
