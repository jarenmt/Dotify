package edu.uw.tillej.dotify.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import edu.uw.tillej.dotify.R
import edu.uw.tillej.dotify.databinding.ItemArtistBinding
import edu.uw.tillej.dotify.model.Artist
import edu.uw.tillej.dotify.model.ArtistList

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
            itemArtistProfilePic.load(artist.smallImageURL){
                crossfade(true)
                placeholder(R.drawable.lord_revan)
                transformations(CircleCropTransformation())
            } // crossfade just for fun
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