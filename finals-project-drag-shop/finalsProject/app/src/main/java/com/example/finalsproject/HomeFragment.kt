package com.example.finalsproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import androidx.cardview.widget.CardView


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val carImage = view.findViewById<ImageView>(R.id.funnycarImage)
        val gearImage = view.findViewById<ImageView>(R.id.gearBackground)
        val galleryImage = view.findViewById<ImageView>(R.id.galleryBackground) // load pictures with glide


        // load car image
        Glide.with(requireContext())
            .load("https://firebasestorage.googleapis.com/v0/b/finalstask-914.firebasestorage.app/o/3eed0f22f5a0f4cbf4501eef8a61b3ce.jpg?alt=media&token=c2f5e25e-6a9d-4635-8d33-374c01523660")
            .into(carImage)

        // load gear background image
        Glide.with(requireContext())
            .load("https://firebasestorage.googleapis.com/v0/b/finalstask-914.firebasestorage.app/o/Screenshot%202025-12-27%20092930.png?alt=media&token=a731dd39-e8a1-4757-bde0-143bc347c0c5")
            .into(gearImage)


        Glide.with(requireContext())
            .load("https://firebasestorage.googleapis.com/v0/b/finalstask-914.firebasestorage.app/o/2.jpg?alt=media&token=70b7dd4a-6955-47df-bbe4-9f045748c32a")
            .into(galleryImage)


        val letsGoCard = view.findViewById<CardView>(R.id.letsGoCard)
        letsGoCard.setOnClickListener {
            (activity as MainActivity).navigateTo(GarageFragment())
        } // card navigates to garage fragment

        val startShoppingCard = view.findViewById<CardView>(R.id.startShoppingCard)
        startShoppingCard.setOnClickListener {
            (activity as MainActivity).navigateTo(ShopItemsFragment())
        } // card navigates to shopItems fragment



        return view
    }







}
