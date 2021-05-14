package edu.uw.tillej.dotify

import androidx.recyclerview.widget.DiffUtil
import com.ericchee.songdataprovider.Song

class SongDiffCallback(private val newSongs: List<Song>, private val oldSongs: List<Song>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldSongs.size

    override fun getNewListSize(): Int = newSongs.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newSongs = newSongs[newItemPosition]
        val oldSongs = oldSongs[oldItemPosition]

        return newSongs.id == oldSongs.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newContents = newSongs[newItemPosition]
        val oldContents = oldSongs[oldItemPosition]

        return newContents.artist == oldContents.artist && newContents.title == oldContents.title && newContents.smallImageID == oldContents.smallImageID &&
                newContents.largeImageID == oldContents.largeImageID

        // we could override equals to
        // areContents the same only called if areItems the Same true
        // newContents == oldContents works too.
    }

}