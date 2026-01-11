package com.example.finalsproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GarageAdapter : RecyclerView.Adapter<GarageAdapter.GarageViewHolder>() {

    private val items = mutableListOf<GaragePost>()

    fun submitList(list: List<GaragePost>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    } //clear out all old posts and replace with the latest from the database (refresh)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GarageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_garage_post, parent, false)
        return GarageViewHolder(view)
    } // we output all the stuff from database into item_garage_post

    override fun onBindViewHolder(holder: GarageViewHolder, position: Int) {
        holder.bind(items[position])
    } //binds data into row view, view is recycled, data changes when we scroll

    override fun getItemCount() = items.size

    class GarageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.postTitle)
        private val descriptionView: TextView = itemView.findViewById(R.id.postDescription) // where should go what. postDescription should have the description from database.
        private val imageView: ImageView = itemView.findViewById(R.id.postPicture)

        fun bind(post: GaragePost) {
            titleView.text = post.title
            descriptionView.text = post.description
            Glide.with(itemView.context)
                .load(post.imageUrl)
                .centerCrop()
                .into(imageView)
        } // use glide to load the image from database
    }
}
