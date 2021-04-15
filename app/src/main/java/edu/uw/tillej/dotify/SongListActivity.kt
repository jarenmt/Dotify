package edu.uw.tillej.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import edu.uw.tillej.dotify.databinding.ActivitySongListBinding

class SongListActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongListBinding
    private lateinit var currentSelectedSong: Song
    private lateinit var currentSongsList: List<Song>

//    private var largeAlbumId = 0
//    private var artist = ""
//   private var songName = ""
//    private var songSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)
        binding = ActivitySongListBinding.inflate(layoutInflater).apply { setContentView(root) }

        with(binding) {
            currentSongsList = SongDataProvider.getAllSongs()
            val adapter =  SongListAdapter(currentSongsList)
            rvSongs.adapter = adapter
            btnRefresh.setOnClickListener() {
                adapter.updateSongs(currentSongsList.toMutableList().shuffled())
            }


            adapter.songOnClickListener = { _: Int, song: Song ->
//                songSelected = true
//                largeAlbumId = song.largeImageID
//                artist = song.artist
//                songName = song.title
                currentSelectedSong = song
                songDescription.text = "${song.title} - ${song.artist}"
                songDescription.visibility = View.VISIBLE
                btnRefresh.visibility = View.VISIBLE
            }

            adapter.songOnLongClickListener = { pos: Int, _: Song ->
                Toast.makeText(this@SongListActivity, "Song removed", Toast.LENGTH_SHORT).show()
                val newSongsList = currentSongsList.toMutableList()
                newSongsList.removeAt(pos)
                currentSongsList = newSongsList
                adapter.updateSongs(currentSongsList)
                true
            }

            songDescription.setOnClickListener {
                navigateToMainActivity(this@SongListActivity, currentSelectedSong)
            }
        }
    }
}