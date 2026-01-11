package com.example.finalsproject

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class SuccessFragment : Fragment(R.layout.fragment_success) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageView = view.findViewById<ImageView>(R.id.zhatapicture)

        val imageUrl = "https://firebasestorage.googleapis.com/v0/b/finalstask-914.firebasestorage.app/o/dog.jpg?alt=media&token=20090459-bc41-40de-b125-0ebd6e1ba685"

        // zhata image

        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.placeholder) // load zhata into zhatapicture
            .into(imageView)

        // make the whole fragment clickable
        view.setOnClickListener {
            val fragment = HomeFragment() // navigate to home fragment with fade animation
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.anim.fade_in, android.R.anim.fade_out,
                    android.R.anim.fade_in, android.R.anim.fade_out
                )
                .replace(R.id.fragmentContainer, fragment)
                .commit()
        }

    }
}
