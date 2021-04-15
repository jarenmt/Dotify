package edu.uw.tillej.dotify

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.widget.addTextChangedListener
import com.ericchee.songdataprovider.Song
import edu.uw.tillej.dotify.databinding.ActivityMainBinding
import kotlin.random.Random

//private const val SONG_ALBUM_COVER = "album_cover"
//private const val SONG_ARTIST_NAME = "artist_name"
//private const val SONG_NAME = "song_name"
private const val SONG = "song"

fun navigateToMainActivity(context: Context, song: Song) = with(context) {
    // take the person object send it to

    val intent = Intent(this, MainActivity::class.java).apply { // declare to launch PersonDetailActivity
        val bundle = Bundle().apply {
//            putString(SONG_ARTIST_NAME, artist)
//            putInt(SONG_ALBUM_COVER, albumCover)
//            putString(SONG_NAME, songName)

//             Use parcelable for passing custom objects
            putParcelable(SONG, song)
        }
        putExtras(bundle)
    }

    startActivity(intent)
}

class MainActivity : AppCompatActivity() {

    private var randomPlays = Random.nextInt(69419, 69421)
    private lateinit var userNameEditView: EditText
    private lateinit var userNameTextView: TextView
    private lateinit var binding: ActivityMainBinding

    private  var editUser = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        with(binding) {

//        userNameEditView = findViewById(R.id.editUserName)
//        userNameTextView = findViewById(R.id.textViewUserName)
//        userNameEditView.setText(userNameTextView.text) // sets default text

            val song: Song? = intent.getParcelableExtra<Song>(SONG)

            song?.let { albumCover.setImageResource(it.largeImageID) }
            song?.let { artist.text = song.artist }
            song?.let { songTitle.text = song.title }


            userNameEditView = editUserName
            userNameTextView = textViewUserName
            userNameEditView.setText(userNameTextView.text) // sets default text
            var changeUserButton = findViewById<Button>(R.id.changeUserButton)

        userNameEditView.addTextChangedListener(onTextChanged = { _: CharSequence?, _: Int, _: Int, _: Int ->
            changeUserButton.isEnabled = userNameEditView.text.toString().trim().isNotEmpty()
            }
        )
            changeUserButton.setOnClickListener{
            changeUserNameClicked(changeUserButton)
        }

//        var playsNumber = findViewById<TextView>(R.id.plays)

            plays.text = randomPlays.toString()

//        val albumCover = findViewById<ImageView>(R.id.albumCover)
        albumCover.setOnLongClickListener{
            var color = Color.argb(Random.nextInt(0, 256), Random.nextInt(0, 256), Random.nextInt(0, 256), Random.nextInt(0, 256))
            plays.setTextColor(color)
            true
        }

//        val playButton = findViewById<ImageView>(R.id.playButton)
        playButton.setOnClickListener{
            playButtonClicked(plays)
        }

        val prevButton = findViewById<ImageView>(R.id.previousButton)
            previousButton.setOnClickListener{
            prevButtonClicked()
        }
//        val nextButton = findViewById<ImageView>(R.id.nextButton)
        nextButton.setOnClickListener{
            nextButtonClicked()
        }

        }
    }

    private fun changeUserNameClicked(btn: Button) {
      if (userNameEditView.text.toString().isNotEmpty()) {
          editUser = editUser.not()
          if (editUser) {
              btn.text = "Apply"
              userNameEditView.visibility = View.VISIBLE
              userNameTextView.visibility = View.GONE
          } else {
              btn.text = "Change User"
              userNameTextView.text = userNameEditView.text.toString()
              userNameEditView.visibility = View.GONE
              userNameTextView.visibility = View.VISIBLE
          }
      }
    }


    private fun playButtonClicked(plays: TextView): TextView {
        var currentPlays = plays.text.toString().toInt()
        plays.text = (currentPlays + 1).toString()
        return plays
    }

    private fun prevButtonClicked() {
        Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
    }

    private fun nextButtonClicked() {
        Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()

    }
}

