package com.example.finalsproject

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.finalsproject.databinding.FragmentLoadingscreenBinding

class LoadingScreenFragment : Fragment(R.layout.fragment_loadingscreen) {

    private var _binding: FragmentLoadingscreenBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoadingscreenBinding.bind(view)


        val rotation = ObjectAnimator.ofFloat(binding.wheelImage, "rotation", 0f, 600f)
        rotation.duration = 3400
        rotation.repeatCount = ObjectAnimator.INFINITE
        rotation.start()

        // wheel spin animation




        // when preload is done (or after a delay), show homefragment. this is jjust the delaying system
        binding.root.postDelayed({
            (activity as MainActivity).navigateTo(HomeFragment())
        }, 3100)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // avoid memory leaks
    }
}
