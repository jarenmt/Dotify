package edu.uw.tillej.dotify.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.widget.addTextChangedListener
import com.ericchee.songdataprovider.Song
import edu.uw.tillej.dotify.DotifyApplication
import edu.uw.tillej.dotify.R
import edu.uw.tillej.dotify.databinding.ActivityMainBinding
import edu.uw.tillej.dotify.manager.MusicManager
import kotlin.random.Random

private const val SONG = "song"

fun navigateToMainActivity(context: Context) = with(context) {
    // take the person object send it to

    val intent = Intent(this, MainActivity::class.java) // declare to launch PersonDetailActi
    // we don't need to create a bundle here because we are storing song at the application level
    startActivity(intent)
}

class MainActivity : AppCompatActivity() {

    private val dotifyApp: DotifyApplication by lazy { application as DotifyApplication }
    private lateinit var binding: ActivityMainBinding
    private var totalPlays = 0;
    lateinit var musicManager: MusicManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.musicManager = dotifyApp.musicManager
        val song = musicManager.currentSelectedSong
        var randomPlays = Random.nextInt(0, 100)
        totalPlays = randomPlays
        if (savedInstanceState != null) {
            totalPlays = savedInstanceState.getInt("plays", 0)
        }
        with(binding) {
//            val song: Song? = intent.getParcelableExtra<Song>(SONG) this is now pulled from application

            song?.let { albumCover.setImageResource(it.largeImageID) }
            song?.let { artist.text = song.artist }
            song?.let { songTitle.text = song.title }

            plays.text = totalPlays.toString()

            settings.setOnClickListener{
                song?.let { it1 -> navigateToSettingsActivity(this@MainActivity, it1, totalPlays) }
            }

        albumCover.setOnLongClickListener{
            var color = Color.argb(Random.nextInt(0, 256), Random.nextInt(0, 256), Random.nextInt(0, 256), Random.nextInt(0, 256))
            plays.setTextColor(color)
            true
        }

        playButton.setOnClickListener{
            playButtonClicked(plays)
        }

            previousButton.setOnClickListener{
            prevButtonClicked()
        }
        nextButton.setOnClickListener{
            nextButtonClicked()
        }

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("plays", totalPlays)
        super.onSaveInstanceState(outState)
    }

    private fun playButtonClicked(plays: TextView): TextView {
        var currentPlays = plays.text.toString().toInt()
        musicManager.playPause() // flips from play to pause vice versa
        if (musicManager.isPlaying) {
            binding.playButton.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24)
        } else {
            binding.playButton.setImageResource(R.drawable.ic_play)
            totalPlays = currentPlays + 1
            plays.text = (currentPlays + 1).toString()
        }
        return plays
    }

    private fun prevButtonClicked() {
        Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        musicManager.prevSong()
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun nextButtonClicked() {
        Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        musicManager.nextSong()
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}

