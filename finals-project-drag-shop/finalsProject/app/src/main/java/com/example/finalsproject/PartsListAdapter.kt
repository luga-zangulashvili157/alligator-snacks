package com.example.finalsproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PartsListAdapter(
    initialParts: List<PartsListItem>,
    private val onItemClick: (PartsListItem) -> Unit
) : RecyclerView.Adapter<PartsListAdapter.PartsViewHolder>() {

    private val parts: MutableList<PartsListItem> = initialParts.toMutableList()

    inner class PartsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val picture: ImageView = itemView.findViewById(R.id.itemPicture)
        val title: TextView = itemView.findViewById(R.id.itemTitle) // where should go what. itemTitle should have the title from database.
        val price: TextView = itemView.findViewById(R.id.itemPrice)
        val arrow: ImageView = itemView.findViewById(R.id.arrowItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.part_from_list, parent, false)
        return PartsViewHolder(view) // for each item, we use part_from_list
    }

    override fun onBindViewHolder(holder: PartsViewHolder, position: Int) {
        val part = parts[position] // takes data for each part from database
        holder.title.text = part.name // displays text

        // format price with commas and 2 decimals
        holder.price.text = String.format("$%,.2f", part.price)

        Glide.with(holder.picture.context)
            .load(part.imageUrl) // displays image from database
            .into(holder.picture)

        holder.itemView.setOnClickListener { onItemClick(part) } // click function
    }

    override fun getItemCount(): Int = parts.size // idk what this is for

    fun updateList(newParts: List<PartsListItem>) {
        parts.clear()
        parts.addAll(newParts)
        notifyDataSetChanged()
    } // we refresh the parts list with new data from database
}
