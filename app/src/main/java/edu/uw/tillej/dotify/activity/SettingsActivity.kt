package edu.uw.tillej.dotify.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.ericchee.songdataprovider.Song
import edu.uw.tillej.dotify.R
import edu.uw.tillej.dotify.databinding.ActivitySettingsBinding
import java.security.AccessController

const val SONG_KEY = "SONG"
const val PLAYS_KEY = "PLAYS"

fun navigateToSettingsActivity(context: Context, song: Song, plays: Int) = with(context){
    startActivity(Intent(this, SettingsActivity::class.java).apply {
        putExtra(SONG_KEY, song)
        putExtra(PLAYS_KEY, plays)
    })

}

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    private val navController by lazy { findNavController(R.id.navHost) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater).apply{setContentView(root)}

        with(binding) {
            title = "Settings"

            navController.setGraph(R.navigation.nav_graph, intent.extras)
        }
    }
}