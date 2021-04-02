package edu.uw.tillej.dotify

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.widget.addTextChangedListener
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
//        changeUserNameButton.setOnFocusChangeListener{ view: View, b: Boolean ->
//            changeUserNameButton.isClickable = userNameEditView.text.toString().trim().isNotEmpty()
//        }
        changeUserNameButton.addTextChangedListener{
            changeUserNameButton.isClickable = userNameEditView.text.toString().trim().isNotEmpty()
        }
        changeUserNameButton.setOnClickListener{
            changeUserNameClicked(changeUserNameButton)
        }

        var playsNumber = findViewById<TextView>(R.id.plays)

        playsNumber.text = randomPlays.toString()

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

    fun changeUserNameClicked(btn: Button) {
      if (userNameEditView.text.toString().isNotEmpty()) {
          editUser = editUser.not()
          if (editUser == true) {
              btn.text = "Apply"
              userNameEditView.visibility = View.VISIBLE
              userNameTextView.visibility = View.GONE
          } else {
              btn.text = "Change User"
              userNameTextView.text = userNameEditView.text.toString()
              userNameEditView.visibility = View.GONE
              userNameTextView.visibility = View.VISIBLE
          }
      } else {
          btn.isClickable = false
      }
    }

//    git remote add origin https://github.com/jarenmt/Dotify.git
//git branch -M main
//git push -u origin main

    fun playButtonClicked(plays: TextView): TextView {
        var currentPlays = plays.text.toString().toInt()
        plays.text = (currentPlays + 1).toString()
        return plays
    }

    fun prevButtonClicked() {
        Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
    }

    fun nextButtonClicked() {
        Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()

    }
}