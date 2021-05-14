package edu.uw.tillej.dotify

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import edu.uw.tillej.dotify.databinding.FragmentStatisticsBinding

class StatisticsFragment : Fragment() {

    val safeArgs: StatisticsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentStatisticsBinding.inflate(inflater)


        with(binding) {
            statisticsAlbumCover.setImageResource(safeArgs.albumCover)
            statisticsPlays.text = safeArgs.plays.toString()
        }

        return binding.root
    }
}