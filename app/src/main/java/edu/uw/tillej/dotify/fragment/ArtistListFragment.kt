package edu.uw.tillej.dotify.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import edu.uw.tillej.dotify.DotifyApplication
import edu.uw.tillej.dotify.R
import edu.uw.tillej.dotify.databinding.FragmentArtistListBinding
import edu.uw.tillej.dotify.repository.DataRepository
import kotlinx.coroutines.launch


class ArtistListFragment : Fragment() {

    private val navController by lazy { findNavController() }

    private lateinit var dataRepo: DataRepository
    private lateinit var dotifyApp: DotifyApplication

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
        val binding = FragmentArtistListBinding.inflate(inflater)

//        lifecycleScope.launch {
//            val artists = dataRepo.getAllArtist()
//            Log.i("LEBRON", "lifecylce hit")
//            Log.i("LEBRON", artists.toString())
//        }
        return binding.root
    }
}