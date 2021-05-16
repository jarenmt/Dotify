package edu.uw.tillej.dotify.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ericchee.songdataprovider.Song
import edu.uw.tillej.dotify.model.Artist

class ArtistDiffCallback(private val newArtists: List<Artist>, private val oldArtists: List<Artist>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldArtists.size

    override fun getNewListSize(): Int = newArtists.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newSongs = newArtists[newItemPosition]
        val oldSongs = oldArtists[oldItemPosition]

        return newSongs.name == oldSongs.name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newContents = newArtists[newItemPosition]
        val oldContents = oldArtists[oldItemPosition]

       return newContents == oldContents
        // we could override equals to
        // areContents the same only called if areItems the Same true
        // newContents == oldContents works too.
    }

}