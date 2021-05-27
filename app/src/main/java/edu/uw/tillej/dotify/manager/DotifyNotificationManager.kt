package edu.uw.tillej.dotify.manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ericchee.songdataprovider.Song
import edu.uw.tillej.dotify.DotifyApplication
import edu.uw.tillej.dotify.R
import edu.uw.tillej.dotify.activity.MainActivity
import kotlin.random.Random

private const val RANDOM_SONG_CHANNEL_ID = "NEW_EMAILS_CHANNEL_ID"

class DotifyNotificationManager(
        private val context: Context
    ) {


    private val notificationManager = NotificationManagerCompat.from(context)
    private val application by lazy { context.applicationContext as DotifyApplication }
    private val musicManager by lazy { application.musicManager }
//    private val dotifyApp: DotifyApplication by lazy { application as DotifyApplication }

        var isNotificationsEnabled = true

        init {
            // Initialize all channels
            initNotificationChannels()
        }

        fun publishRandomSongNotification(randomSong: Song) {
            if (!isNotificationsEnabled) {
                return
            }
            val song = randomSong.title
            val artist = randomSong.artist

            // Define the intent or action you want when user taps on notification
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                musicManager.setCurrentSong(randomSong) // sets current song in music manager
//               putExtra(EMAIL_INFO_KEY, "This string is coming from the notification")
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT) // dont forget to add PendingIntent.FLAG_UPDATE_CURRENT to send data over


            // Build information you want the notification to show
            val builder = NotificationCompat.Builder(context, RANDOM_SONG_CHANNEL_ID)    // channel id from creating the channel
                .setSmallIcon(R.drawable.ic_baseline_music_note_24)
                .setContentTitle(context.getString(R.string.random_song_notif_title, artist))
                .setContentText(context.getString(R.string.random_song_notif_description, song))
                .setContentIntent(pendingIntent)    // sets the action when user clicks on notification
                .setAutoCancel(true)    // This will dismiss the notification tap
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            // Tell the OS to publish the notification using the info
            with(notificationManager) {
                // notificationId is a unique int for each notification that you must define
                val notificationId = Random.nextInt()
                notify(notificationId, builder.build())
            }
        }

        private fun initNotificationChannels() {
            initNewRandomSongChannel()
        }

        private fun initNewRandomSongChannel() {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Info about the channel
                val name = context.getString(R.string.random_song_channel_title)
                val descriptionText = context.getString(R.string.random_song_channel_description)
                val importance = NotificationManager.IMPORTANCE_DEFAULT

                // Create channel object
                val channel = NotificationChannel(RANDOM_SONG_CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }

                // Tell the Android OS to create a channel
                notificationManager.createNotificationChannel(channel)
            }
        }

//    private fun initPromotionChannel() {}

    }
