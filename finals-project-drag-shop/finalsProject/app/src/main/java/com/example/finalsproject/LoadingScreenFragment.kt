package com.example.finalsproject

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment

class LoadingScreenFragment : Fragment(R.layout.fragment_loadingscreen) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wheel = view.findViewById<ImageView>(R.id.wheelImage)
        val rotation = ObjectAnimator.ofFloat(wheel, "rotation", 0f, 600f)
        rotation.duration = 3400
        rotation.repeatCount = ObjectAnimator.INFINITE
        rotation.start()

        // wheel spin animation



        // when preload is done (or after a delay), show homefragment. this is jjust the delaying system
        view.postDelayed({
            (activity as MainActivity).navigateTo(HomeFragment())
        }, 3100)

    }

}
