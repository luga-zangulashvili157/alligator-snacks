package com.example.finalsproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.finalsproject.databinding.FragmentShopItemsBinding

class ShopItemsFragment : Fragment() {

    private var _binding: FragmentShopItemsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopItemsBinding.inflate(inflater, container, false) // create the binding object for fragment_shop_items.xml, inflate its views into memory, and store it in _binding so i can use it.

        // track parts placeholder
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/finalstask-914.firebasestorage.app/o/track-parts.png?alt=media&token=1ea41566-310a-4375-aae5-d974603b5537")
            .into(binding.trackPlaceholder) // glide image into the trackPlaceholder from firebase storage


        // navigate when clicked
        binding.trackPlaceholder.setOnClickListener {
            val bundle = Bundle().apply { putString("category", "track") }
            val partsListFragment = PartsListFragment().apply { arguments = bundle }
            (activity as MainActivity).navigateTo(partsListFragment) // when we click on the trackPlaceholder box, we navigate to the partsListFragment with the track category
        }

        // drag parts placeholder
        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/finalstask-914.firebasestorage.app/o/drag-parts.png?alt=media&token=0bb562bd-0135-4a96-9b5c-d4bd9cc5fadb")
            .into(binding.DragPlaceholder) // glide image into the dragPlaceholder from firebase storage


        // navigate when clicked
        binding.DragPlaceholder.setOnClickListener {
            val bundle = Bundle().apply { putString("category", "drag") }
            val partsListFragment = PartsListFragment().apply { arguments = bundle }
            (activity as MainActivity).navigateTo(partsListFragment) // when we click on the dragPlaceholder box, we navigate to the partsListFragment with the drag category
        }

        // back arrow navigation into homeFragment
        binding.shopItemsBackarrowIcon.setOnClickListener {
            (activity as MainActivity).navigateTo(HomeFragment())
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }
}
