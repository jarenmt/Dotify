package edu.uw.tillej.dotify

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
import edu.uw.tillej.dotify.databinding.ActivityMainBinding
import kotlin.random.Random

private const val SONG = "song"

fun navigateToMainActivity(context: Context, song: Song) = with(context) {
    // take the person object send it to

    val intent = Intent(this, MainActivity::class.java).apply { // declare to launch PersonDetailActivity
        val bundle = Bundle().apply {

//             Use parcelable for passing custom objects
            putParcelable(SONG, song)
        }
        putExtras(bundle)
    }

    startActivity(intent)
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var totalPlays = 0;
    private  var editUser = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        with(binding) {
            var randomPlays = Random.nextInt(0, 100)
            totalPlays = randomPlays
            if (savedInstanceState != null) {
                totalPlays = savedInstanceState.getInt("plays", 0)
            }

            val song: Song? = intent.getParcelableExtra<Song>(SONG)

            song?.let { albumCover.setImageResource(it.largeImageID) }
            song?.let { artist.text = song.artist }
            song?.let { songTitle.text = song.title }

            plays.text = totalPlays.toString()

        albumCover.setOnLongClickListener{
            var color = Color.argb(Random.nextInt(0, 256), Random.nextInt(0, 256), Random.nextInt(0, 256), Random.nextInt(0, 256))
            plays.setTextColor(color)
            true
        }

        playButton.setOnClickListener{
            playButtonClicked(plays)
        }

        val prevButton = findViewById<ImageView>(R.id.previousButton)
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
        totalPlays = currentPlays + 1
        plays.text = (currentPlays + 1).toString()
        return plays
    }

    private fun prevButtonClicked() {
        Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
    }

    private fun nextButtonClicked() {
        Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}

