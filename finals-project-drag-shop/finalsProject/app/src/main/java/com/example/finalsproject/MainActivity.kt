package com.example.finalsproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.example.finalsproject.databinding.ActivityMainBinding
import kotlin.text.replace

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, LoadingScreenFragment())
                .commit()
        } // we start with loadingscreenfragment
    }


    // navigate with animations
    fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out,
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            .replace(binding.fragmentContainer.id, fragment)
            .addToBackStack(null) // allows popBackStack() to work. it removes the top fragment from the back stack and shows the previous one. basically allows the user to go back into fragments
            .commit()
    }
}









