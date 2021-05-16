package edu.uw.tillej.dotify.manager

import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider

class MusicManager {

    var currentSongsList: List<Song> = SongDataProvider.getAllSongs()
        private set
    var currentSelectedSong: Song? = null
        private set
    var isPlaying = true
        private set
    var currentSongPos: Int = 0
        private set
    fun setCurrentSong(song: Song) {
        this.currentSelectedSong = song
    }

    fun setCurrentSongList(new: List<Song>) {
        this.currentSongsList = new
    }

    fun setCurrentSongPos(pos: Int) {
        this.currentSongPos = pos
    }

    fun playPause() {
        this.isPlaying = !this.isPlaying
    }

    fun nextSong() {
        val listLength = currentSongsList.size
            if (this.currentSongPos < listLength - 1) {
                setCurrentSongPos(this.currentSongPos + 1)
            } else {
                setCurrentSongPos(0)
            }
        setCurrentSong(currentSongsList[currentSongPos])
    }

    fun prevSong() {
        val listLength = currentSongsList.size
        if (this.currentSongPos >= 1) {
            setCurrentSongPos(this.currentSongPos - 1)
        } else {
            setCurrentSongPos(listLength - 1) // last element in list
        }
        setCurrentSong(currentSongsList[currentSongPos])
    }
}