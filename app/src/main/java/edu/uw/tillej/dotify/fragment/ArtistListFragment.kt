package edu.uw.tillej.dotify.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import edu.uw.tillej.dotify.DotifyApplication
import edu.uw.tillej.dotify.adapter.ArtistListAdapter
import edu.uw.tillej.dotify.databinding.FragmentArtistListBinding
import edu.uw.tillej.dotify.repository.DataRepository
import kotlinx.coroutines.launch


class ArtistListFragment : Fragment() {

    private val navController by lazy { findNavController() }

    private lateinit var dataRepo: DataRepository
    private lateinit var dotifyApp: DotifyApplication
    private lateinit var binding: FragmentArtistListBinding
    override fun onAttach(context: Context) {
        super.onAttach(context)
        dotifyApp = context.applicationContext as DotifyApplication
        this.dataRepo = dotifyApp.dataRepo
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArtistListBinding.inflate(inflater)
        with(binding) {
            swipeToRefresh.setOnRefreshListener {
                loadArtists()
                swipeToRefresh.isRefreshing = false
            }
        }


        loadArtists()

        return binding.root
    }

    private fun loadArtists() {
        lifecycleScope.launch {
            runCatching {
                Toast.makeText(activity, "loading...", Toast.LENGTH_SHORT).show()
                val artists = dataRepo.getAllArtist()

                val adapter = ArtistListAdapter(artists.artists)
                binding.avArtists.adapter = adapter

            }.onFailure{
                Toast.makeText(activity, "Load Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}