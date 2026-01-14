package com.example.finalsproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalsproject.databinding.PartFromListBinding

class PartsListAdapter(
    initialParts: List<PartsListItem>,
    private val onItemClick: (PartsListItem) -> Unit
) : RecyclerView.Adapter<PartsListAdapter.PartsViewHolder>() {

    private val parts: MutableList<PartsListItem> = initialParts.toMutableList()

    inner class PartsViewHolder(val binding: PartFromListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartsViewHolder {
        val binding = PartFromListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PartsViewHolder(binding) // for each item, we use part_from_list
    }

    override fun onBindViewHolder(holder: PartsViewHolder, position: Int) {
        val part = parts[position] // takes data for each part from database

        // bind text
        holder.binding.itemTitle.text = part.name // displays text

        // format price with commas and 2 decimals
        holder.binding.itemPrice.text = String.format("$%,.2f", part.price)


        Glide.with(holder.binding.itemPicture.context)
            .load(part.imageUrl) // displays image from database
            .into(holder.binding.itemPicture)


        holder.binding.root.setOnClickListener { onItemClick(part) } // click function
    }

    override fun getItemCount(): Int = parts.size  // idk what this is for

    fun updateList(newParts: List<PartsListItem>) {
        parts.clear()
        parts.addAll(newParts)
        notifyDataSetChanged()
    } // we refresh the parts list with new data from database
}
