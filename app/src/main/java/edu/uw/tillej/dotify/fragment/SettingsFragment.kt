package edu.uw.tillej.dotify.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.uw.tillej.dotify.DotifyApplication
import edu.uw.tillej.dotify.NavGraphDirections
import edu.uw.tillej.dotify.databinding.FragmentSettingsBinding
import edu.uw.tillej.dotify.manager.DotifyNotificationManager
import edu.uw.tillej.dotify.manager.DotifyWorkManager

const val NOTIFICATION_ENABLED_KEY = "NOTIFS_ENABLED"

class SettingsFragment : Fragment() {

    private val safeArgs: SettingsFragmentArgs by navArgs()
    private val navController by lazy { findNavController() }
    private lateinit var notificationManager: DotifyNotificationManager
    private lateinit var preferences: SharedPreferences
    private lateinit var workManager: DotifyWorkManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val myApp = context.applicationContext as DotifyApplication
        notificationManager = myApp.notificationManager
        preferences = myApp.sharedPreferences
        workManager = myApp.dotifyWorkManager
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingsBinding.inflate(inflater)

        with(binding) {
            statistics.setOnClickListener{
                navController.navigate(
                    NavGraphDirections.actionGlobalStatisticsFragment(
                        albumCover = safeArgs.SONG.largeImageID,
                        plays = safeArgs.PLAYS
                    )
                )
            }

            // turns on notifications but will be published based upon if it is checked
            workManager.randomSongPeriodically()

            if (preferences != null) {
                switchNotificationsEnabled.isChecked = preferences.getBoolean(NOTIFICATION_ENABLED_KEY, false)
            }

            switchNotificationsEnabled.setOnCheckedChangeListener{_, isChecked ->
                notificationManager.isNotificationsEnabled = isChecked
                preferences?.edit { putBoolean(NOTIFICATION_ENABLED_KEY, isChecked ) }

                if (isChecked) {
                    Toast.makeText(activity, "Notifications Enabled", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "Notifications Disabled", Toast.LENGTH_SHORT).show()
                }

            }

            profile.setOnClickListener{
                navController.navigate(NavGraphDirections.actionGlobalProfileFragment())
            }

            about.setOnClickListener{
                navController.navigate(NavGraphDirections.actionGlobalAboutFragment())
            }

            artistListButton.setOnClickListener{
                navController.navigate(NavGraphDirections.actionGlobalArtistListFragment())
            }

        }

        return binding.root
    }
}