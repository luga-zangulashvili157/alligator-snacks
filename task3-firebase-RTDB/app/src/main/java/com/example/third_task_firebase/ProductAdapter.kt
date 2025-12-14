package com.example.third_task_firebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    private val items: MutableList<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductVH>() {

    inner class ProductVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice) // elementis textview-ze mititebebs vinaxavt, mere akavshirebistvis.
        val tvStock: TextView = itemView.findViewById(R.id.tvStock)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductVH(view)
    }

    override fun onBindViewHolder(holder: ProductVH, position: Int) {
        val product = items[position]
        holder.tvName.text = product.name ?: "unknown"
        holder.tvPrice.text = "price: $${String.format("%.2f", product.price ?: 0.0)}"
        holder.tvStock.text = if (product.inStock == true) "in stock" else "out of stock" // produqtis monacemebs recyclerview elementis xedebtan vakavshirebt

        holder.itemView.setOnClickListener { onItemClick(product) } //click action-s vaketebt
    }

    override fun getItemCount(): Int = items.size

    fun replaceAll(newItems: List<Product>) {
        items.clear()
        items.addAll(newItems) // es 1 patara fragmenti shlis wina cvladebs da yvelafers, da databazashi axali racaa shemosuli imas chawers. recyclerview -s arefreshebs.
        notifyDataSetChanged()
    }
}
