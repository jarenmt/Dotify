package edu.uw.tillej.dotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import edu.uw.tillej.dotify.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private val safeArgs: SettingsFragmentArgs by navArgs()
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingsBinding.inflate(inflater)

        with(binding) {
            statistics.setOnClickListener{
                navController.navigate(NavGraphDirections.actionGlobalStatisticsFragment(albumCover = safeArgs.SONG.largeImageID, plays = safeArgs.PLAYS ))
            }

            profile.setOnClickListener{
                navController.navigate(NavGraphDirections.actionGlobalProfileFragment())
            }

            about.setOnClickListener{
                navController.navigate(NavGraphDirections.actionGlobalAboutFragment())
            }

        }

        return binding.root
    }
}