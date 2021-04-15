package edu.uw.tillej.dotify

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song
import edu.uw.tillej.dotify.databinding.ItemSongBinding



class SongListAdapter(private var listofSongs: List<Song>): RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {
    var songOnLongClickListener: (position: Int, song: Song) -> Unit = {_, _ -> }
    var songOnClickListener: (position: Int, song: Song) -> Unit = { _, _ -> }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SongViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context))
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song: Song = listofSongs[position]
        with(holder.binding) {
            svArtistName.text = song.artist
            svSongName.text = song.title
            svAlbumCover.setImageResource(song.smallImageID)

            root.setOnClickListener{
                songOnClickListener(position, song)
            }

            root.setOnLongClickListener{
                songOnLongClickListener(position, song)
                true
            }
        }
    }

    override fun getItemCount(): Int = listofSongs.size
    fun updateSongs(newListSongs: List<Song>) {
        this.listofSongs = newListSongs

        notifyDataSetChanged()
    }

    class SongViewHolder(val binding: ItemSongBinding): RecyclerView.ViewHolder(binding.root)

}