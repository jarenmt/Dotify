package edu.uw.tillej.dotify.activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import edu.uw.tillej.dotify.R
import edu.uw.tillej.dotify.adapter.SongListAdapter
import edu.uw.tillej.dotify.databinding.ActivitySongListBinding



const val SONGS = "SONGS_LIST"
const val CURRENT_SONG = "CURRENT_SONG"

class SongListActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongListBinding
    private lateinit var currentSelectedSong: Song
    private lateinit var currentSongsList: List<Song>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)
        binding = ActivitySongListBinding.inflate(layoutInflater).apply { setContentView(root) }

        with(binding) {
//             could maybe use elvis operator here but probably more confusing
            currentSongsList = SongDataProvider.getAllSongs()

            if (savedInstanceState != null) {
                currentSongsList =
                    (savedInstanceState.getParcelableArray(SONGS) as Array<Song>).toList()

                savedInstanceState.getParcelable<Song>(CURRENT_SONG).also {
                    if (it != null) {
                        currentSelectedSong = it
                        songDescription.text =
                            "${currentSelectedSong.title} - ${currentSelectedSong.artist}"
                        songDescription.visibility = View.VISIBLE
                        btnRefresh.visibility = View.VISIBLE
                    }
                }

            } else {
                currentSongsList = SongDataProvider.getAllSongs()
            }


            val adapter = SongListAdapter(currentSongsList)
            rvSongs.adapter = adapter
            btnRefresh.setOnClickListener() {
                adapter.updateSongs(currentSongsList.toMutableList().shuffled())
            }

            title = getString(R.string.songs_list_title)

            adapter.songOnClickListener = { _: Int, song: Song ->
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

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArray(SONGS, currentSongsList.toTypedArray())
        outState.putParcelable(CURRENT_SONG, currentSelectedSong)
        super.onSaveInstanceState(outState)
    }
}