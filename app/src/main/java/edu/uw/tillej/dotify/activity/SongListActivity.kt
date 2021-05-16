package edu.uw.tillej.dotify.activity


import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import edu.uw.tillej.dotify.DotifyApplication
import edu.uw.tillej.dotify.R
import edu.uw.tillej.dotify.adapter.SongListAdapter
import edu.uw.tillej.dotify.databinding.ActivitySongListBinding
import edu.uw.tillej.dotify.manager.MusicManager
import edu.uw.tillej.dotify.repository.DataRepository
import kotlinx.coroutines.launch


const val SONGS = "SONGS_LIST"
const val CURRENT_SONG = "CURRENT_SONG"

class SongListActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySongListBinding
    private lateinit var currentSongsList: List<Song>
    private lateinit var musicManager: MusicManager

    private val dotifyApp: DotifyApplication by lazy {application as DotifyApplication}
    private val dataRepository: DataRepository by lazy { dotifyApp.dataRepo }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongListBinding.inflate(layoutInflater).apply { setContentView(root) }

        this.musicManager = dotifyApp.musicManager


        with(binding) {
//             could maybe use elvis operator here but probably more confusing
            if (savedInstanceState != null) {
                musicManager.setCurrentSongList(
                    (savedInstanceState.getParcelableArray(SONGS) as Array<Song>).toList()
                )

                savedInstanceState.getParcelable<Song>(CURRENT_SONG).also {
                    if (it != null) {
                        musicManager.setCurrentSong(it)
                        songDescription.text =
                            "${musicManager.currentSelectedSong?.title} - ${musicManager.currentSelectedSong?.artist}"
                        songDescription.visibility = View.VISIBLE
                        btnRefresh.visibility = View.VISIBLE
                    }
                }

            }
            currentSongsList = musicManager.currentSongsList

            val adapter = SongListAdapter(currentSongsList)
            rvSongs.adapter = adapter
            btnRefresh.setOnClickListener() {
                adapter.updateSongs(currentSongsList.toMutableList().shuffled())
            }

            title = getString(R.string.songs_list_title)

            // keep
            adapter.songOnClickListener = { pos: Int, song: Song ->
                musicManager.setCurrentSong(song)
                musicManager.setCurrentSongPos(pos)
                songDescription.text = "${song.title} - ${song.artist}"
                songDescription.visibility = View.VISIBLE
                btnRefresh.visibility = View.VISIBLE
            }

            adapter.songOnLongClickListener = { pos: Int, _: Song ->
                // pasted in here just for testing
                lifecycleScope.launch {
                    Log.i("LEBRON", "ENTERED")
                    val artists = dataRepository.getAllArtist()
                    Log.i("LEBRON JAMES", artists.toString())
                }
//                lifecycleScope.launch {
//                    runCatching {
//                        Log.i("LEBRON", "ENTERED")
//                        val artists = dataRepository.getAllArtist()
//                        Log.i("LEBRON JAMES", artists.toString())
//                    }.onFailure {
//                Toast.makeText(this@SongListActivity, "Load Failed", Toast.LENGTH_SHORT).show()
//            }
//                }
                Toast.makeText(this@SongListActivity, "Song removed", Toast.LENGTH_SHORT).show()
                val newSongsList = currentSongsList.toMutableList()
                newSongsList.removeAt(pos)
                currentSongsList = newSongsList
                musicManager.setCurrentSongList(currentSongsList)
                adapter.updateSongs(currentSongsList)
                true
            }

            songDescription.setOnClickListener {
                navigateToMainActivity(this@SongListActivity)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArray(SONGS, currentSongsList.toTypedArray())
        outState.putParcelable(CURRENT_SONG, musicManager.currentSelectedSong)
        super.onSaveInstanceState(outState)
    }
}