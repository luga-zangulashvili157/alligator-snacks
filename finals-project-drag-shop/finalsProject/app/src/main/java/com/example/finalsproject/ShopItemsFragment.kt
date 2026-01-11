package com.example.finalsproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class ShopItemsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shop_items, container, false) // use fragment_shop_items.xml

        // Track parts placeholder
        val trackPlaceholder = view.findViewById<ImageView>(R.id.trackPlaceholder)
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/finalstask-914.firebasestorage.app/o/track-parts.png?alt=media&token=1ea41566-310a-4375-aae5-d974603b5537")
            .into(trackPlaceholder) // glide image into the trackPlaceholder from firebase storage

        // Navigate when clicked
        trackPlaceholder.setOnClickListener {
            val bundle = Bundle().apply { putString("category", "track") }
            val partsListFragment = PartsListFragment()
            partsListFragment.arguments = bundle
            (activity as MainActivity).navigateTo(partsListFragment) // when we click on the trackPlaceholder box, we navigate to the partsListFragment with the track category
        }

        // Drag parts placeholder
        val dragPlaceholder = view.findViewById<ImageView>(R.id.DragPlaceholder)
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/finalstask-914.firebasestorage.app/o/drag-parts.png?alt=media&token=0bb562bd-0135-4a96-9b5c-d4bd9cc5fadb")
            .into(dragPlaceholder) // glide image into the dragPlaceholder from firebase storage

        // Navigate when clicked
        dragPlaceholder.setOnClickListener {
            val bundle = Bundle().apply { putString("category", "drag") }
            val partsListFragment = PartsListFragment()
            partsListFragment.arguments = bundle
            (activity as MainActivity).navigateTo(partsListFragment) // when we click on the dragPlaceholder box, we navigate to the partsListFragment with the drag category
        }


        // back arrow navigation into homeFragment
        val backArrow = view.findViewById<ImageView>(R.id.shopItemsBackarrowIcon)
        backArrow.setOnClickListener {
            (activity as MainActivity).navigateTo(HomeFragment())
        }

        return view
    }
}
