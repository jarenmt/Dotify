package edu.uw.tillej.dotify

import android.graphics.Color
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var randomPlays = Random.nextInt(69419, 69421)
    private lateinit var userNameEditView: EditText
    private lateinit var userNameTextView: TextView
    private  var editUser = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userNameEditView = findViewById(R.id.editUserName)
        userNameTextView = findViewById(R.id.textViewUserName)
        userNameEditView.setText(userNameTextView.text) // sets default text


        var changeUserNameButton = findViewById<Button>(R.id.changeUserButton)

        userNameEditView.addTextChangedListener(onTextChanged = { c: CharSequence?, _: Int, _: Int, _: Int ->
            changeUserNameButton.isEnabled = userNameEditView.text.toString().trim().isNotEmpty()
            }
        )
        changeUserNameButton.setOnClickListener{
            changeUserNameClicked(changeUserNameButton)
        }

        var playsNumber = findViewById<TextView>(R.id.plays)

        playsNumber.text = randomPlays.toString()

        val albumCover = findViewById<ImageView>(R.id.albumCover)
        albumCover.setOnLongClickListener{
            var color = Color.argb(Random.nextInt(0, 256), Random.nextInt(0, 256), Random.nextInt(0, 256), Random.nextInt(0, 256))
            playsNumber.setTextColor(color)
            true
        }

        val playButton = findViewById<ImageView>(R.id.playButton)
        playButton.setOnClickListener{
            playsNumber = playButtonClicked(playsNumber)
        }

        val prevButton = findViewById<ImageView>(R.id.previousButton)
        prevButton.setOnClickListener{
            prevButtonClicked()
        }
        val nextButton = findViewById<ImageView>(R.id.nextButton)
        nextButton.setOnClickListener{
            nextButtonClicked()
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

private fun albumLongClick(function: (playsNumber: TextView) -> Unit) {

}
