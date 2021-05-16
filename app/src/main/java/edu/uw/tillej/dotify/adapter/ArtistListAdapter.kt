package edu.uw.tillej.dotify.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import edu.uw.tillej.dotify.databinding.ItemArtistBinding
import edu.uw.tillej.dotify.model.Artist

class ArtistListAdapter(private var listOfArtists: List<Artist>): RecyclerView.Adapter<ArtistListAdapter.ArtistViewHolder> () {
    class ArtistViewHolder(val binding: ItemArtistBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val binding = ItemArtistBinding.inflate(LayoutInflater.from(parent.context))
        return ArtistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val artist: Artist = listOfArtists[position]
        with(holder.binding) {
            itemArtistName.text = artist.name
//            itemArtistProfilePic.setImageResource(artist.smallImageURL)
        }
    }

    fun updateArtists(newArtists: List<Artist>) {
        val callback = ArtistDiffCallback(this.listOfArtists, newArtists)
        val results = DiffUtil.calculateDiff(callback)
        results.dispatchUpdatesTo(this)

        this.listOfArtists = newArtists
    }
    override fun getItemCount(): Int = listOfArtists.size

}