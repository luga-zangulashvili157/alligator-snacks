package com.example.finalsproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalsproject.databinding.ItemGaragePostBinding

class GarageAdapter : RecyclerView.Adapter<GarageAdapter.GarageViewHolder>() {

    private val items = mutableListOf<GaragePost>()

    fun submitList(list: List<GaragePost>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    } // clear out all old posts and replace with the latest from the database (refresh)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GarageViewHolder {
        val binding = ItemGaragePostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GarageViewHolder(binding)
    } // inflate binding instead of raw view. we output all the stuff from database into item_garage_post

    override fun onBindViewHolder(holder: GarageViewHolder, position: Int) {
        holder.bind(items[position])
    } // binds data into row view, view is recycled, data changes when we scroll

    override fun getItemCount() = items.size

    class GarageViewHolder(private val binding: ItemGaragePostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(post: GaragePost) {
            binding.postTitle.text = post.title
            binding.postDescription.text = post.description
            Glide.with(binding.root.context)
                .load(post.imageUrl)
                .centerCrop()
                .into(binding.postPicture)
        } // use glide to load the image from database
    }
}
